// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Comparator;
import java.util.Collections;
import java.util.Collection;
import java.util.stream.Collectors; 
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class FindMeetingQuery implements Comparator<Event> {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<Event> eventList = events.stream().collect(Collectors.toList());
    Collections.sort(eventList, new FindMeetingQuery());
    int mandatoryStart = TimeRange.START_OF_DAY;
    int optionalStart = TimeRange.START_OF_DAY;
    Collection<TimeRange> mandatoryAvailableTimes = new ArrayList<TimeRange>();
    Collection<TimeRange> optionalAvailableTimes = new ArrayList<TimeRange>();
    for (int i = 0; i < eventList.size(); i++) {
        checkIfEventConflicts(mandatoryAvailableTimes, eventList.get(i), request, true, mandatoryStart);
        checkIfEventConflicts(optionalAvailableTimes, eventList.get(i), request, false, optionalStart);
        TimeRange eventRange = eventList.get(i).getWhen();
        if (mandatoryStart < eventRange.end() && hasAttendees(eventList.get(i).getAttendees(), request, true)) {
            mandatoryStart = eventRange.end();
        } if (optionalStart < eventRange.end() && hasAttendees(eventList.get(i).getAttendees(), request, false)) {
            optionalStart = eventRange.end();
        }
    }
    TimeRange range = TimeRange.fromStartEnd(mandatoryStart, TimeRange.END_OF_DAY,true);
    if (range.duration() >= request.getDuration()) {
        mandatoryAvailableTimes.add(range);
    }
    TimeRange optionalRange = TimeRange.fromStartEnd(optionalStart, TimeRange.END_OF_DAY,true);
    if (optionalRange.duration() >= request.getDuration()) {
        optionalAvailableTimes.add(optionalRange);
    }

    return optionalAvailableTimes.isEmpty() ? mandatoryAvailableTimes : optionalAvailableTimes;
  }

    public int compare(Event a, Event b) { 
        //sort by start
        return a.getWhen().start() - b.getWhen().start(); 
    }

    public static boolean hasAttendees(Set eventAttendees, MeetingRequest request, boolean checkingOptional) {
        Collection<String> attendees = request.getAttendees();
        Collection<String> optionalAttendees = request.getOptionalAttendees();
        // if (checkingOptional) {
        //     attendees.addAll(optionalAttendees);
        // }
        for (String attendee : attendees) {
            if (eventAttendees.contains(attendee)) {
                return true;
            }
        }
        return false;
    }

    public static void checkIfEventConflicts(Collection<TimeRange> availableTimes, Event event, MeetingRequest request, boolean optional, int start) {
        if (hasAttendees(event.getAttendees(), request, optional)) {
            TimeRange eventRange = event.getWhen();

            if (start < eventRange.start()) {
                TimeRange range = TimeRange.fromStartEnd(start, eventRange.start(), false);
                if (range.duration() >= request.getDuration()) {
                    availableTimes.add(range);
                }
            }
            if (start < eventRange.end()) {
                start = eventRange.end();
            }
        }
    }
}

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
        mandatoryStart = checkIfEventConflicts(mandatoryAvailableTimes, eventList.get(i), request, false, mandatoryStart);
        optionalStart = checkIfEventConflicts(optionalAvailableTimes, eventList.get(i), request, true, optionalStart);
    }
    addFinalRange(mandatoryStart, request, mandatoryAvailableTimes);
    addFinalRange(optionalStart, request, optionalAvailableTimes);
    TimeRange range = TimeRange.fromStartEnd(mandatoryStart, TimeRange.END_OF_DAY,true);

    return (optionalAvailableTimes.isEmpty() && !request.getAttendees().isEmpty()) ? mandatoryAvailableTimes : optionalAvailableTimes;
  }

    public int compare(Event a, Event b) { 
        //sort by start
        return a.getWhen().start() - b.getWhen().start(); 
    }

    public static boolean hasAttendees(Set eventAttendees, MeetingRequest request, boolean checkingOptional) {
        Collection<String> attendees = request.getAttendees();
        Collection<String> optionalAttendees = request.getOptionalAttendees();
        for (String attendee : attendees) {
            if (eventAttendees.contains(attendee)) {
                return true;
            }  
        } if (checkingOptional) {
            for (String optionalAttendee : optionalAttendees) {
                if (eventAttendees.contains(optionalAttendee)) {
                    return true;
                }
            }
        } return false;
    }

    public static int checkIfEventConflicts(Collection<TimeRange> availableTimes, Event event, MeetingRequest request, boolean optional, int start) {
        if (hasAttendees(event.getAttendees(), request, optional)) {
            TimeRange eventRange = event.getWhen();

            if (start < eventRange.start()) {
                TimeRange range = TimeRange.fromStartEnd(start, eventRange.start(), false);
                if (range.duration() >= request.getDuration()) {
                    availableTimes.add(range);
                }
            }
            if (start < eventRange.end()) {
                return eventRange.end();
            }
        } return start;
    }

    public static void addFinalRange(int timeOfLastMeeting, MeetingRequest request, Collection<TimeRange> availableTimes) {
        TimeRange range = TimeRange.fromStartEnd(timeOfLastMeeting, TimeRange.END_OF_DAY,true);
        if (range.duration() >= request.getDuration()) {
            availableTimes.add(range);
        }
    }
}

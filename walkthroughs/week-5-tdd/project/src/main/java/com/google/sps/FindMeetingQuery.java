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
    int start = TimeRange.START_OF_DAY;
    Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();
    for (int i = 0; i < eventList.size(); i++) {
        if (hasMandatoryAttendees(eventList.get(i).getAttendees(), request.getAttendees())) {
            TimeRange eventRange = eventList.get(i).getWhen();

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
    TimeRange range = TimeRange.fromStartEnd(start, TimeRange.END_OF_DAY,true);
    if (range.duration() >= request.getDuration()) {
        availableTimes.add(range);
    }
    return availableTimes;
  }

    public int compare(Event a, Event b) { 
        //sort by start
        return a.getWhen().start() - b.getWhen().start(); 
    }

    public static boolean hasMandatoryAttendees(Set eventAttendees, Collection<String> mandatoryAttendees) {
        for (String mandatoryAttendee : mandatoryAttendees) {
            if (eventAttendees.contains(mandatoryAttendee)) {
                return true;
            }
        }
        return false;
    }

}

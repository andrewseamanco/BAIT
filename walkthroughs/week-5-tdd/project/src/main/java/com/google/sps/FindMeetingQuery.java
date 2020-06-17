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

/**
 * Returns a Collection of times which are available for optional attendees and mandatory attendees.
 * If optional attendees cannot be fit in, then only returns the times which mandatory attendees can
 * attend
 * 
 * @param events a list of events that may need to be accounted for
 * @param request the specifications for the meeting to be scheduled
 * @return a Collection of available times for requested meeting
*/
public final class FindMeetingQuery implements Comparator<Event> {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<Event> eventList = events.stream().collect(Collectors.toList());
    Collections.sort(eventList, new FindMeetingQuery());
    int mandatoryEnd = TimeRange.START_OF_DAY;
    int optionalEnd = TimeRange.START_OF_DAY;
    Collection<TimeRange> mandatoryAvailableTimes = new ArrayList<TimeRange>();
    Collection<TimeRange> optionalAvailableTimes = new ArrayList<TimeRange>();
    for (int i = 0; i < eventList.size(); i++) {
        mandatoryEnd = checkIfEventConflicts(mandatoryAvailableTimes, eventList.get(i), request, false, mandatoryEnd);
        optionalEnd = checkIfEventConflicts(optionalAvailableTimes, eventList.get(i), request, true, optionalEnd);
    }
    addFinalRange(mandatoryEnd, request, mandatoryAvailableTimes);
    addFinalRange(optionalEnd, request, optionalAvailableTimes);

    return (optionalAvailableTimes.isEmpty() && !request.getAttendees().isEmpty()) ? mandatoryAvailableTimes : optionalAvailableTimes;
    }

    /**
     * Comparator which organizes events based off the earliest start time
    */
    public int compare(Event a, Event b) { 
        //sort by start
        return a.getWhen().start() - b.getWhen().start(); 
    }

    /**
     * Checks if the set of event attendees has any members from the request
     * 
     * @param eventAttendees the set of people attending the event
     * @param request the specifications of the request used to retrieve attendees and optional attendees
     * @param checkingOptional a boolean if we are checking for optional attendees
     * @return if any of the requested attendees exist in the list for eventAttendees
    */
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

    /**
     * Checks if the time between the event and the last event is enough for the requested meeting time
     * 
     * @param availableTimes a collection of times which will work for the request to be added to
     * @param event the  event which we will check to see if there is enough time between its start and the last 
     * meeting's end
     * @param request the specifications for the requested meeting
     * @param optional a boolean which determines if we are checking optional attendees as well
     * @param start a number which represents the end of the last meeting
     *
     * @return the end of this event if applicable
    */
    public static int checkIfEventConflicts(Collection<TimeRange> availableTimes, Event event, MeetingRequest request, boolean optional, int end) {
        if (hasAttendees(event.getAttendees(), request, optional)) {
            TimeRange eventRange = event.getWhen();

            if (end < eventRange.start()) {
                TimeRange range = TimeRange.fromStartEnd(end, eventRange.start(), false);
                if (range.duration() >= request.getDuration()) {
                    availableTimes.add(range);
                }
            }
            if (end < eventRange.end()) {
                return eventRange.end();
            }
        } return end;
    }

    public static void addFinalRange(int timeOfLastMeeting, MeetingRequest request, Collection<TimeRange> availableTimes) {
        TimeRange range = TimeRange.fromStartEnd(timeOfLastMeeting, TimeRange.END_OF_DAY,true);
        if (range.duration() >= request.getDuration()) {
            availableTimes.add(range);
        }
    }
}

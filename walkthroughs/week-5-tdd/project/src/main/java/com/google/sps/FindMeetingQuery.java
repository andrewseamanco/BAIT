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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Returns a Collection of times which are available for optional attendees and mandatory attendees.
 * If optional attendees cannot be fit in, then only returns the times which mandatory attendees can
 * attend
 *
 * @param events A list of events that may need to be accounted for
 * @param request The specifications for the meeting to be scheduled
 * @return A Collection of available times for requested meeting
 */
public final class FindMeetingQuery implements Comparator<Event> {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    ImmutableList<Event> eventList = ImmutableList.copyOf(events);
    Collections.sort(eventList, new FindMeetingQuery());
    int mandatoryEnd = TimeRange.START_OF_DAY;
    int optionalEnd = TimeRange.START_OF_DAY;
    Collection<TimeRange> mandatoryAvailableTimes = new ArrayList<TimeRange>();
    Collection<TimeRange> optionalAvailableTimes = new ArrayList<TimeRange>();
    for (Event event : eventList) {
      mandatoryEnd = checkIfEventConflicts(
          mandatoryAvailableTimes, event, request, false, mandatoryEnd);
      optionalEnd = checkIfEventConflicts(
          optionalAvailableTimes, event, request, true, optionalEnd);
    }
    addFinalRange(mandatoryEnd, request, mandatoryAvailableTimes);
    addFinalRange(optionalEnd, request, optionalAvailableTimes);

    return (optionalAvailableTimes.isEmpty() && !request.getAttendees().isEmpty())
        ? mandatoryAvailableTimes
        : optionalAvailableTimes;
  }

  /**
   * Comparator which organizes events based off the earliest start time
   */
  public int compare(Event a, Event b) {
    // sort by start
    return a.getWhen().start() - b.getWhen().start();
  }

  /**
   * Checks if the set of event attendees has any members from the request
   *
   * @param eventAttendees The set of people attending the event
   * @param request The specifications of the request used to retrieve attendees and optional
   *     attendees
   * @param checkingOptional A boolean if we are checking for optional attendees
   * @return If any of the requested attendees exist in the list for eventAttendees
   */
  public static boolean hasAttendees(
      Set eventAttendees, MeetingRequest request, boolean checkingOptional) {
    Collection<String> attendees = request.getAttendees();
    Collection<String> optionalAttendees = request.getOptionalAttendees();
    for (String attendee : attendees) {
      if (eventAttendees.contains(attendee)) {
        return true;
      }
    }
    if (checkingOptional) {
      for (String optionalAttendee : optionalAttendees) {
        if (eventAttendees.contains(optionalAttendee)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks if the time between the event and the last event is enough for the requested meeting
   * time
   *
   * @param availableTimes A collection of times which will work for the request to be added to
   * @param event The  event which we will check to see if there is enough time between its start
   *     and the last meeting's end
   * @param request The specifications for the requested meeting
   * @param optional A boolean which determines if we are checking optional attendees as well
   * @param start A number which represents the end of the last meeting
   *
   * @return The end of this event if applicable
   */
  public static int checkIfEventConflicts(Collection<TimeRange> availableTimes, Event event,
      MeetingRequest request, boolean optional, int end) {
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
    }
    return end;
  }

  /**
   * Adds the time to the collection if the time between the final meeting and the end of the
   * day if there is enough time
   * 
   * @param timeOfLastMeeting The end time of the last meeting
   * @param request The requested meeting specifications
   * @param availableTimes The collection of times to add to
   */
  public static void addFinalRange(int timeOfLastMeeting, MeetingRequest request, Collection<TimeRange> availableTimes) {
    TimeRange range = TimeRange.fromStartEnd(timeOfLastMeeting, TimeRange.END_OF_DAY, true);
    if (range.duration() >= request.getDuration()) {
      availableTimes.add(range);
    }
  }
}

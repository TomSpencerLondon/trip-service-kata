package org.craftedsw.tripservicekata.trip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TripServiceTest {

  private static final User GUEST = null;
  private static final User UNUSED_USER = null;
  private static final User REGISTERED_USER = new User();
  private static final User ANOTHER_USER = new User();
  private static final Trip TO_BRAZIL = new Trip();
  private static final Trip TO_LONDON = new Trip();
  private User loggedInUser;
  private TripService tripService;

  @BeforeEach
  void setUp() {
    tripService = new TestableTripService();
  }

  @Test void
  should_throw_an_exception_when_user_is_not_logged_in() {
    loggedInUser = GUEST;

    assertThrows(UserNotLoggedInException.class, () -> {
      tripService.getTripsByUser(UNUSED_USER);
    });
  }

  @Test void
  should_not_return_any_trips_when_users_are_not_friends() {
    loggedInUser = REGISTERED_USER;

    User friend = new User();
    friend.addFriend(ANOTHER_USER);
    friend.addTrip(TO_BRAZIL);

    List<Trip> friendTrips = tripService.getTripsByUser(friend);

    assertEquals(0, friendTrips.size());
  }

  @Test void
  should_return_friend_trips_when_users_are_friends() {
    loggedInUser = REGISTERED_USER;

    User friend = new User();
    friend.addFriend(ANOTHER_USER);
    friend.addFriend(loggedInUser);
    friend.addTrip(TO_BRAZIL);
    friend.addTrip(TO_LONDON);

    List<Trip> friendTrips = tripService.getTripsByUser(friend);

    assertEquals(2, friendTrips.size());
  }

  private class TestableTripService extends TripService {

    @Override
    protected User getLoggedInUser() {
      return loggedInUser;
    }

    @Override
    protected List<Trip> tripsBy(User user) {
      return user.trips();
    }
  }
}

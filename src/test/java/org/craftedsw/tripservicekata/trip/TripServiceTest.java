package org.craftedsw.tripservicekata.trip;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

  private static final User GUEST = null;
  private static final User UNUSED_USER = null;
  private static final User REGISTERED_USER = new User();
  private static final User ANOTHER_USER = new User();
  private static final Trip TO_BRAZIL = new Trip();
  private static final Trip TO_LONDON = new Trip();

  @Mock
  private TripDAO tripDAO;

  @InjectMocks
  @Spy
  private TripService tripService = new TripService();

  @Test void
  should_throw_an_exception_when_user_is_not_logged_in() {
    assertThrows(UserNotLoggedInException.class, () -> {
      tripService.getFriendTrips(UNUSED_USER, GUEST);
    });
  }

  @Test void
  should_not_return_any_trips_when_users_are_not_friends() {
    User friend = aUser()
        .friendsWith(ANOTHER_USER)
        .withTrips(TO_BRAZIL)
        .build();

    List<Trip> friendTrips = tripService.getFriendTrips(friend, REGISTERED_USER);

    assertEquals(0, friendTrips.size());
  }

  @Test void
  should_return_friend_trips_when_users_are_friends() {
    User friend = aUser()
        .friendsWith(ANOTHER_USER, REGISTERED_USER)
        .withTrips(TO_BRAZIL, TO_LONDON)
        .build();

    when(tripDAO.tripsBy(friend)).thenReturn(friend.trips());

    List<Trip> friendTrips = tripService.getFriendTrips(friend, REGISTERED_USER);

    assertEquals(2, friendTrips.size());
  }
}

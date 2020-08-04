package org.craftedsw.tripservicekata.trip;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.junit.jupiter.api.Test;

public class TripServiceTest {

  @Test
  void should_throw_an_exception_when_user_is_not_logged_in() {
    TripService tripService = new TripService();
    assertThrows(UserNotLoggedInException.class, () -> {
      tripService.getTripsByUser(null);
    });
  }
}

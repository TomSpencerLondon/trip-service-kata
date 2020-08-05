package org.craftedsw.tripservicekata.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.craftedsw.tripservicekata.trip.UserBuilder;
import org.junit.jupiter.api.Test;

public class UserTest {

  private static final User BOB = new User();
  private static final User PAUL = new User();

  @Test
  void should_inform_when_users_are_not_friends() {
    User user = UserBuilder.aUser()
        .friendsWith(BOB)
        .build();

    assertThat(user.isFriendsWith(PAUL), is(false));
  }
}

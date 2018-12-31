package com.riteshakya.domain.models;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ritesh Shakya
 */

public class UserProfileTest {

    private static final String FAKE_USERNAME = "Ritesh Shakya";
    private static final String FAKE_EMAIL_ID = "riteshakya037@gmail.com";
    private UserProfile userProfile;

    @Before public void setUp() {
        userProfile = new UserProfile(FAKE_USERNAME, FAKE_EMAIL_ID);
    }

    @Test public void testUserProfileConstructorHappyCase() {
        final String username = userProfile.getUserFullName();
        final String email = userProfile.getUserEmail();

        assertThat(username).isEqualTo(FAKE_USERNAME);
        assertThat(email).isEqualTo(FAKE_EMAIL_ID);
    }
}

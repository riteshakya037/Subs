package com.riteshakya.domain.models;

import com.riteshakya.domain.DatabaseNames;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriptionTest {

    private static final String FAKE_LAYOUT_COLOR = "#81d653";

    private Subscription subscription;

    @Before public void setUp() {
        subscription = new Subscription(DatabaseNames.PATH_DEFAULT_IMAGE, FAKE_LAYOUT_COLOR);
    }

    @Test public void testSubscriptionConstructorHappyCase() {
        final String defaultImage = subscription.getSubscriptionIcon();
        final String defaultColor = subscription.getLayoutColor();

        assertThat(defaultImage).isEqualTo(DatabaseNames.PATH_DEFAULT_IMAGE);
        assertThat(defaultColor).isEqualTo(FAKE_LAYOUT_COLOR);
    }
}

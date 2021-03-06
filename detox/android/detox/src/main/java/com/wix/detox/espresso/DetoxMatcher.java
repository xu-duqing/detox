package com.wix.detox.espresso;

import android.view.View;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by simonracz on 10/07/2017.
 */

public class DetoxMatcher {

    private DetoxMatcher() {
        // static class
    }

    public static Matcher<View> matcherForText(String text) {
        // return anyOf(withText(text), withContentDescription(text));
        return withText(text);
    }

    public static Matcher<View> matcherForContentDescription(String contentDescription) {
        return withContentDescription(contentDescription);
    }

    public static Matcher<View> matcherForTestId(String testId) {
        return withTagValue(is((Object) testId));
    }

    public static Matcher<View> matcherForAnd(Matcher<View> m1, Matcher<View> m2) {
        return allOf(m1, m2);
    }

    public static Matcher<View> matcherForOr(Matcher<View> m1, Matcher<View> m2) {
        return anyOf(m1, m2);
    }

    public static Matcher<View> matcherForNot(Matcher<View> m) {
        return not(m);
    }

    public static Matcher<View> matcherWithAncestor(Matcher<View> m, Matcher<View> ancestorMatcher) {
        return allOf(m, isDescendantOfA(ancestorMatcher));
    }

    public static Matcher<View> matcherWithDescendant(Matcher<View> m, Matcher<View> descendantMatcher) {
        return allOf(m, hasDescendant(descendantMatcher));
    }

    public static Matcher<View> matcherForClass(String className) {
        try {
            Class cls = Class.forName(className);
            return isAssignableFrom(cls);
        } catch (ClassNotFoundException e) {
            // empty
        }
        return new BaseMatcher<View>() {
            @Override
            public boolean matches(Object item) {
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("This matcher doesn't match anything on purpose.");
            }
        };
    }

    public static Matcher<View> matcherForSufficientlyVisible() {
        return isDisplayingAtLeast(75);
    }

    public static Matcher<View> matcherForNotVisible() {
        return not(isDisplayed());
    }

    public static Matcher<View> matcherForNotNull() {
        return notNullValue(android.view.View.class);
    }

    public static Matcher<View> matcherForNull() {
        return nullValue(android.view.View.class);
    }

    public static Matcher<View> matcherForAtIndex(final int index, final Matcher<View> innerMatcher) {
        return new BaseMatcher<View>() {
            int count = 0;
            @Override
            public boolean matches(Object item) {
                if (!innerMatcher.matches(item)) return false;

                if (count == index) {
                    count = 0;
                    return true;
                }
                ++count;
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("matches " + index + "th view.");
            }
        };
    }

    public static Matcher<View> matcherForAnything() {
        return isAssignableFrom(View.class);
    }

}

package nl.zoostation.fsd.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import nl.zoostation.fsd.R
import nl.zoostation.fsd.util.withRating

inline fun venueDetails(block: VenueDetailsElement.() -> Unit) = VenueDetailsElement().apply(block)

class VenueDetailsElement {

    fun checkName(expectedText: String) {
        onView(withId(R.id.txtVenueName)).check(matches(withText(expectedText)))
    }

    fun checkRating(expectedValue: Float) {
        onView(withId(R.id.rating)).check(matches(withRating(expectedValue)))
    }

    fun checkDescription(expectedText: String) {
        onView(withId(R.id.txtDescription)).check(matches(withText(expectedText)))
    }

    fun checkAddress(expectedText: String) {
        onView(withId(R.id.txtAddress)).check(matches(withText(expectedText)))
    }

    fun checkPhoneNumber(expectedText: String) {
        onView(withId(R.id.txtPhoneNumber)).check(matches(withText(expectedText)))
    }
}
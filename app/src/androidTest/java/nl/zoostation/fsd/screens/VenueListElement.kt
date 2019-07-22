package nl.zoostation.fsd.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import nl.zoostation.fsd.R
import nl.zoostation.fsd.screens.list.VenueListAdapter
import nl.zoostation.fsd.util.atPosition

inline fun venueList(block: VenueListElement.() -> Unit) = VenueListElement().apply(block)

class VenueListElement {

    fun checkName(itemPosition: Int, expectedText: String) {
        onView(withId(R.id.recyclerView))
            .check(matches(atPosition(itemPosition, hasDescendant(withText(expectedText)))))
    }

    fun checkAddress(itemPosition: Int, expectedText: String) {
        onView(withId(R.id.recyclerView))
            .check(matches(atPosition(itemPosition, hasDescendant(withText(expectedText)))))
    }

    fun click(itemPosition: Int) {
        onView(withId(R.id.recyclerView))
            .perform(actionOnItemAtPosition<VenueListAdapter.ViewHolder>(itemPosition, click()))
    }
}
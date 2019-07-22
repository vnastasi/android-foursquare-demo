package nl.zoostation.fsd.util

import android.view.View
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun describeTo(description: Description?) {
            description?.appendText("has item at position ")?.appendValue(position)?.appendText(": ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView?): Boolean {
            val viewHolder = view?.findViewHolderForAdapterPosition(position) ?: return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }

fun withRating(value: Float): Matcher<View> =
    object : BoundedMatcher<View, RatingBar>(RatingBar::class.java) {

        override fun describeTo(description: Description?) {
            description?.appendText("has rating ")?.appendValue(value)
        }

        override fun matchesSafely(view: RatingBar?): Boolean =
            view?.rating == value
    }
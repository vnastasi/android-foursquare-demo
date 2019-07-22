package nl.zoostation.fsd.screens

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import nl.zoostation.fsd.R

inline fun toolbar(block: ToolbarElement.() -> Unit) = ToolbarElement().apply(block)

class ToolbarElement {

    fun selectSearch() {
        onView(withId(R.id.app_bar_search)).perform(click())
    }

    fun submitSearchQuery(query: String) {
        onView(isAssignableFrom(EditText::class.java)).perform(typeText(query), pressImeActionButton())
    }
}
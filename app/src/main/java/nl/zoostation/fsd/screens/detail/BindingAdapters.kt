package nl.zoostation.fsd.screens.detail

import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.databinding.BindingAdapter
import nl.zoostation.fsd.R
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.screens.hide
import nl.zoostation.fsd.screens.show

@BindingAdapter("description")
fun setDescription(view: TextView, venue: Venue?) {
    val text = if (venue?.description.isNullOrBlank()) {
        view.context.resources.getString(R.string.placeholder_no_description)
    } else {
        venue?.description
    }
    view.text = text
}

@BindingAdapter("showWhenContactAvailable")
fun hasContact(group: Group, venue: Venue?) {
    if (venue?.contactInfo == null) {
        group.hide()
    } else {
        group.show()
    }
}

@BindingAdapter("phoneNumber")
fun setPhoneNumber(view: TextView, venue: Venue?) {
    setContactItem(view, venue?.contactInfo?.phoneNumber)
}

@BindingAdapter("facebookHandle")
fun setFacebookHandle(view: TextView, venue: Venue?) {
    setContactItem(view, venue?.contactInfo?.facebookHandle)
}

@BindingAdapter("instagramHandle")
fun setInstagramHandle(view: TextView, venue: Venue?) {
    setContactItem(view, venue?.contactInfo?.instagramHandle)
}

@BindingAdapter("twitterHandle")
fun setTwitterHandle(view: TextView, venue: Venue?) {
    setContactItem(view, venue?.contactInfo?.twitterHandle)
}

private fun setContactItem(view: TextView, text: String?) {
    if (text.isNullOrBlank()) {
        view.hide()
    } else {
        view.show()
        view.text = text
    }
}
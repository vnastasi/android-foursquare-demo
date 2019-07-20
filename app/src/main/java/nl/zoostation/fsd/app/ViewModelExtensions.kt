package nl.zoostation.fsd.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> LiveData<T>.mutate() = this as MutableLiveData<T>
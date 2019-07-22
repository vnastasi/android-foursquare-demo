package nl.zoostation.fsd.util

import androidx.lifecycle.Observer
import org.assertj.core.api.Assertions.assertThat

class RecordingObserver<T> : Observer<T> {

    private val values = mutableListOf<T>()

    override fun onChanged(t: T) {
        values += t
    }

    fun assertSequence(vararg value: T) {
        assertThat(values)
            .usingFieldByFieldElementComparator()
            .containsExactly(*value)
    }
}
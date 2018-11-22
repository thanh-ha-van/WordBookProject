package thanh.ha.data.repository

import android.arch.lifecycle.LiveData
import io.reactivex.Flowable
import thanh.ha.domain.Definition

interface Repository {

    fun getWordDefinition(currencies: String): LiveData<List<Definition>>

}

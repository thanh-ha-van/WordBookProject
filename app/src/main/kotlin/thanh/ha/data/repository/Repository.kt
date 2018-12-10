package thanh.ha.data.repository

import android.arch.lifecycle.LiveData
import thanh.ha.domain.DefinitionInfo

interface Repository {

    fun getWordDefinition(currencies: String): LiveData<List<DefinitionInfo>>

    fun getLocalDefs(): LiveData<List<DefinitionInfo>>

}

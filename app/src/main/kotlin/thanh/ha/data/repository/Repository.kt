package thanh.ha.data.repository

import android.arch.lifecycle.LiveData
import thanh.ha.domain.DefinitionInfo

interface Repository {

    fun getWordDefs(word: String): LiveData<List<DefinitionInfo>>

    fun getRandom(): LiveData<List<DefinitionInfo>>

    fun getLocalDefs(): LiveData<List<DefinitionInfo>>

    fun saveLocalDefs(definitionInfo: DefinitionInfo)
}

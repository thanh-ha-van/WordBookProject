package thanh.ha.data.repository

import androidx.lifecycle.LiveData
import thanh.ha.domain.DefinitionInfo
import thanh.ha.domain.Keyword

interface Repository {

    fun getWordDefs(word: String): LiveData<List<DefinitionInfo>>

    fun getRandom(): LiveData<List<DefinitionInfo>>

    fun getLocalDefs(): LiveData<List<DefinitionInfo>>

    fun saveLocalDefs(definitionInfo: DefinitionInfo)

    fun removeLocalDefs(definitionInfo: DefinitionInfo)

    fun deleteAllDefs()

    fun getLocalKeyword(): LiveData<List<Keyword>>

    fun saveLocalKeyword(keyword: Keyword)

    fun deleteAllKeyword()
}

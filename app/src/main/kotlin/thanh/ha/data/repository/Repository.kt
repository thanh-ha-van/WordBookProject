package thanh.ha.data.repository

import androidx.lifecycle.LiveData
import thanh.ha.domain.DefinitionInfo

interface Repository {

    fun getWordDefinition(currencies: String): LiveData<List<DefinitionInfo>>

}

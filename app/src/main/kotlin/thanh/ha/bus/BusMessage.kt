package thanh.ha.bus

import thanh.ha.domain.DefinitionInfo

class RxEvent {
    data class EventRecentSearch(val word: String)

    data class EventSavedLocal(val word: DefinitionInfo)
}
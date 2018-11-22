package thanh.ha.domain

//TODO consider update this duplicated classes.
data class DefinitionInfo(
        var defId: Int,
        var word: String,
        var definition: String,
        var thumbsUp: Int?,
        var thumbsDown: Int?,
        var author: String,
        var currentVote: String?,
        var writtenOn: String?,
        var example: String?
)

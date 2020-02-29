package thanh.ha.data.room


class RoomContract {

    companion object {

        const val DATABASE_DEFINITION = "definition.db"

        const val TABLE_DEFINITION = "definition"

        const val TABLE_KEYWORD = "keyword"

        private const val SELECT_FROM = "SELECT * FROM "

        private const val DELETE_FROM = "DELETE FROM "

        const val SELECT_DEFINITION = SELECT_FROM + TABLE_DEFINITION

        const val SELECT_KEYWORD = SELECT_FROM + TABLE_KEYWORD

        const val DELETE_DEFINITION = DELETE_FROM + TABLE_DEFINITION

        const val DELETE_KEYWORD = DELETE_FROM + TABLE_DEFINITION

        const val COUNT_DEFINITION = "SELECT COUNT(*) FROM $TABLE_DEFINITION"

        const val COUNT_KEYWORD = "SELECT COUNT(*) FROM $TABLE_KEYWORD"

    }
}


package thanh.ha.data.room

class RoomContract {

    companion object {

        const val DATABASE_DEFINITION = "definition.db"

        const val TABLE_DEFINITION = "definition"

        private const val SELECT_FROM = "SELECT * FROM "

        private const val DELETE_FROM = "SELECT * FROM "

        const val SELECT_DEFINITION = SELECT_FROM + TABLE_DEFINITION

        const val DELETE_DEFINITION = DELETE_FROM + TABLE_DEFINITION

    }
}


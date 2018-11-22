package thanh.ha.data.room

class RoomContract {

    companion object {

        const val DATABASE_CURRENCY = "currency.db"

        const val TABLE_CURRENCIES = "currencies"

        private const val SELECT_COUNT = "SELECT COUNT(*) FROM "
        private const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_CURRENCIES_COUNT = SELECT_COUNT + TABLE_CURRENCIES
        const val SELECT_CURRENCIES = SELECT_FROM + TABLE_CURRENCIES

    }
}


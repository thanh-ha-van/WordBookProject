package thanh.ha.data.remote

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
        @SerializedName(RemoteContract.SUCCESS) val isSuccess: Boolean,
        @SerializedName(RemoteContract.QUOTES) val currencyQuotes: Map<String, Double>
)

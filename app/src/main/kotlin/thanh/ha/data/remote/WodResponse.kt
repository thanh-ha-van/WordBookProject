package thanh.ha.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WodResponse {

    @SerializedName("example")
    @Expose
    val example: String? = null
    @SerializedName("word")
    @Expose
    val word: String? = null
    @SerializedName("meaning")
    @Expose
    val meaning: String? = null

}
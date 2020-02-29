package thanh.ha.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DefinitionListResponse {

    @SerializedName("list")
    @Expose
    var list: List<DefinitionResponse>? = null

}
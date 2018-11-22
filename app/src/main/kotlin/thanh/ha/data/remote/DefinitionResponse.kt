package thanh.ha.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DefinitionResponse {

    @SerializedName("definition")
    @Expose
    var definition: String? = null
    @SerializedName("permalink")
    @Expose
    var permalink: String? = null
    @SerializedName("thumbs_up")
    @Expose
    var thumbsUp: Int? = null
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("word")
    @Expose
    var word: String? = null
    @SerializedName("defid")
    @Expose
    var defid: Int? = null
    @SerializedName("current_vote")
    @Expose
    var currentVote: String? = null
    @SerializedName("written_on")
    @Expose
    var writtenOn: String? = null
    @SerializedName("example")
    @Expose
    var example: String? = null
    @SerializedName("thumbs_down")
    @Expose
    var thumbsDown: Int? = null

}
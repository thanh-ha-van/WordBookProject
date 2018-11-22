package com.example

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import thanh.ha.data.remote.DefinitionResponse

class DefinitionListResponse {

    @SerializedName("list")
    @Expose
    var list: List<DefinitionResponse>? = null

}
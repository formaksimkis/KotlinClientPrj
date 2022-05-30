

package com.company.ipcclient.model

import com.google.gson.annotations.SerializedName

//dto
data class RootModel(
    @SerializedName ("command") val command: CommandRequestModel,
    @SerializedName ("response") val response: ResponseModel
)
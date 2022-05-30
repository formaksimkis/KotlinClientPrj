

package com.company.ipcclient.model

import com.google.gson.annotations.SerializedName

//dto
data class ResponseModel (
    @SerializedName ("channels") val channels: List<ChannelModel>,
    @SerializedName ("commands") val commands: List<CommandModel>,
    @SerializedName ("message") val message: String = "",
    @SerializedName ("status") val status: String = ""
)


package com.company.ipcclient.model

import com.google.gson.annotations.SerializedName

//dto
data class ChannelModel (
    @SerializedName("channel") val channel: Int,
    @SerializedName("description") val description: String)
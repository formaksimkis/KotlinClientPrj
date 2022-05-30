

package com.company.ipcclient.model.submit

import com.google.gson.annotations.SerializedName

data class Command(
    @SerializedName("name") val name: String,
    @SerializedName("props") val props: Props?
)
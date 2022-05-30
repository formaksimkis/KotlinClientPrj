

package com.company.ipcclient.model

import com.google.gson.annotations.SerializedName

//dto
data class LogModel (
        @SerializedName("message") val message: String)
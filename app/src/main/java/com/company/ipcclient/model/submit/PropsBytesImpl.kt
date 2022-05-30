

package com.company.ipcclient.model.submit

import com.google.gson.annotations.SerializedName

data class PropsBytesImpl(
        @SerializedName("bytes") val bytes: List<Int>
): Props()
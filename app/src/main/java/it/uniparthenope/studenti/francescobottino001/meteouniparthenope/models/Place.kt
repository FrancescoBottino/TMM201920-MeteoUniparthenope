package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import com.google.gson.annotations.SerializedName

class Place(
    @SerializedName("id")
    var codice: String,
    @SerializedName("label")
    var nome:String
) : JSONConvertable
package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import com.google.gson.annotations.SerializedName
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.JSONConvertable

data class Previsione(
    @SerializedName("it")
    var it:String,
    @SerializedName("en")
    var en:String
) : JSONConvertable
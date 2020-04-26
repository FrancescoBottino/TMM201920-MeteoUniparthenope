package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import com.google.gson.Gson

class Places(
    var array: List<Place>
) : ModelType {
    companion object {
        fun parse(response: String): Places {
            val type = Array<Place>::class.java
            val parsed = Gson().fromJson(response, type)
            return Places( parsed.asList() )
        }
    }
}
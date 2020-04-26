package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class Places(
    var array: List<Place>
) : ModelType {
    companion object {
        fun parse(response: String): Places {
            val message = "message"
            val jsonToken = JSONTokener(response).nextValue()
            if (jsonToken !is JSONObject)
                throw Exception("Could not parse result as JSON")

            val jsonResponse = JSONObject(response)
            if ( jsonResponse.has(message) ) {
                throw Exception(jsonResponse.getString(message))
            }

            val jsonResponseArray = JSONArray(jsonToken)
            try {
                return Places(jsonResponseArray.toArrayList<Place>())
            } catch (e:Exception) {
                e.printStackTrace()
                throw Exception("Error parsing forecast json",e)
            }
        }
    }
}
package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import com.google.gson.annotations.SerializedName
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.extensions.JSONConvertable
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.extensions.toArrayList
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class Places(
    var array: ArrayList<Place>
) : ModelType, JSONConvertable {
    companion object {
        fun parse(response: String): Places {
            val jsonToken = JSONTokener(response).nextValue()

            if (jsonToken !is JSONArray) {
                if (jsonToken !is JSONObject) {
                    throw Exception("Could not parse result as JSON")
                } else {
                    val jsonError = JSONObject(response)

                    if ( jsonError.has("message") ) {
                        throw Exception(jsonError.getString("message"))
                    } else {
                        throw Exception("Unknown error in response JSON parsing")
                    }
                }
            }

            val jsonResponseArray = JSONArray(response)

            try {
                return Places( jsonResponseArray.toArrayList() )
            } catch (e:Exception) {
                throw Exception("Error parsing places json",e)
            }
        }
    }

    data class Place(
        @SerializedName("id")
        var codice: String,
        @SerializedName("label")
        var nome:String
    ) : JSONConvertable
}

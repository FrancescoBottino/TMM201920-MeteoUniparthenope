package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import com.google.gson.annotations.SerializedName
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.JSONConvertable
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.toObject
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.Exception

data class Forecast(
    @SerializedName("text")
    var previsione: Previsione
) : ModelType, JSONConvertable {
    companion object {
        fun parse(response: String): ModelType {
            val result = "result"
            val successful = "ok"
            val details = "details"
            val forecast = "forecast"

            val jsonToken = JSONTokener(response).nextValue()
            if (jsonToken !is JSONObject)
                throw Exception("Could not parse result as JSON")
            val jsonResponse = JSONObject(response)

            if ( jsonResponse.has(result) && jsonResponse.getString(result) != successful ) {
                if( jsonResponse.has(details) ) {
                    throw Exception(jsonResponse.getString(details))
                } else {
                    throw Exception("Unknown error in response JSON parsing")
                }
            }

            if( !jsonResponse.has(forecast) )
                throw Exception("Could not find forecast data in JSON response")

            try {
                return jsonResponse.getJSONObject(forecast).toString().toObject<Forecast>()
            } catch (e:Exception) {
                e.printStackTrace()
                throw Exception("Error parsing forecast json",e)
            }
        }
    }
}
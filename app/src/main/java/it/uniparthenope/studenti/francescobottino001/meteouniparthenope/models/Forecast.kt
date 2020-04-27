package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import org.json.JSONTokener

data class Forecast(
    @SerializedName("text")
    var previsione: Previsione,
    @SerializedName("clf")
    var nuvolosita: Float,
    @SerializedName("crd")
    var pioggia24h: Int,
    @SerializedName("crh")
    var pioggia1h: Int,
    @SerializedName("dateTime")
    var data: String,
    @SerializedName("rh2")
    var umidita2Metri: Float,
    @SerializedName("rh300")
    var umidita300hPa: Float,
    @SerializedName("rh500")
    var umidita500hPa: Float,
    @SerializedName("rh700")
    var umidita700hPa: Float,
    @SerializedName("rh850")
    var umidita850hPa: Float,
    @SerializedName("rh950")
    var umidita950hPa: Float,
    @SerializedName("slp")
    var pressione: Float,
    @SerializedName("swe")
    var neve1h: Int,
    @SerializedName("t2c")
    var temperatura: Float,
    @SerializedName("wchill")
    var temperaturaPercepita: Float,
    @SerializedName("wd10")
    var direzioneVento10m_gradiNord: Float,
    @SerializedName("winds")
    var direzioneVento10m_coordinate: String,
    @SerializedName("ws10")
    var velocitaVento_MetriSecondo: Float,
    @SerializedName("ws10b")
    var velocitaVento_Beaufort: Float,
    @SerializedName("ws10k")
    var direzioneVento10m_KmH: Float,
    @SerializedName("ws10n")
    var direzioneVento10m_Nodi: Float

) : ModelType,
    JSONConvertable {
    companion object {
        fun parse(response: String): Forecast {
            //TODO (Update method using GSon)
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
                return jsonResponse.getJSONObject(forecast).toString().toObject()
            } catch (e:Exception) {
                e.printStackTrace()
                throw Exception("Error parsing forecast json",e)
            }
        }
    }

    data class Previsione(
        @SerializedName("it")
        var it:String,
        @SerializedName("en")
        var en:String
    ) : JSONConvertable
}
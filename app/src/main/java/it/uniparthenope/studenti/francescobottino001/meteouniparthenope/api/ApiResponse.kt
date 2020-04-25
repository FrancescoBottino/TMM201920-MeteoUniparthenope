package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api

import org.json.JSONObject
import org.json.JSONTokener

class ApiResponse(response: String) {

    var success: Boolean = false
    var message: String = ""
    var json: String = ""

    private val result = "result"
    private val successful = "ok"
    private val details = "details"
    private val forecast = "forecast"

    init {
        try {
            val jsonToken = JSONTokener(response).nextValue()
            if (jsonToken is JSONObject) {
                val jsonRsponse = JSONObject(response)

                message = if (
                    jsonRsponse.has(result) &&
                    jsonRsponse.getString(result) == successful &&
                    jsonRsponse.has(forecast)
                ) {
                    jsonRsponse.getJSONObject(forecast).toString()
                } else {
                    "An error was occurred while processing the response : ${jsonRsponse.getString(details)}"
                }

                if (jsonRsponse.optJSONObject(forecast) != null) {
                    json = jsonRsponse.getJSONObject(forecast).toString()
                    success = true
                } else {
                    success = false
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
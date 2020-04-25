package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api

import com.android.volley.Request

sealed class ApiRoute {

    val timeOut: Int
        get() {
            return 3000
        }

    val baseUrl: String
        get() {
            return "http://api.meteo.uniparthenope.it"
        }

    data class Forecast(var place:String) : ApiRoute()

    val httpMethod: Int
        get() {
            return when (this) {
                is Forecast -> Request.Method.GET
            }
        }

    val params: HashMap<String, String>
        get() {
            return when (this) {
                else -> hashMapOf()
            }
        }

    val headers: HashMap<String, String>
        get() {
            val map: HashMap<String, String> = hashMapOf()
            map["Accept"] = "application/json"
            return when (this) {
                else -> map
            }
        }

    val url: String
        get() {
            return "$baseUrl/${when (this@ApiRoute) {
                is Forecast -> "products/wrf5/forecast/${this.place}"
            }}"
        }
}
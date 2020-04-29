package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api

import com.android.volley.Request

sealed class ApiRoute {

    val timeOut: Int
        get() {
            return 3000
        }

    val baseUrl: String
        get() {
            return "https://api.meteo.uniparthenope.it"
        }

    data class Forecast(var placeCode:String) : ApiRoute()
    data class SearchPlace(var name:String) : ApiRoute()

    val httpMethod: Int
        get() {
            return when (this) {
                is Forecast -> Request.Method.GET
                is SearchPlace -> Request.Method.GET
            }
        }

    val params: HashMap<String, String>
        get() {
            val params: HashMap<String, String> = hashMapOf()
            return when (this) {
                else -> params
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
                is Forecast -> "products/wrf5/forecast/${this.placeCode}"
                is SearchPlace ->  "places/search/byname/autocomplete${if(name.isNotEmpty()) "?term=${name.replace(" ","%20")}" else ""}"
            }}"
        }
}
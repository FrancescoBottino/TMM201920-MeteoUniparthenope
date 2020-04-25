package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api

import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.ModelType
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.ModelTypeFactory
import kotlin.reflect.KClass

class ApiResponse<T: ModelType>(response: String, type: KClass<T>) {

    var success: Boolean = false
    var error: String = ""
    var json: String = ""
    var message: T? = null

    init {
        json = response
        try {
            if( response.isEmpty() ) throw Exception("Empty response")
            message = ModelTypeFactory.parse<T>(response, type)
            success = true
            error = ""
        } catch (e: Exception) {
            e.printStackTrace()
            error = e.message?:"Parsing Error"
            success = false
            message = null
        }
    }

    companion object {
        fun <T: ModelType> getErrorResponse(error:String, type:KClass<T>) : ApiResponse<T> {
            val errorResponse = ApiResponse<T>(error, type)
            errorResponse.error = error

            return errorResponse
        }
    }
}
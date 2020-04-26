package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.*
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Forecast
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.ModelType
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Places
import kotlin.reflect.KClass

class ApiClient(private val ctx: Context) {

    /***
     * PERFORM REQUEST
     */
    private fun <T:ModelType> performRequest(route: ApiRoute, type:KClass<T>, completion: (success: Boolean, apiResponse: ApiResponse<T>) -> Unit) {
        val request: StringRequest = object : StringRequest(route.httpMethod, route.url, { response ->
            this.handleResponse(response, type, completion)
        }, {
            it.printStackTrace()
            if (it.networkResponse != null && it.networkResponse.data != null)
                this.handleError(String(it.networkResponse.data), type, completion)
            else
                this.handleError(getStringError(it), type, completion)
        }) {
            override fun getParams(): MutableMap<String, String> {
                return route.params
            }

            override fun getHeaders(): MutableMap<String, String> {
                return route.headers
            }
        }
        request.retryPolicy = DefaultRetryPolicy(route.timeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        getRequestQueue().add(request)
    }

    /**
     * This method will make the creation of the error as ApiResponse
     **/
    private fun <T:ModelType> handleError(error: String, type:KClass<T>, completion: (success: Boolean, apiResponse: ApiResponse<T>) -> Unit) {
        val ar = ApiResponse.getErrorResponse(error,type)
        completion.invoke(ar.success, ar)
    }

    /**
     * This method will make the creation of the answer as ApiResponse
     **/
    private fun <T:ModelType> handleResponse(response: String, type:KClass<T>, completion: (success: Boolean, apiResponse: ApiResponse<T>) -> Unit) {
        val ar = ApiResponse<T>(response, type)
        completion.invoke(ar.success, ar)
    }

    /**
     * This method will return the error as String
     **/
    private fun getStringError(volleyError: VolleyError): String {
        return when (volleyError) {
            is TimeoutError -> "The conection timed out."
            is NoConnectionError -> "The conection couldn´t be established."
            is AuthFailureError -> "There was an authentication failure in your request."
            is ServerError -> "Server error while prosessing the server response."
            is NetworkError -> "Network error, please verify your conection."
            is ParseError -> "Error while prosessing the server response."
            else -> "Internet error"
        }
    }
    /**
     * We create and return a new instance for the queue of Volley requests.
     **/
    private fun getRequestQueue(): RequestQueue {
        val maxCacheSize = 20 * 1024 * 1024
        val cache = DiskBasedCache(ctx.cacheDir, maxCacheSize)
        val netWork = BasicNetwork(HurlStack())
        val mRequestQueue = RequestQueue(cache, netWork)
        mRequestQueue.start()
        System.setProperty("http.keepAlive", "false")
        return mRequestQueue
    }

    /**
     * --------- GET FORECAST ----------------------------
     **/
    fun forecast(placeCode: String, completion: (forecast: Forecast?, error: String?) -> Unit) {
        val route = ApiRoute.Forecast(placeCode)
        this.performRequest(route, Forecast::class) { success, response ->
            if (success) {
                completion.invoke(response.message!!, null)
            } else {
                completion.invoke(null, response.error)
            }
        }
    }

    /**
     * --------- GET AUTOCOMPLETE NAMES ------------------
     */
    fun searchPlace(name: String, completion: (places: Places?, error: String?) -> Unit) {
        val route = ApiRoute.SearchPlace(name)
        this.performRequest(route, Places::class) { success, response ->
            if (success) {
                completion.invoke(response.message!!, null)
            } else {
                completion.invoke(null, response.error)
            }
        }
    }
}
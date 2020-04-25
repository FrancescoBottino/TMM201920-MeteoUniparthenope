package it.uniparthenope.studenti.francescobottino001.meteouniparthenope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api.ApiClient
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api.ApiRoute
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.extensions.TAG
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val place = "com63049"

        //DEBUG
        //val http_request = ApiRoute.Forecast(place).url
        //input_testo.setText(http_request)

        button_submit.setOnClickListener {
            ApiClient(this@MainActivity).forecast(place) { resultJSON: String?, message: String ->
                Log.d(TAG,"Result : ${resultJSON?:"null"}")
                Log.d(TAG,"Message : ${if(message.isEmpty()) "empty" else message}")
                output_json.text = resultJSON ?: message
            }
        }
    }
}

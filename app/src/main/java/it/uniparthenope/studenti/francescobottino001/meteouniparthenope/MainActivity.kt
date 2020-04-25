package it.uniparthenope.studenti.francescobottino001.meteouniparthenope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api.ApiClient
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Forecast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val place = "com63049"

        button_submit.setOnClickListener {
            ApiClient(this@MainActivity).forecast(place) { result: Forecast?, error: String? ->
                output_json.text = result?.previsione?.it ?: (error ?: "null")
            }
        }
    }
}

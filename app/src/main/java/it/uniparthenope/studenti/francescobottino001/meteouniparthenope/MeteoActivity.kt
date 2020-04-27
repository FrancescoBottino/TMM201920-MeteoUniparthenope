package it.uniparthenope.studenti.francescobottino001.meteouniparthenope

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api.ApiClient
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Forecast
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Place
import kotlinx.android.synthetic.main.activity_meteo.*
import kotlinx.android.synthetic.main.forecast_result.*
import kotlinx.android.synthetic.main.progress_overlay.*
import java.text.SimpleDateFormat
import java.util.*

class MeteoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meteo)

        showProgressOverlay()

        lateinit var place: Place
        val extras = intent.extras
        if(extras == null) {
            //TODO(Show error)
            Place( "com63049", "Comune di Napoli" )
        } else {
            place = Place(extras.get("place_code") as String, extras.get("place_name") as String)
        }

        ApiClient(this).forecast(place.codice) { result: Forecast?, error: String? ->
            if( error != null || result == null ) {
                Toast.makeText(this@MeteoActivity, error, Toast.LENGTH_LONG).show()
                return@forecast
            }

            forecast_luogo.text = place.nome

            val date: Date? = SimpleDateFormat("yyyyMMddHHmm", Locale.ITALIAN).parse(result.data.replace("Z",""))

            forecast_data.text =
                if(date != null)
                    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALIAN).format(date)
                else
                    getString(R.string.date_parse_error)

            forecast_previsione.text = result.previsione.it

            val nuvolosita = TextView(this@MeteoActivity, null, R.style.ResultTextView)
            nuvolosita.text = String.format(getString(R.string.nuvolosita), result.nuvolosita)
            forecast_content.addView(nuvolosita)

            val pressione = TextView(this@MeteoActivity, null, R.style.ResultTextView)
            pressione.text = String.format(getString(R.string.pressione), result.pressione)
            forecast_content.addView(pressione)

            val temperatura = TextView(this@MeteoActivity, null, R.style.ResultTextView)
            temperatura.text = String.format(getString(R.string.temperatura), result.temperatura)
            forecast_content.addView(temperatura)

            val temperaturaPercepita = TextView(this@MeteoActivity, null, R.style.ResultTextView)
            temperaturaPercepita.text = String.format(getString(R.string.temperatura_percepita), result.temperaturaPercepita)
            forecast_content.addView(temperaturaPercepita)

            /**
             * TODO(Implement remaining Forecast::class fields visualization)
             **/

            hideProgressOverlay()
        }
    }

    fun showProgressOverlay() {
        forecast_result_container.visibility = View.GONE
        animateView(progress_overlay, View.VISIBLE, 0.4f, 200)
    }

    fun hideProgressOverlay() {
        animateView(progress_overlay, View.GONE, 0f, 200)
        forecast_result_container.visibility = View.VISIBLE
    }
}

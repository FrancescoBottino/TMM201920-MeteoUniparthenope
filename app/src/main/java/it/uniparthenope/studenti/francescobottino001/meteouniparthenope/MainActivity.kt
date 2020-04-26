package it.uniparthenope.studenti.francescobottino001.meteouniparthenope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api.ApiClient
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Forecast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.forecast_result.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val place = "com63049"

        button_submit.setOnClickListener {
            ApiClient(this@MainActivity).forecast(place) { result: Forecast?, error: String? ->
                if( error != null || result == null ) {
                    Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show()
                    return@forecast
                }
                
                val date: Date? = SimpleDateFormat("yyyyMMddHHmm", Locale.ITALIAN).parse(result.data.replace("Z",""))

                forecast_data.text =
                    if(date != null)
                        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALIAN).format(date)
                    else
                        getString(R.string.date_parse_error)

                forecast_previsione.text = result.previsione.it

                val nuvolosita = TextView(this@MainActivity, null, R.style.ResultTextView)
                nuvolosita.text = String.format(getString(R.string.nuvolosita), result.nuvolosita)
                forecast_content.addView(nuvolosita)

                val pressione = TextView(this@MainActivity, null, R.style.ResultTextView)
                pressione.text = String.format(getString(R.string.pressione), result.pressione)
                forecast_content.addView(pressione)

                val temperatura = TextView(this@MainActivity, null, R.style.ResultTextView)
                temperatura.text = String.format(getString(R.string.temperatura), result.temperatura)
                forecast_content.addView(temperatura)

                val temperaturaPercepita = TextView(this@MainActivity, null, R.style.ResultTextView)
                temperaturaPercepita.text = String.format(getString(R.string.temperatura_percepita), result.temperaturaPercepita)
                forecast_content.addView(temperaturaPercepita)

                /**
                 * TODO(Implement remaining Forecast::class fields visualization)
                 **/
            }
        }
    }
}

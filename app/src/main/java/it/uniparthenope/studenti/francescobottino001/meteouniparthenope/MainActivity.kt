package it.uniparthenope.studenti.francescobottino001.meteouniparthenope

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api.ApiClient
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api.ApiRoute
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.extensions.TAG
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Place
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Places
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PlacesAdapter(ArrayList(), this::mostraMeteo)
        list_of_places.adapter = adapter
        list_of_places.layoutManager = LinearLayoutManager(this)

        button_submit.setOnClickListener {
            val place = input_testo.text.toString()
            if( place.isEmpty() ) {
                input_testo.error = "Inserisci località"
                return@setOnClickListener
            }

            Log.d(TAG, "Searching for places with name ${place}")
            Log.d(TAG, "Request is : ${ApiRoute.SearchPlace(place).url}")

            ApiClient(this@MainActivity).searchPlace(place) { places: Places?, error: String? ->

                Log.d(TAG, "Results obtained ->")
                if( error != null ) {
                    Log.d(TAG, "Error ( $error )")
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@searchPlace
                }
                if( places != null ) {
                    Log.d(TAG, "Result : ${places.array.size} places")
                    adapter.setData( places.array )
                    return@searchPlace
                }
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Unknown error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun mostraMeteo( place:Place ) {
        val intent = Intent(this, MeteoActivity::class.java)
        intent.putExtra("place_code",place.codice)
        intent.putExtra("place_name",place.nome)
        startActivity(intent)
    }
}

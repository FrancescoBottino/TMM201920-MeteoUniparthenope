package it.uniparthenope.studenti.francescobottino001.meteouniparthenope

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.api.ApiClient
import it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models.Places
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestApi {
    @Test
    fun testSearchPlacesSingleResult() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        ApiClient(appContext).searchPlace("Comune di Napoli") { result: Places?, error: String? ->
            Assert.assertNotNull(result)
            val places: Places = result?: Places(listOf())
            Assert.assertEquals(1, places.array.size)
            Assert.assertEquals("com63049", places.array[0].codice)
        }
    }

    @Test
    fun testSearchPlacesMultipleResults() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        ApiClient(appContext).searchPlace("Napoli") { result: Places?, error: String? ->
            Assert.assertNotNull(result)
            val places: Places = result?: Places(listOf())
            Assert.assertEquals(20, places.array.size)
            Assert.assertEquals("com63048", places.array[5].codice)
            Assert.assertEquals("com63049", places.array[6].codice)
            Assert.assertEquals("com63041", places.array[14].codice)
        }
    }
}
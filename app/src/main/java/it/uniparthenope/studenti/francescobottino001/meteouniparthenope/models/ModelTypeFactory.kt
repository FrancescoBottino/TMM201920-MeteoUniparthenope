package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class ModelTypeFactory {
    companion object {
        fun <T:ModelType> parse(json: String, type: KClass<T>) : T? {
            return when(type) {
                Forecast::class -> Forecast.parse(json) as T
                Places::class -> Places.parse(json) as T
                else -> null
            }
        }
    }
}
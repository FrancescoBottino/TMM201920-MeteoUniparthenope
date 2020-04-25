package it.uniparthenope.studenti.francescobottino001.meteouniparthenope.models

import kotlin.reflect.KClass

class ModelTypeFactory {
    companion object {
        public fun <T:ModelType> parse(json: String, type: KClass<T>) : T? {
            return when(type) {
                Forecast::class -> {
                    Forecast.parse(json) as T
                }
                else -> null
            }
        }
    }
}
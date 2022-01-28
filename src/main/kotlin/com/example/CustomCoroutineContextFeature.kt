package com.example

import io.ktor.application.*
import io.ktor.util.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class CustomCoroutineContextFeature {
    companion object Feature : ApplicationFeature<Application, Unit, CustomCoroutineContextFeature> {
        override val key: AttributeKey<CustomCoroutineContextFeature> = AttributeKey("Custom Coroutine Context")

        override fun install(pipeline: Application, configure: Unit.() -> Unit): CustomCoroutineContextFeature {
            val feature = CustomCoroutineContextFeature()

            pipeline.intercept(ApplicationCallPipeline.Features) {
                withContext(CustomCoroutineContext("contextValue")) {
                    proceed()
                }
            }

            return feature
        }
    }
}

class CustomCoroutineContext(val contextValue: String) : AbstractCoroutineContextElement(CustomCoroutineContext) {
    companion object Key : CoroutineContext.Key<CustomCoroutineContext>
}
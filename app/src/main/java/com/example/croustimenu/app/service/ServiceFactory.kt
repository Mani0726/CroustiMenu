package com.example.croustimenu.app.service

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ServiceFactory {
    private const val BASE_URL = "https://api.croustillant.menu/v1/"

    val client = HttpClient(Android) {
        // Configuration JSON
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        // Configuration du logging
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("HTTP", message)
                }
            }
            level = LogLevel.BODY
        }

        // Configuration de base
        defaultRequest {
            url(BASE_URL)
        }

        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }
    }

    val crousService: CrousService = CrousServiceImpl(client)
}
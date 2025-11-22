package com.example.croustimenu.app.service

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
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
        expectSuccess = true
    }

    val crousService: CrousService = CrousServiceImpl(client, BASE_URL)
}
package com.example.equipodefutbol.service

import com.example.equipodefutbol.model.Jugador
import com.example.equipodefutbol.model.PartidoDetalle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // 1. Obtener todos los jugadores de un equipo específico
    @GET("api/jugadores/equipo/{id}")
    suspend fun getJugadoresPorEquipo(@Path("id") equipoId: Int): List<Jugador>

    // 2. Obtener los jugadores que han marcado más de X goles
    @GET("api/jugadores/goleadores")
    suspend fun getTopGoleadores(@Query("minGoles") goles: Int): List<Jugador>

    // 3. Obtener los resultados de todos los partidos con nombres de equipos
    @GET("api/partidos/resultados")
    suspend fun getResultadosPartidos(): List<PartidoDetalle>
}

object RetrofitClient {
    // Recuerda poner aquí la URL de Render cuando tu compañero la tenga lista
    private const val BASE_URL = "https://equipo-futbol-pch9.onrender.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
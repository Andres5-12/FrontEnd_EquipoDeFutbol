package com.example.equipodefutbol.service

import com.example.equipodefutbol.model.Equipo
import com.example.equipodefutbol.model.Jugador
import com.example.equipodefutbol.model.PartidoDetalle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // 1. Obtener todos los jugadores de un equipo específico
    @GET("api/jugadores/equipo/{id}")
    suspend fun getJugadoresPorEquipo(@Path("id") equipoId: Int): List<Jugador>

    // 2. Obtener los jugadores que han marcado más de X goles
    @GET("api/jugadores/goleadores")
    suspend fun getTopGoleadores(@Query("minGoles") goles: Int): List<Jugador>

    // 3. Obtener los resultados de todos los partidos
    @GET("api/partidos/resultados")
    suspend fun getResultadosPartidos(): List<PartidoDetalle>

    // 4. Borrar un equipo
    @DELETE("api/equipos/{id}")
    suspend fun deleteEquipo(@Path("id") equipoId: Int): retrofit2.Response<Unit>

    // 5. Insertar un equipo (NUEVA: Necesaria para que tu repositorio no de error)
    @POST("api/equipos")
    suspend fun postEquipo(@Body equipo: Equipo): retrofit2.Response<Unit>
}

object RetrofitClient {
    private const val BASE_URL = "https://equipo-futbol-pch9.onrender.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
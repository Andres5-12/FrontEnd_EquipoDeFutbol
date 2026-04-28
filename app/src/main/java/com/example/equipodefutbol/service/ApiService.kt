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
    @GET("api/jugadores/equipo/{equipoId}")
    suspend fun getJugadoresPorEquipo(@Path("equipoId") equipoId: Int): List<Jugador>

    @GET("api/jugadores/goleadores")
    suspend fun getTopGoleadores(@Query("minGoles") goles: Int): List<Jugador>

    @GET("api/partidos/detalles")
    suspend fun getResultadosPartidos(): List<PartidoDetalle>

    @DELETE("api/equipos/{id}")
    suspend fun deleteEquipo(@Path("id") id: Int): retrofit2.Response<Unit>

    @POST("api/equipos")
    suspend fun postEquipo(@Body equipo: Equipo): retrofit2.Response<Unit>

    @GET("api/equipos")
    suspend fun getEquipos(): List<Equipo>
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
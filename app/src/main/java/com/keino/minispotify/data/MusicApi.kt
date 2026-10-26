package com.keino.minispotify.data
import retrofit2.http.GET
interface MusicApi {
    @GET("tracks") suspend fun tracks(): List<Track>
    @GET("albums") suspend fun albums(): List<Album>
}

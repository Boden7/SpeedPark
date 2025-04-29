package com.example.speedpark

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val BASE_URL = "http://10.0.2.2:5000/"

interface SpeedParkApi {
    @POST("register")
    fun registerUser(@Body body: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    fun loginUser(@Body body: LoginRequest): Call<TokenResponse>

    @GET("parking_availability")
    fun getParkingAvailability(
        @Header("Authorization") token: String,
        @Query("lot") lot: String = "Main Lot",
        @Query("spots") spots: Int = 5
    ): Call<ParkingAvailabilityResponse>
}

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val username: String,
    val email: String,
    val password: String
)

data class TokenResponse(
    val token: String
)

data class RegisterResponse(
    val message: String? = null,
    val error: String? = null
)

data class ParkingAvailabilityResponse(
    val data: ParkingData,
    val signature: String
)

data class ParkingData(
    val lot_name: String,
    val free_spots: Int,
    val not_free_spots: Int,
    val timestamp: String
)

object RetrofitClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: SpeedParkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpeedParkApi::class.java)
}
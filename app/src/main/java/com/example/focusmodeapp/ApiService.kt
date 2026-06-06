package com.example.focusmodeapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.http.PUT

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<Any>

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("sessions")
    suspend fun createSession(
        @Body request: SessionRequest
    ): Response<Any>

    @GET("sessions/user/{userId}")
    suspend fun getUserSessions(
        @Path("userId") userId: Int
    ): Response<List<SessionResponse>>

    @POST("goals")
    suspend fun createGoal(
        @Body request: GoalRequest
    ): Response<GoalResponse>

    @GET("goals/user/{userId}")
    suspend fun getUserGoals(
        @Path("userId") userId: Int
    ): Response<List<GoalResponse>>

    @DELETE("goals/{id}")
    suspend fun deleteGoal(
        @Path("id") id: Int
    ): Response<Any>

    @GET("reminders/user/{userId}")
    suspend fun getUserReminders(
        @Path("userId") userId: Int
    ): Response<List<ReminderResponse>>

    @GET("badges/user/{userId}")
    suspend fun getUserBadges(
        @Path("userId") userId: Int
    ): Response<List<BadgeResponse>>

    @DELETE("sessions/{id}")
    suspend fun deleteSession(
        @Path("id") id: Int
    ): Response<Any>

    @PUT("goals/{id}")
    suspend fun updateGoal(
        @Path("id") id: Int,
        @Body request: GoalRequest
    ): Response<GoalResponse>
}
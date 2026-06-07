package com.example.focusmodeapp

data class BlockedWebsiteResponse(
    val id: Int,
    val user_id: Int,
    val website_url: String,
    val is_active: Int,
    val created_at: String? = null
)
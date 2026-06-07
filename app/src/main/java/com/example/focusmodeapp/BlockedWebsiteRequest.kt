package com.example.focusmodeapp

data class BlockedWebsiteRequest(
    val user_id: Int,
    val website_url: String,
    val is_active: Int = 1
)
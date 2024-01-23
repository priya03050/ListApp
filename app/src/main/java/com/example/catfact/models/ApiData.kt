package com.example.catfact.models

data class ApiData(
    val `data`: Data=Data(),
    val error: Any="",
    val status: String=""
)
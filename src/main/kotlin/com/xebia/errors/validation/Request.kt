package com.xebia.com.xebia.errors.validation

data class Request(
    val headers: Map<String, String>,
    val queryParams: Map<String, String>,
    val pathParams: Map<String, String>,
)

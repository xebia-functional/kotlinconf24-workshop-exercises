package com.xebia.com.xebia.domain.exercise

val passwordRegex: Regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex()

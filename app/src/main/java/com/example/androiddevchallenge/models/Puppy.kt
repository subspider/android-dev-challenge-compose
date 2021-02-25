package com.example.androiddevchallenge.models

import androidx.annotation.DrawableRes

data class Puppy(
    val id: Int,
    val name: String,
    val age: Int,
    @DrawableRes val dogImage: Int,
    val description: String
)
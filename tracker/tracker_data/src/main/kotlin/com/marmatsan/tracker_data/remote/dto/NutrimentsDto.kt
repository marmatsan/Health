package com.marmatsan.tracker_data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NutrimentsDto(
    @SerialName("carbohydrates_100g")
    val carbohydrates100g: Double?,
    @SerialName("energy-kcal_100g")
    val energyKcal100g: Double?,
    @SerialName("fat_100g")
    val fat100g: Double?,
    @SerialName("proteins_100g")
    val proteins100g: Double?
)
package com.marmatsan.core_domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface ActivityLevel {

    @Serializable
    data object Low : ActivityLevel

    @Serializable
    data object Medium : ActivityLevel

    @Serializable
    data object High : ActivityLevel

}
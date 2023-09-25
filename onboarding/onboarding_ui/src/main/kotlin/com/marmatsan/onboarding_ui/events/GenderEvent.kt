package com.marmatsan.onboarding_ui.events

import com.marmatsan.core_domain.model.Gender

sealed interface GenderEvent : UiEvent {
    data class OnGenderChange(val gender: Gender) : GenderEvent
    data object OnNextClicked : GenderEvent
}
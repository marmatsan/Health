package com.marmatsan.tracker_ui.tracker_overview.components.states

import com.marmatsan.tracker_domain.models.TrackedFood
import com.marmatsan.tracker_ui.models.MealItem
import com.marmatsan.tracker_ui.models.defaultMeals
import java.time.LocalDate

data class TrackerOverviewState(
    val totalCarbs: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCalories: Int = 0,
    val carbsGoal: Int = 0,
    val proteinGoal: Int = 0,
    val fatGoal: Int = 0,
    val caloriesGoal: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val trackedFoods: List<TrackedFood> = emptyList(),
    val meals: List<MealItem> = defaultMeals
)
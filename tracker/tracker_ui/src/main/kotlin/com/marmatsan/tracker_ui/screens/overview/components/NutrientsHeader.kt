package com.marmatsan.tracker_ui.screens.overview.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marmatsan.core_ui.dimensions.LocalSpacing
import com.marmatsan.core_ui.dimensions.theme.CarbColor
import com.marmatsan.core_ui.dimensions.theme.FatColor
import com.marmatsan.core_ui.dimensions.theme.ProteinColor
import com.marmatsan.tracker_domain.R
import com.marmatsan.tracker_ui.components.UnitDisplay
import com.marmatsan.tracker_ui.states.NutrientsHeaderState

@Composable
fun NutrientsHeader(
    state: NutrientsHeaderState,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val totalCaloriesCount = animateIntAsState(
        targetValue = state.totalCalories, label = ""
    )
    val caloriesGoalCount = animateIntAsState(
        targetValue = state.caloricGoal, label = ""
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                horizontal = spacing.spaceLarge,
                vertical = spacing.spaceExtraLarge
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UnitDisplay(
                amount = totalCaloriesCount.value,
                unit = stringResource(id = R.string.kcal),
                amountTextSize = 40.sp,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Column {
                Text(
                    text = stringResource(id = R.string.your_goal),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                UnitDisplay(
                    amount = caloriesGoalCount.value,
                    unit = stringResource(id = R.string.kcal),
                    amountTextSize = 40.sp,
                )
            }
        }
        Spacer(Modifier.height(spacing.spaceSmall))
        NutrientsBar(
            carbs = state.totalCarbs,
            protein = state.totalProtein,
            fat = state.totalFat,
            calories = state.totalCalories,
            calorieGoal = state.caloricGoal
        )
        Spacer(Modifier.height(spacing.spaceLarge))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutrientBarInfo(
                value = state.totalCarbs,
                goal = state.carbsGoal,
                name = stringResource(id = R.string.carbs),
                color = CarbColor
            )
            NutrientBarInfo(
                value = state.totalProtein,
                goal = state.proteinGoal,
                name = stringResource(id = R.string.protein),
                color = ProteinColor
            )
            NutrientBarInfo(
                value = state.totalFat,
                goal = state.fatGoal,
                name = stringResource(id = R.string.fat),
                color = FatColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutrientsHeaderPreview() {
    NutrientsHeader(
        state = NutrientsHeaderState()
    )
}

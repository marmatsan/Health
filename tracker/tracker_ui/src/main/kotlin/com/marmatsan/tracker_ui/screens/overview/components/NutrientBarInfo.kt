package com.marmatsan.tracker_ui.screens.ovewview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.marmatsan.core_ui.dimensions.theme.CarbColor
import com.marmatsan.tracker_domain.R
import com.marmatsan.tracker_ui.components.UnitDisplay

@Composable
fun NutrientBarInfo(
    value: Int,
    goal: Int,
    name: String,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp
) {
    val background = MaterialTheme.colorScheme.background
    val goalExceededColor = MaterialTheme.colorScheme.error

    val angleRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(value) {
        angleRatio.animateTo(
            targetValue = if (goal > 0) {
                value / goal.toFloat()
            } else 0f,
            animationSpec = tween(
                durationMillis = 300
            )
        )
    }

    Box(
        modifier = modifier.size(90.dp), // TODO: Better approach?
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            drawArc(
                color = if (value <= goal) background else goalExceededColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
            if (value <= goal) {
                drawArc(
                    color = color,
                    startAngle = 90f,
                    sweepAngle = 360f * angleRatio.value,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: Reduce boilerplate code in UnitDisplay text color
            UnitDisplay(
                amount = value,
                unit = stringResource(id = R.string.grams),
                amountColor = if (value <= goal) {
                    MaterialTheme.colorScheme.onPrimary
                } else goalExceededColor,
                unitColor = if (value <= goal) {
                    MaterialTheme.colorScheme.onPrimary
                } else goalExceededColor
            )
            Text(
                text = name,
                color = if (value <= goal) {
                    MaterialTheme.colorScheme.onPrimary
                } else goalExceededColor,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutrientBarInfoPreview() {
    NutrientBarInfo(
        modifier = Modifier.padding(16.dp),
        value = 60,
        goal = 100,
        name = stringResource(id = R.string.carbs),
        color = CarbColor
    )
}
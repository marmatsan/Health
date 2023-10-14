package com.marmatsan.tracker_ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.marmatsan.core_ui.dimensions.LocalSpacing
import com.marmatsan.core_ui.event.UiEvent
import com.marmatsan.tracker_domain.R
import com.marmatsan.tracker_domain.models.Meal
import com.marmatsan.tracker_ui.events.SearchEvent
import com.marmatsan.tracker_ui.screens.search.components.SearchTextField
import com.marmatsan.tracker_ui.screens.search.components.TrackableFoodItem
import com.marmatsan.tracker_ui.states.SearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

@OptIn(ExperimentalCoilApi::class)
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    state: SearchState,
    mealName: String,
    date: LocalDate,
    onEvent: (SearchEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyboardController?.hide()
                }

                UiEvent.Success -> TODO()
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(
                    top = spacing.spaceMedium,
                    bottom = spacing.spaceLarge
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.add_meal, mealName.lowercase()),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            SearchTextField(
                modifier = modifier.padding(
                    start = spacing.spaceSmall,
                    end = spacing.spaceSmall
                ),
                text = state.query,
                hintVisible = state.isHintVisible,
                onValueChange = {
                    onEvent(SearchEvent.OnQueryChange(it))
                },
                onSearch = {
                    onEvent(SearchEvent.OnSearch)
                },
                onFocusChanged = {
                    onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
                }
            )
        }
        Spacer(Modifier.height(spacing.spaceMedium))
        if (state.isSearching || state.trackableFoods.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isSearching -> CircularProgressIndicator()
                    state.trackableFoods.isEmpty() -> {
                        Text(
                            text = stringResource(id = R.string.no_results),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.trackableFoods) { trackableFoodUiState ->
                    TrackableFoodItem(
                        modifier = Modifier.fillMaxWidth(),
                        trackableFoodUiState = trackableFoodUiState,
                        onClick = {
                            onEvent(SearchEvent.OnToggleTrackableFood((trackableFoodUiState.food)))
                        },
                        onAmountChange = {
                            onEvent(SearchEvent.OnAmountForFoodChange(food = trackableFoodUiState.food, amount = it))
                        },
                        onTrack = {
                            keyboardController?.hide()
                            onEvent(
                                SearchEvent.OnTrackFoodClick(
                                    food = trackableFoodUiState.food,
                                    meal = when (mealName) {
                                        "Breakfast" -> Meal.Breakfast
                                        "Lunch" -> Meal.Lunch
                                        "Dinner" -> Meal.Dinner
                                        "Snack" -> Meal.Snack
                                        else -> Meal.Breakfast
                                    },
                                    date = date
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        state = SearchState(),
        mealName = "breakfast",
        date = LocalDate.now(),
        onEvent = {},
        uiEvent = flow {},
        snackbarHostState = SnackbarHostState()
    )
}
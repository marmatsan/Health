package com.marmatsan.tracker_domain.repository

import com.marmatsan.tracker_domain.models.TrackableFood
import com.marmatsan.tracker_domain.models.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {

    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<RequestState<List<TrackableFood>>>

    suspend fun insertTrackedFood(trackedFood: TrackedFood)

    suspend fun deleteTrackedFood(trackedFood: TrackedFood)

    fun getFoodsFromCurrentDate(localDate: LocalDate): Flow<List<TrackedFood>>
}
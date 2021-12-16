package com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieDoc(
    val _id: String,
    val name: String,
    val rottenTomatoesScore: Float,
    val runtimeInMinutes: Int,
    val academyAwardNominations: Int,
    val academyAwardWins: Int,
    val boxOfficeRevenueInMillions: Float,
    val budgetInMillions: Int,
) : Parcelable

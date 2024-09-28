package com.interview.xdata.puzzle.api

import com.interview.xdata.puzzle.core.Constraint
import java.io.Serializable

data class PuzzleInputModel(val constraints: List<Constraint>) : Serializable

data class PuzzleOutputModel(val solutions: List<SolutionModel>) : Serializable

data class ErrorModel(val errorMessage: String) : Serializable

data class SolutionModel(val houses: List<ClassicHouseModel>) : Serializable

data class ClassicHouseModel(
    val houseNumber: Int,
    val color: String,
    val nationality: String,
    val drink: String,
    val smoke: String,
    val pet: String
) : Serializable
package com.interview.xdata.puzzle.api

import com.interview.xdata.puzzle.core.Attribute
import com.interview.xdata.puzzle.core.Attribute.*
import com.interview.xdata.puzzle.core.House
import com.interview.xdata.puzzle.core.Solution

class ModelMapper {

    fun toApiModel(solutions: List<Solution>): PuzzleOutputModel {
        return PuzzleOutputModel(solutions = solutions.map { toApiModel(it) })
    }

    private fun toApiModel(model: House): ClassicHouseModel {
        return ClassicHouseModel(
            houseNumber = model.houseNumber,
            color = model.getAttributeValue(COLOR),
            nationality = model.getAttributeValue(NATIONALITY),
            drink = model.getAttributeValue(DRINK),
            smoke = model.getAttributeValue(SMOKE),
            pet = model.getAttributeValue(PET)
        )
    }

    private fun toApiModel(solution: Solution): SolutionModel {
        return SolutionModel(houses = solution.houses.map { toApiModel(it) })
    }

    private fun House.getAttributeValue(attribute: Attribute) =
        this.facts.first { it.attribute == attribute }.value
}
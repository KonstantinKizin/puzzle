package com.interview.xdata.puzzle.api.mapper

import com.interview.xdata.puzzle.api.ClassicHouseModel
import com.interview.xdata.puzzle.api.PuzzleOutputModel
import com.interview.xdata.puzzle.api.SolutionModel
import com.interview.xdata.puzzle.core.Attribute
import com.interview.xdata.puzzle.core.Fact
import com.interview.xdata.puzzle.core.House
import com.interview.xdata.puzzle.core.Solution
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class ModelMapperTest : StringSpec({

    "should map solutions to api model" {
        val solutionSample =
            Solution(
                houses = listOf(
                    House(
                        1,
                        listOf(
                            Fact(Attribute.COLOR, "Red"),
                            Fact(Attribute.PET, "Bird"),
                            Fact(Attribute.NATIONALITY, "Englishman"),
                            Fact(Attribute.SMOKE, "Chesterfield"),
                            Fact(Attribute.DRINK, "Tea")
                        )
                    )
                )
            )

        val solutionModelSample = SolutionModel(
            houses = listOf(
                ClassicHouseModel(
                    houseNumber = 1,
                    color = "Red",
                    nationality = "Englishman",
                    drink = "Tea",
                    smoke = "Chesterfield",
                    pet = "Bird"
                )
            )
        )

        val expected = PuzzleOutputModel(solutions = listOf(solutionModelSample))
        val mapper = ModelMapper()
        val actual = mapper.toApiModel(listOf(solutionSample))

        actual shouldBeEqual expected
    }
})


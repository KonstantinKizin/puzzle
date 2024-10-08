package com.interview.xdata.puzzle.core

import com.interview.xdata.puzzle.core.Attribute.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

// TODO: Enable tests when ClassicZebraPuzzle will be completed
class ClassicZebraPuzzleTest : StringSpec({

    "should find no solutions when there are contradictory constraints".config(enabled = false) {
        val classicZebraPuzzle = ClassicZebraPuzzle()

        val solution = classicZebraPuzzle.findSolutions(
            listOf(
                buildConstraint(NATIONALITY, "Norwegian", Relation.IS, COLOR, "Yellow"),
                buildConstraint(NATIONALITY, "Norwegian", Relation.IS, COLOR, "Red")
            )
        )

        solution.isEmpty() shouldBe true
    }


    "should find multiple solutions when constraints are not restrictive enough".config(enabled = false) {
        val classicZebraPuzzle = ClassicZebraPuzzle()

        val solution = classicZebraPuzzle.findSolutions(
            listOf(
                buildConstraint(HOUSE_NUMBER, "1", Relation.IS, COLOR, "Yellow"),
                buildConstraint(COLOR, "Yellow", Relation.IS, PET, "Bird")
            )
        )

        solution.size shouldBeGreaterThan (1)

    }

    "should process duplicate facts".config(enabled = false) {
        val classicZebraPuzzle = ClassicZebraPuzzle()

        val solutions = classicZebraPuzzle.findSolutions(
            listOf(
                buildConstraint(HOUSE_NUMBER, "1", Relation.IS, COLOR, "Yellow"),
                buildConstraint(NATIONALITY, "Norwegian", Relation.IS, COLOR, "Yellow"),
                buildConstraint(NATIONALITY, "Norwegian", Relation.IS, COLOR, "Yellow")
            )
        )

        solutions.isNotEmpty() shouldBe true
    }

    "should find the same solutions regardless of constraint order".config(enabled = false) {
        val classicZebraPuzzle = ClassicZebraPuzzle()

        val solutions1 = classicZebraPuzzle.findSolutions(
            listOf(
                buildConstraint(HOUSE_NUMBER, "1", Relation.IS, COLOR, "Yellow"),
                buildConstraint(NATIONALITY, "Norwegian", Relation.IS, COLOR, "Yellow"),
            )
        )

        val solutions2 = classicZebraPuzzle.findSolutions(
            listOf(
                buildConstraint(NATIONALITY, "Norwegian", Relation.IS, COLOR, "Yellow"),
                buildConstraint(HOUSE_NUMBER, "1", Relation.IS, COLOR, "Yellow"),
            )
        )

        solutions1 shouldBeEqual solutions2
    }

    "should find the solution for the original puzzle published in Life International".config(enabled = false) {
        val classicZebraPuzzle = ClassicZebraPuzzle()

        val expectedSolution = Solution(
            houses = listOf(
                buildHouse(
                    number = 1,
                    mapOf(
                        COLOR to "Yellow",
                        NATIONALITY to "Norwegian",
                        DRINK to "Water",
                        SMOKE to "Kools",
                        PET to "Fox"
                    )
                ),
                buildHouse(
                    number = 2,
                    mapOf(
                        COLOR to "Blue",
                        NATIONALITY to "Ukrainian",
                        DRINK to "Tea",
                        SMOKE to "Chesterfield",
                        PET to "Horse"
                    )
                ),
                buildHouse(
                    number = 3,
                    mapOf(
                        COLOR to "Red",
                        NATIONALITY to "Englishman",
                        DRINK to "Milk",
                        SMOKE to "Pall Mall",
                        PET to "Bird"
                    )
                ),
                buildHouse(
                    number = 4,
                    mapOf(
                        COLOR to "Ivory",
                        NATIONALITY to "Spaniard",
                        DRINK to "Orange Juice",
                        SMOKE to "Blue Master",
                        PET to "Dog"
                    )
                ),
                buildHouse(
                    number = 5,
                    mapOf(
                        COLOR to "Green",
                        NATIONALITY to "Japanese",
                        DRINK to "Coffee",
                        SMOKE to "Parliaments",
                        PET to "Snails"
                    )
                )
            )
        )

        val solutions = classicZebraPuzzle.findSolutions(
            listOf(
                // 1. The Englishman lives in the red house.
                buildConstraint(NATIONALITY, "Englishman", Relation.IS, COLOR, "Red"),

                // 2. The Spaniard owns the dog.
                buildConstraint(NATIONALITY, "Spaniard", Relation.IS, PET, "Dog"),

                // 3. Coffee is drunk in the green house.
                buildConstraint(DRINK, "Coffee", Relation.IS, COLOR, "Green"),

                // 4. The Ukrainian drinks tea.
                buildConstraint(NATIONALITY, "Ukrainian", Relation.IS, DRINK, "Tea"),

                // 5. The green house is immediately to the left of the ivory house.
                buildConstraint(COLOR, "Green", Relation.LEFT_OF, COLOR, "Ivory"),

                // 6. The Old Gold smoker owns snails.
                buildConstraint(SMOKE, "Old Gold", Relation.IS, PET, "Snails"),

                // 7. Kools are smoked in the yellow house.
                buildConstraint(SMOKE, "Kools", Relation.IS, COLOR, "Yellow"),

                // 8. Milk is drunk in the middle house.
                buildConstraint(HOUSE_NUMBER, "3", Relation.IS, DRINK, "Milk"),

                // 9. The Norwegian lives in the first house.
                buildConstraint(NATIONALITY, "Norwegian", Relation.IS, HOUSE_NUMBER, "1"),

                // 10. The man who smokes Chesterfields lives in the house next to the man with the fox.
                buildConstraint(SMOKE, "Chesterfields", Relation.NEXT_TO, PET, "Fox"),

                // 11. Kools are smoked in the house next to the house where the horse is kept.
                buildConstraint(SMOKE, "Kools", Relation.NEXT_TO, PET, "Horse"),

                // 12. The Lucky Strike smoker drinks orange juice.
                buildConstraint(SMOKE, "Lucky Strike", Relation.IS, DRINK, "Orange Juice"),

                // 13. The Japanese smokes Parliaments.
                buildConstraint(NATIONALITY, "Japanese", Relation.IS, SMOKE, "Parliaments"),

                // 14. The Norwegian lives next to the blue house.
                buildConstraint(NATIONALITY, "Norwegian", Relation.NEXT_TO, COLOR, "Blue")
            )
        )

        solutions shouldBeEqual listOf(expectedSolution)
    }
})

fun buildHouse(number: Int, values: Map<Attribute, String>): House {
    return House(houseNumber = number, facts = values.map { Fact(it.key, it.value) })
}

fun buildConstraint(
    leftAttr: Attribute,
    leftVal: String,
    relation: Relation,
    rightAttr: Attribute,
    rightVal: String
) = Constraint(left = Fact(leftAttr, leftVal), relation, right = Fact(rightAttr, rightVal))
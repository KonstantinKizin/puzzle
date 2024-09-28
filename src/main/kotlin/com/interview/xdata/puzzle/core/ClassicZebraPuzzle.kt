package com.interview.xdata.puzzle.core

import kotlin.math.abs

class ClassicZebraPuzzle {

    fun findSolutions(statements: List<Statement>): List<Solution> {
        val attributes = Attribute.classicValues().toList()
        val tableWidth = Attribute.HOUSE_NUMBER.possibleValues.size

        return generatePossibleSolutions(tableWidth, attributes)
            .filter { checkPossibleSolution(it, statements) }
    }

    private fun checkPossibleSolution(solution: Solution, statements: List<Statement>) =
        statements.all { statement -> doesSolutionSatisfyStatement(statement, solution) }

    private fun generatePossibleSolutions(tableWidth: Int, attributes: List<Attribute>): List<Solution> {
        // TODO: Implement generation logic
        return emptyList()
    }

    private fun doesSolutionSatisfyStatement(statement: Statement, solution: Solution): Boolean {
        val leftFactHouse = findHouseByFact(statement.left, solution.houses)

        val rightFactHouse = findHouseByFact(statement.right, solution.houses)

        if (leftFactHouse == null || rightFactHouse == null) {
            return false
        }

        return when (statement.relation) {
            Relation.IS -> leftFactHouse.houseNumber == rightFactHouse.houseNumber
            Relation.NEXT_TO -> abs(leftFactHouse.houseNumber - rightFactHouse.houseNumber) == 1
            Relation.LEFT_OF -> leftFactHouse.houseNumber + 1 == rightFactHouse.houseNumber
        }
    }

    private fun findHouseByFact(fact: Fact, houses: List<House>): House? {
        if (fact.attribute == Attribute.HOUSE_NUMBER) {
            return houses.firstOrNull { it.houseNumber == fact.value.toInt() }
        }
        return houses.find { house ->
            house.facts.any { it.attribute == fact.attribute && it.value == fact.value }
        }
    }

}
package com.interview.xdata.puzzle.core

data class Fact(val attribute: Attribute, val value: String)

enum class Relation {
    IS,
    NEXT_TO,
    LEFT_OF,
}

data class Constraint(val left: Fact, val relation: Relation, val right: Fact)

enum class Attribute(val possibleValues: Set<String>) {

    COLOR(setOf("Yellow", "Blue", "Red", "Ivory", "Green")),
    PET(setOf("Dog", "Bird", "Cat", "Snails", "Horse")),
    NATIONALITY(setOf("Norwegian", "Ukrainian", "Englishman", "Spaniard", "Japanese")),
    DRINK(setOf("Water", "Tea", "Milk", "Orange Juice", "Coffee")),
    SMOKE(setOf("Kools", "Chesterfield", "Old Gold", "Lucky Strike", "Parliament")),
    HOUSE_NUMBER(setOf("1", "2", "3", "4", "5"));

    companion object {
        fun classicValues(): Set<Attribute> = setOf(COLOR, PET, NATIONALITY, DRINK, SMOKE)
    }
}

data class House(val houseNumber: Int, val facts: List<Fact>)

data class Solution(val houses: List<House>)

package com.interview.xdata.puzzle.api.io

import com.interview.xdata.puzzle.core.*
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class JsonFileInputModelReaderTest : StringSpec({

    "should read input file" {
        val reader = JsonFileInputModelReader()

        val inputModel = reader.readModel(
            arrayOf(
                getResourcePath("input.json")
            )
        )

        inputModel.constraints.toSet() shouldBeEqual setOf(
            Constraint(
                left = Fact(attribute = Attribute.PET, value = "Bird"),
                relation = Relation.IS,
                right = Fact(attribute = Attribute.HOUSE_NUMBER, value = "1")
            ),
            Constraint(
                left = Fact(attribute = Attribute.COLOR, value = "Blue"),
                relation = Relation.IS,
                right = Fact(attribute = Attribute.HOUSE_NUMBER, value = "1")
            )
        )
    }

    "should throw ValidationException when args are empty" {
        val reader = JsonFileInputModelReader()

        shouldThrowExactly<ValidationException> {
            reader.readModel(arrayOf())
        }
    }

    "should throw ValidationException when JSON is not valid" {
        val reader = JsonFileInputModelReader()

        shouldThrowExactly<ValidationException> {
            reader.readModel(arrayOf(getResourcePath("invalid_input.json")))
        }
    }

    "should throw ValidationException when input file does not exist" {
        val reader = JsonFileInputModelReader()

        shouldThrowExactly<ValidationException> {
            reader.readModel(arrayOf("input1.json"))
        }
    }
})

fun getResourcePath(fileName: String): String {
    return JsonFileInputModelReaderTest::class.java.getResource("/${fileName}")!!.path
}

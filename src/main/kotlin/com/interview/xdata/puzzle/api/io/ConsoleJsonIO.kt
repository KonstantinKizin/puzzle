package com.interview.xdata.puzzle.api.io

import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.interview.xdata.puzzle.api.PuzzleInputModel
import com.interview.xdata.puzzle.core.ValidationException
import java.io.File
import java.io.IOException
import java.io.Serializable


private val writer: ObjectWriter = jacksonObjectMapper().writer().withDefaultPrettyPrinter()
private val reader: ObjectReader = jacksonObjectMapper().reader()

class ConsoleJsonOutputWriter : OutputWriter {
    override fun write(outputModel: Serializable) {
        println(writer.writeValueAsString(outputModel))
    }
}

class JsonFileInputModelReader : InputModelReader {

    override fun readModel(args: Array<String>): PuzzleInputModel {
        if (args.isEmpty()) {
            throw ValidationException("No input present")
        }

        val inputFile = File(args[0])

        if (!inputFile.exists()) {
            throw ValidationException("Input ${args[0]} is not exists")
        }

        if (!inputFile.isFile) {
            throw ValidationException("Input ${args[0]} is not a file")
        }

        return try {
            reader.readValue(inputFile, PuzzleInputModel::class.java);
        } catch (ex: IOException) {
            throw ValidationException("Can't recognize model ${args[0]} : ${ex.message}")
        }
    }
}

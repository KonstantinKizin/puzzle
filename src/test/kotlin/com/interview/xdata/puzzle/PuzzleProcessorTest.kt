package com.interview.xdata.puzzle

import com.interview.xdata.puzzle.api.ErrorHandler
import com.interview.xdata.puzzle.api.ModelMapper
import com.interview.xdata.puzzle.api.PuzzleInputModel
import com.interview.xdata.puzzle.api.PuzzleOutputModel
import com.interview.xdata.puzzle.api.io.InputModelReader
import com.interview.xdata.puzzle.api.io.OutputWriter
import com.interview.xdata.puzzle.core.*
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import java.lang.NullPointerException

class PuzzleProcessorTest : StringSpec({
    val modelMapper = mockk<ModelMapper>()
    val outputWriter = mockk<OutputWriter>()
    val inputModelReader = mockk<InputModelReader>()
    val errorHandler = mockk<ErrorHandler>()
    val puzzle = mockk<ClassicZebraPuzzle>()
    val outputModel = mockk<PuzzleOutputModel>()
    val processor = PuzzleProcessor(modelMapper, outputWriter, inputModelReader, errorHandler, puzzle)

    val sampleInputModel = PuzzleInputModel(
        statements = listOf(
            Statement(
                left = Fact(Attribute.COLOR, "Red"),
                relation = Relation.IS,
                right = Fact(Attribute.PET, "Bird")
            )
        )
    )


    beforeTest {
        every {
            inputModelReader.readModel(arrayOf("statements.json"))
        } returns sampleInputModel

        every {
            puzzle.findSolutions(sampleInputModel.statements)
        } returns listOf(mockk())

        every {
            modelMapper.toApiModel(any())
        } returns outputModel

        justRun {
            errorHandler.handleError(any())
        }
    }

    "should successfully process input file" {
        justRun {
            outputWriter.write(any())
        }

        processor.process(arrayOf("statements.json"))

        verify { outputWriter.write(outputModel) }

    }

    "should handle client errors" {
        val exception = ValidationException("Client mistake")

        every {
            inputModelReader.readModel(arrayOf("statements.json"))
        } throws exception

        processor.process(arrayOf("statements.json"))

        verify {
            errorHandler.handleError(exception)
        }
    }

    "should handle system errors" {
        val exception = NullPointerException("System mistake")

        every {
            puzzle.findSolutions(any())
        } throws exception

        processor.process(arrayOf("statements.json"))

        verify {
            errorHandler.handleError(exception)
        }
    }

})
package com.interview.xdata.puzzle.api

import com.interview.xdata.puzzle.api.io.InputModelReader
import com.interview.xdata.puzzle.api.io.OutputWriter
import com.interview.xdata.puzzle.api.mapper.ModelMapper
import com.interview.xdata.puzzle.core.ClassicZebraPuzzle

class PuzzleProcessor(
    private val modelMapper: ModelMapper,
    private val outputWriter: OutputWriter,
    private val inputModelReader: InputModelReader,
    private val errorHandler: ErrorHandler,
    private val puzzle: ClassicZebraPuzzle
) {

    fun process(args: Array<String>) {
        try {
            val inputModel = inputModelReader.readModel(args)
            val solutions = puzzle.findSolutions(inputModel.statements)
            outputWriter.write(modelMapper.toApiModel(solutions))
        } catch (throwable: Throwable) {
            errorHandler.handleError(throwable)
        }
    }
}
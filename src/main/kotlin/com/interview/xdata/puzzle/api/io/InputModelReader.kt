package com.interview.xdata.puzzle.api.io

import com.interview.xdata.puzzle.api.PuzzleInputModel

interface InputModelReader {
    fun readModel(args: Array<String>): PuzzleInputModel
}
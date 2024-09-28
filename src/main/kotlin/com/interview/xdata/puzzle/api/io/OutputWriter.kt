package com.interview.xdata.puzzle.api.io

import java.io.Serializable

interface OutputWriter {
    fun write(outputModel: Serializable)
}
import com.interview.xdata.puzzle.api.ErrorHandler
import com.interview.xdata.puzzle.api.PuzzleInputModel
import com.interview.xdata.puzzle.api.PuzzleProcessor
import com.interview.xdata.puzzle.api.io.ConsoleJsonOutputWriter
import com.interview.xdata.puzzle.api.io.JsonFileInputModelReader
import com.interview.xdata.puzzle.api.mapper.ModelMapper
import com.interview.xdata.puzzle.core.Solution
import com.interview.xdata.puzzle.core.ClassicZebraPuzzle

fun main(args: Array<String>) {
    val writer = ConsoleJsonOutputWriter()
    val reader = JsonFileInputModelReader()
    val errorHandler = ErrorHandler(writer)
    val modelMapper = ModelMapper()
    val puzzle = ClassicZebraPuzzle()
    val puzzleProcessor = PuzzleProcessor(modelMapper, writer, reader, errorHandler, puzzle)

    puzzleProcessor.process(args)

}
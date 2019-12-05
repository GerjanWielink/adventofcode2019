import kotlin.math.abs
import kotlin.streams.toList

fun getInstructions(): List<List<String>> = Main::class.java.getResourceAsStream("daythreeinput.txt")
    .bufferedReader()
    .lines()
    .map { line -> line.split(",") }
    .toList()


data class Node(val x: Int, val y: Int)

fun manhattanDistance(first: Node, second: Node): Int =
    abs(first.x - second.x) + abs(first.y - second.y)

fun extendPath(current: Node, delta: Node) : Node {
    return Node(current.x + delta.x, current.y + delta.y)
}

fun getDeltaVector(instruction: Char): Node = when(instruction) {
    'U' -> Node(0, 1)
    'D' -> Node(0, -1)
    'R' -> Node(1, 0)
    'L' -> Node(-1, 0)
    else -> throw Exception("Invalid instruction $instruction")
}

fun MutableList<Node>.addInstruction(instruction: String) : MutableList<Node> {
    val deltaNode: Node = getDeltaVector(instruction.first())
    val length: Int = instruction.drop(1).toInt()

    for (i in 1 .. length) {
        this.add(extendPath(this.last(), deltaNode))
    }

    return this
}

fun getPath (instructions: List<String>) : MutableList<Node>  {
    val path = mutableListOf(Node(0, 0))

    instructions.forEach { instruction -> path.addInstruction(instruction) }

    return path
}

fun dayThreePartOne(): Int {
    val centralPort = Node(0, 0)

    val instructions = getInstructions()

    val closestIntersection = getIntersections(getPath(instructions[0]), getPath(instructions[1]))
        .toList().minBy { manhattanDistance(centralPort, it) } ?: throw Exception("Empty list provided")

    return manhattanDistance(centralPort, closestIntersection)
}

fun dayThreePartTwo(): Int {
    val centralPort = Node(0, 0)

    val instructions = getInstructions()

    val pathOne = getPath(instructions[0])
    val pathTwo = getPath(instructions[1])

    val closestIntersection = getIntersections(pathOne, pathTwo)
        .toList()
        .minBy { intersection -> pathOne.indexOf(intersection) + pathTwo.indexOf(intersection) }

    return pathOne.indexOf(closestIntersection) + pathTwo.indexOf(closestIntersection)
}

fun getIntersections(pathOne: List<Node>, pathTwo: List<Node>): Set<Node> = pathOne
    .filter { node -> node != Node(0, 0) }
    .toSet()
    .intersect(
        pathTwo.filter { node -> node !=  Node(0, 0) }
    )


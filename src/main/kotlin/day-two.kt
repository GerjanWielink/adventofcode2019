import java.lang.Exception

/**
 * Read the input masses from the input file
 */
fun getInputOpCode(): MutableList<Int> = Main::class.java.getResource("daytwoinput.txt")
    .readText()
    .split(",")
    .map { value -> value.toInt(10) }
    .toMutableList()

fun MutableList<Int>.interpretOpCode(position: Int = 0): List<Int> =
    when (this[position]) {
        1 -> {
            val first = this[this[position + 1]]
            val second = this[this[position + 2]]

            this[this[position + 3]] = first + second

            this.interpretOpCode(position + 4)
        }
        2 -> {
            val first = this[this[position + 1]]
            val second = this[this[position + 2]]

            this[this[position + 3]] = first * second

            this.interpretOpCode(position + 4)
        }
        99 -> this
        else -> throw Exception("Invalid instruction: ${this[position]}")
    }

fun MutableList<Int>.setNounAndVerb(noun: Int, verb: Int) : MutableList<Int> {
    this[1] = noun
    this[2] = verb

    return this
}



fun findTarget(target: Int): Pair<Int, Int> {
    for (noun in 0 .. 99) {
        for (verb in 0 ..99) {
            if (getInputOpCode().setNounAndVerb(noun, verb).interpretOpCode()[0] == target) {
                return Pair(noun, verb)
            }
        }
    }

    throw Exception("Target not found")
}

fun dayTwo(): Int {
    val res = findTarget(19690720)

    return (100 * res.first) + res.second
}
fun String.isValidPassword () =
    this.matches(Regex("\\d{6}")) &&
    this.matches(Regex("0*1*2*3*4*5*6*7*8*9*")) &&
    this.matches(Regex(".*(\\d)\\1.*"))

fun String.isValidPasswordTwo() =
    this.isValidPassword() &&
    this.matches(Regex(".*(?:^|(.)(?!\\1))(\\d)\\2(?!\\2).*"))

fun dayFourPartOne(): Int =
    (347312 .. 805915)
        .toList()
        .filter { it
            .toString()
            .isValidPassword()
        }.size

fun dayFourPartTwo(): Int =
    (347312 .. 805915)
        .toList()
        .filter { it
            .toString()
            .isValidPasswordTwo()
        }.size

fun main() {
    print(dayFourPartTwo())
}
import kotlin.streams.toList

/**
 * Read the input masses from the input file
 */
fun getMassesFromInput(): List<Long> = Main::class.java.getResourceAsStream("inputdayone.txt")
        .bufferedReader()
        .lines()
        .map { line -> line.toLong(10) }
        .toList()

/**
 * Calculate the fuel requirement for a given mass
 */
fun calculateFuelFromMass(mass: Long): Long = (mass / 3) - 2

/**
 * Recursively get the total required fuel input for a mass, e.g. taking into account the additional mass from the fuel
 */
fun getTotalFuelForMass(mass: Long): Long {
    val fuelForMass = calculateFuelFromMass(mass)
    val fuelForFuel = calculateFuelFromMass(fuelForMass)

    if (fuelForFuel < 1) {
        return fuelForMass
    }

    return fuelForMass + getTotalFuelForMass(fuelForMass)
}

/**
 * Complete day one exercise
 */
fun dayOne() : Long = getMassesFromInput()
    .map(::getTotalFuelForMass)
    .sum()

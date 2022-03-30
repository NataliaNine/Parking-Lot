package parking

var parkingLot: MutableMap<Int, Car> = mutableMapOf()
var maxSpotNum = -1

fun main() {
    var input = readLine()!!
    while (input != "exit") {
        val parts = input.split(Regex("\\s+"))

        when (parts[0]) {
            "create" -> {
                if (parts.size == 2 && isNumber(parts[1])) {
                    createLot(parts)
                } else
                    invalidCommand(input)

            }
            "park" -> {
                if (maxSpotNum < 1)
                    println("Sorry, a parking lot has not been created.")
                else if (parts.size == 3)
                    park(Car(parts[1], parts[2]))
                else
                    invalidCommand(input)
            }

            "leave" ->
                if (maxSpotNum < 1)
                    println("Sorry, a parking lot has not been created.")
                else if (parts.size == 2 && isNumber(parts[1]))
                    leave(parts[1].toInt())
                else
                    invalidCommand(input)


            "status" ->
                if (maxSpotNum < 1)
                    println("Sorry, a parking lot has not been created.")
                else
                    printLotStatus()

            "reg_by_color" -> {
                if (maxSpotNum < 1)
                    println("Sorry, a parking lot has not been created.")
                else
                    regByColor(parts[1])
            }

            "spot_by_color" -> {
                if (maxSpotNum < 1)
                    println("Sorry, a parking lot has not been created.")
                else
                    spotByColor(parts[1])
            }

            "spot_by_reg" -> {
                if (maxSpotNum < 1)
                    println("Sorry, a parking lot has not been created.")
                else
                    spotByReg(parts[1])
            }

        }

        input = readLine()!!
    }
}

fun spotByReg(reg: String) {
    val spots = parkingLot.filter { reg == it.value.licensePlate }
        .toList().map{ "${it.first}"}
    if (spots.isEmpty())
        println("No cars with registration number $reg were found.")
    else
        println(spots.joinToString(", "))
}

fun spotByColor(color: String) {
    val spots = parkingLot.filter { color.uppercase() == it.value.color.uppercase() }
        .toList().map { "${it.first}"}
    if (spots.isEmpty())
        println("No cars with color $color were found.")
    else
        println(spots.joinToString(", "))
}

fun regByColor(color: String) {
    val spots = parkingLot.filter { color.uppercase() == it.value.color.uppercase() }
        .toList().map { "${it.second.licensePlate}"}
    if (spots.isEmpty())
        println("No cars with color $color were found.")
    else
        println(spots.joinToString(", "))
}

private fun createLot(parts: List<String>) {
    parkingLot = mutableMapOf()
    maxSpotNum = parts[1].toInt()
    println("Created a parking lot with $maxSpotNum spots.")
}

fun printLotStatus() {
    if (parkingLot.isEmpty())
        println("Parking lot is empty.")
    else
        parkingLot.forEach {  entry -> println("${entry.key} ${entry.value.licensePlate} ${entry.value.color}")}
}

private fun isNumber(inp: String) = inp.matches(Regex("\\d+"))

fun leave(spotNum: Int) {
    if (spotNum < 1 || spotNum > maxSpotNum)
        println("Invalid Spot Number: $spotNum")
    else {
        val unparkedCar = parkingLot.remove(spotNum)
        if (unparkedCar != null) {
            println("Spot $spotNum is free.")
        } else {
            println("There is no car in spot $spotNum.")
        }
    }
}

private fun invalidCommand(input: String) {
    println("ERROR Invalid command: $input")
}

fun park(car: Car) {
    for (i in 1..maxSpotNum) {
        if (parkingLot[i] == null) {
            parkingLot[i] = car
            println("${car.color} car parked in spot $i.")
            return
        }
    }

    println("Sorry, the parking lot is full.")
}


class Car(val licensePlate: String, val color: String)
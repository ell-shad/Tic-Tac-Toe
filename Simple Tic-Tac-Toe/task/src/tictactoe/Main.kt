package tictactoe

var previous = 'o'
fun main() {
    val input = "         "
    val matrix = matrixCreator(input)
    draw(matrix)
    play(matrix)
}

//main draw function, calling every time table updated
fun draw(list: MutableList<MutableList<Char>>) {
    print("---------\n")
    for (i in list.indices) for (j in list[i].indices) when (j) {
        0 -> print("| ${list[i][j]} ")
        2 -> println("${list[i][j]} |")
        else -> print("${list[i][j]} ")
    }
    print("---------\n")
}

//play function controls the loop till game finishes
fun play(list: MutableList<MutableList<Char>>) {
    var gameOver = false
    while (!gameOver) gameOver = inputCheck(readln(), list)
}

//checking user input if correct
fun inputCheck(input: String, list: MutableList<MutableList<Char>>): Boolean {
    input.replace(" ", "", true).forEach {
        if (!it.isDigit()) {
            print("You should enter numbers!\n")
            return false
        }
    }
    if (input.length > 3 || input.length < 2) {
        print("You should enter numbers!\n")
        return false
    } else if (input[0].digitToInt() > 3 || input[0].digitToInt() < 1 || input[2].digitToInt() > 3 || input[2].digitToInt() < 1) {
        print("Coordinates should be from 1 to 3!\n")
        return false
    } else {
        val x = input[0].digitToInt() - 1
        val y = input[2].digitToInt() - 1
        return if (list[x][y] != ' ') {
            print("This cell is occupied! Choose another one!\n")
            false
        } else {
            if (previous == 'x') {
                list[x][y] = 'O'
                previous = 'o'
            } else {
                list[x][y] = 'X'
                previous = 'x'
            }
            draw(list)
            val sum = check(list)
            return sum.contains(". wins".toRegex()) || sum == "Draw"
        }
    }
}

//creates empty table
fun matrixCreator(input: String): MutableList<MutableList<Char>> {
    val list = mutableListOf(
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' ')
    )
    var counter = 0
    for (i in list.indices) list[i].indices.forEach {
        list[i][it] = input[counter]
        counter++
    }
    return list
}

//win check function
fun check(list: MutableList<MutableList<Char>>): String {
    var xCount = 0
    var yCount = 0
    //the loop counts 'x's & 'o's for draw case
    list.indices.forEach {
        list[it].indices.forEach { j ->
            if (list[it][j] == 'X') xCount++
            else if (list[it][j] == 'O') yCount++
        }
    }
    //horizontal check
    for (i in list.indices) if (list[i][0] != ' ' && (list[i][0] == list[i][1] && list[i][0] == list[i][2])) {
        println("${list[i][0]} wins")
        return "${list[i][0]} wins"
    }
    //vertical check
    else if (list[0][i] != ' ' && (list[0][i] == list[1][i] && list[0][i] == list[2][i])) {
        println("${list[0][i]} wins")
        return "${list[0][i]} wins"
    }
    //diagonal check
    return when {
        list[0][0] != ' ' && (list[0][0] == list[1][1] && list[0][0] == list[2][2]) -> {
            println("${list[0][0]} wins")
            "${list[0][0]} wins"
        }

        list[0][2] != ' ' && (list[0][2] == list[1][1] && list[0][2] == list[2][0]) -> {
            println("${list[0][2]} wins")
            "${list[0][2]} wins"
        }
        //draw
        xCount + yCount == 9 -> {
            println("Draw")
            "Draw"
        }

        else -> ""
    }
}
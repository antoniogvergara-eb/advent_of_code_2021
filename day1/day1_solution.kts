import java.io.File
import kotlin.io.println
import kotlin.math.min

val INCREMENT = "increment"
val DECREMENT = "decrement"
val SAME = "same"


fun countIncrementAndDecrements(list: MutableList<Pair<Int, String>>): Triple<Int, Int, Int>{
    var numIncrements: Int = 0
    var numDecrements: Int = 0
    var numSame: Int = 0

    for (pair in list){
        var (_, value) = pair
        if (value == INCREMENT){
            numIncrements = numIncrements + 1
        }else if (value == DECREMENT){
            numDecrements = numDecrements + 1
        }else if (value == SAME){
            numSame = numSame + 1
        }
    }
    return Triple(numIncrements, numDecrements, numSame)
}
fun getIncrementAndDecrementSlidingWindow(lines: MutableList<Int>): MutableList<Pair<Int, String>>{
    var actualSum: Int = -1
    var lastSum: Int = -1
    var incrementList: MutableList<Pair<Int, String>> = mutableListOf()
    var allWindows: List<List<Int>> = lines.windowed(size = 3, step = 1, partialWindows = false)

    for (window in allWindows){
        if (actualSum == -1 && lastSum == -1){
            actualSum = window.sum()
            incrementList.add(Pair(actualSum, "N/A"))
            continue
        }
        if (window.size == 3){
            actualSum = window.sum()
            if (actualSum > lastSum){
                incrementList.add(Pair(actualSum, INCREMENT))
            }else if(actualSum < lastSum){
                incrementList.add(Pair(actualSum, DECREMENT))
            }else{
                incrementList.add(Pair(actualSum, SAME))
            }
            lastSum = actualSum
        }else{
            break
        }
    }
    return incrementList
}

fun getIncrementsAndDecrements(lines: MutableList<Int>):  MutableList<Pair<Int, String>>{
    var actualLine: Int = -1
    var lastLine: Int = -1
    var incrementList: MutableList<Pair<Int, String>> = mutableListOf()
    for (line in lines){
        if (actualLine == -1 && lastLine == -1){
            incrementList.add(Pair(line, "N/A"))
            actualLine = line
            continue
        }
        actualLine = line
        if (actualLine > lastLine){
            incrementList.add(Pair(actualLine, INCREMENT))
        }else if(actualLine < lastLine){
            incrementList.add(Pair(actualLine, DECREMENT))
        }else{
            incrementList.add(Pair(actualLine, SAME))
        }
        lastLine = actualLine
    }
    return incrementList
}

fun main(){
    val filePath = "./input.txt"
    val lineList = mutableListOf<Int>()
    File(filePath).useLines { lines -> lines.forEach { lineList.add(it.toInt()) }}
    val incrementalList = getIncrementsAndDecrements(lineList)
    val incrementalSlidingWindow = getIncrementAndDecrementSlidingWindow(lineList)
    val (numIncrements, numDecrements, numSame) = countIncrementAndDecrements(incrementalList)
    val (numIncrementsSlide, numDecrementsSlide, numSameSlide) = countIncrementAndDecrements(incrementalSlidingWindow)
    var length = lineList.size
    println("Num of lines in file $length")
    println("-------------------------------")
    println(incrementalList)
    println("Num Increments: $numIncrements")
    println("Num Decrements: $numDecrements")
    println("Num Same: $numSame")
    println("-------------------------------")
    println(incrementalSlidingWindow)
    println("Num Increments: $numIncrementsSlide")
    println("Num Decrements: $numDecrementsSlide")
    println("Num Same: $numSameSlide")
}

main()
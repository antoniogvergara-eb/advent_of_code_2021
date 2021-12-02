import java.io.File
import kotlin.io.println
import kotlin.text.split
import kotlin.text.set

val HORIZONTAL_POS = "horizontal_pos"
val DEPTH = "depth"
val DEPTH_AIM = "depth_aim"
val AIM = "aim"

fun calculatePosAndDepth(positions: List<Pair<String, Int>>): MutableMap<String, Int>{
    var finalPosition: MutableMap<String, Int> = mutableMapOf(
        HORIZONTAL_POS to 0,
        DEPTH to 0,
        AIM to 0,
        DEPTH_AIM to 0,
    )
    var calcPos: Int
    var calcAim: Int

    for (position in positions){
        var (pos,value) = position
        if(pos == "forward"){
            calcPos = value + finalPosition.getValue(HORIZONTAL_POS)
            calcAim = value * finalPosition.getValue(AIM)
            finalPosition[HORIZONTAL_POS] = calcPos
            finalPosition[DEPTH_AIM] = finalPosition[DEPTH_AIM]!! + calcAim
        }else{
            calcPos = value + finalPosition.getValue(DEPTH)
            finalPosition[DEPTH] = calcPos
            finalPosition[AIM] = calcPos
        }
    }
    return finalPosition
}

fun parseInputFile(line: String, delimiter: String): Pair<String, Int>{
    var splittedLine: MutableList<String> = line.split(delimiter).toMutableList()
    if (splittedLine[0] == "up"){
        splittedLine[1] = (splittedLine[1].toInt() * -1).toString()
    }
    return Pair(splittedLine[0], splittedLine[1].toInt())
}

fun main() {
    val filePath = "./input.txt"
    val delim = " "
    val lineList = mutableListOf<Pair<String, Int>>()
    File(filePath).useLines { lines -> lines.forEach { lineList.add(parseInputFile(it, delim))} }
    println(lineList)
    val finalPosition: MutableMap<String, Int> = calculatePosAndDepth(lineList)
    println("Final position: $finalPosition")
    val finalPosCalc: Int = finalPosition[HORIZONTAL_POS]!!.times(finalPosition[DEPTH]!!)
    println("Final Pos Calculation: $finalPosCalc")
    println("-------------------------------")
    val finalPosAimCalc: Int = finalPosition[HORIZONTAL_POS]!!.times(finalPosition[DEPTH_AIM]!!)
    println("Final Pos Aim Calculation: $finalPosAimCalc")
}

main()
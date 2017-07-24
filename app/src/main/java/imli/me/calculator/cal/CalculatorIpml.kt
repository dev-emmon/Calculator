package imli.me.calculator.cal

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout

/**
 * Created by Doots on 2017/7/19.
 */

class CalculatorIpml : BasicCalculator, SeniorCalculator {

    val TAG: String = "CalculatorIpml"

    val ERROR: String = "ERROR"
    val BASIC_TEXT_RESULT = ""
    val BASIC_TEXT_EQUATION = "0"

    // 计算公式
    var equation = BASIC_TEXT_EQUATION
    var result = BASIC_TEXT_RESULT
    // 运算符号
    var symbolAdd: String = "+"
    var symbolSub: String = "-"
    var symbolMul: String = "*"
    var symbolDivider: String = "/"
    var regex = "[$symbolMul|$symbolDivider|$symbolAdd|\\$symbolSub]".toRegex()
    var symbolPoint: String = "."

    // 监听
    var calculatorListener : OnCalculatorListener? = null

    constructor(symbolAdd: String, symbolSub: String, symbolMul: String, symbolDivider: String) {
        this.symbolAdd = symbolAdd
        this.symbolSub = symbolSub
        this.symbolMul = symbolMul
        this.symbolDivider = symbolDivider
        this.regex = "[$symbolMul|$symbolDivider|$symbolAdd|\\$symbolSub]".toRegex()
    }

    fun setOnCalculatorListener(listener: OnCalculatorListener) {
        calculatorListener = listener
    }

    /**
     * 添加公式
     */
    fun addEquation(str: String) {
        if (TextUtils.isEmpty(str)) return
        var newEquation = if (equation.equals(BASIC_TEXT_EQUATION) && !isComputerSymbol(str)) "" else equation
        if (isComputerSymbol(str)) {
            newEquation = addEquationSymbol(newEquation, str)
        } else if (isPoint(str)) {
            // 如果是点，则判断前面是否有数字
            newEquation = addEquationPoint(newEquation, str)
        } else {
            // 如果是数字，则直接添加
            newEquation = addOther(newEquation, str)
        }
        // 通知改变公式
        notifyEquation(newEquation)
    }

    /**
     * 添加运算符
     */
    fun addEquationSymbol(eq: String, str: String): String {
        var tmpEquation = eq
        // 如果是运算符号，则先判断最后一个字符是否是运算符号，或者是点
        val lastChar: String = tmpEquation[tmpEquation.lastIndex] + ""
        if (lastChar.equals(symbolPoint)) {
            // 如果是点，则替换 . 字符
            tmpEquation = replaceLastStr(tmpEquation, lastChar, str)
        } else if (isComputerSymbol(lastChar)) {
            // 如果是运算符号, 并且添加的字符是减号
            if (str.equals(symbolSub)) {
                if (lastChar.equals(symbolAdd)) {
                    // 本身为加号
                    tmpEquation = replaceLastStr(tmpEquation, lastChar, str)
                } else if (!lastChar.equals(symbolSub)) {
                    // 本身为非减号
                    tmpEquation += str
                } else {
                }
            }
        } else {
            tmpEquation += str
        }
        return tmpEquation
    }

    /**
     * 添加点
     */
    fun addEquationPoint(eq: String, str: String): String {
        var tmpEquation = eq
        val lastChar: String = tmpEquation[tmpEquation.lastIndex] + ""
        if (!isPoint(lastChar)) {
            val nums: List<String> = equation.split(regex)
            if (!nums[nums.size - 1].contains(symbolPoint)) {
                tmpEquation += str
            }
        }
        return tmpEquation
    }

    /**
     * 添加其它公式
     */
    fun addOther(eq: String, str: String): String {
        var tmpEquation = eq
        tmpEquation += str
        return tmpEquation
    }

    /**
     * 替换末尾的字符串
     */
    fun replaceLastStr(str: String, oldStr: String, newStr: String): String {
        val index = str.lastIndexOf(oldStr)
        if (index != -1) {
            return str.replaceRange(index, str.length, newStr)
        }
        return str
    }

    fun calculator() {
        this.calculator(equation)
    }

    /**
     * 是否为 .
     */
    fun isPoint(str: String): Boolean {
        return symbolPoint.equals(str)
    }

    /**
     * 是否为运算符号
     */
    fun isComputerSymbol(str: String): Boolean {
        return str.equals(symbolAdd) || str.equals(symbolSub) || str.equals(symbolMul) || str.equals(symbolDivider)
    }

    /**
     * 通知公式
     */
    fun notifyEquation(str: String) {
        equation = str
        calculatorListener?.onEquationChange(str)
    }

    /**
     * 通知计算结果
     */
    fun notifyResult(str: String) {
        result = str
        calculatorListener?.onCalculatorResult(str)
    }

    override fun add(num1: Double, num2: Double): Double {
        return num1 + num2
    }

    override fun sub(num1: Double, num2: Double): Double {
        return num1 - num2
    }

    override fun mul(num1: Double, num2: Double): Double {
        return num1 * num2
    }

    override fun divided(num1: Double, num2: Double): Double {
        return num1 / num2
    }

    override fun ac() {
        notifyEquation(BASIC_TEXT_EQUATION)
        notifyResult(BASIC_TEXT_RESULT)
    }

    override fun del() {
        var tmpEquation = equation
        if (tmpEquation.equals(BASIC_TEXT_EQUATION)) return
        if (tmpEquation.length <= 0) {
            tmpEquation = BASIC_TEXT_EQUATION
        } else {
            tmpEquation = tmpEquation.substring(0, tmpEquation.length - 1)
        }
        // 通知改变公式
        notifyEquation(tmpEquation)
    }

    @Throws(RuntimeException::class)
    fun calculator(num1: String, num2: String, symbol: String): String {
        // 转换成 Double 类型
        var dNum1: Double = num1.toDouble()
        var dNum2: Double = num2.toDouble()

        // 计算
        if (symbolAdd.equals(symbol)) {
            return "" + add(dNum1, dNum2)
        } else if (symbolSub.equals(symbol)) {
            return "" + sub(dNum1, dNum2)
        } else if (symbolMul.equals(symbol)) {
            return "" + mul(dNum1, dNum2)
        } else if (symbolDivider.equals(symbol)) {
            return "" + divided(dNum1, dNum2)
        }
        return ERROR
    }

    override fun calculator(equation: String) {
        val symbols = regex.find(equation)?.value
        var nums: List<String> = equation.split(regex)

        // 计算
        var resultStr: String = ERROR
        try {
            val result = this.calculator(nums[0], nums[1], symbols.toString())
            if (!result.contains("E")) {
                val results = result?.split(symbolPoint)
                resultStr = if (results[1]?.toInt() > 0) result else results[0]
            } else {
                resultStr = result
            }
        } catch (e: RuntimeException) {
            Log.e(TAG, "calculator error", e)
        }

        // 回调结果
        notifyResult(resultStr)
    }

}

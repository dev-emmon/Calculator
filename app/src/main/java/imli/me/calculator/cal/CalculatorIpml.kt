package imli.me.calculator.cal

import android.text.TextUtils

/**
 * Created by Doots on 2017/7/19.
 */

class CalculatorIpml constructor(config: CalculatorConfig = CalculatorConfig.Builder().default()) {

    val TAG: String = "CalculatorIpml"

    // 计算公式
    var equation = ""
    var result = ""
    // 运算符号
    var calculatorConfig: CalculatorConfig? = null
    var calculator: Calculator? = null

    // 监听
    var calculatorListener : OnCalculatorListener? = null

    init {
        calculatorConfig = config
        calculator = Calculator(config)
        equation = config.BASIC_TEXT_EQUATION
        result = config.BASIC_TEXT_RESULT
    }

    fun setOnCalculatorListener(listener: OnCalculatorListener) {
        calculatorListener = listener
    }

    /**
     * 添加公式
     */
    fun addEquation(str: String) {
        if (TextUtils.isEmpty(str)) return
        var newEquation = if (equation.equals(calculatorConfig!!.BASIC_TEXT_EQUATION) && !isComputerSymbol(str)) "" else equation
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
    private fun addEquationSymbol(eq: String, str: String): String {
        var tmpEquation = eq
        // 如果是运算符号，则先判断最后一个字符是否是运算符号，或者是点
        val lastChar: String = tmpEquation[tmpEquation.lastIndex] + ""
        if (lastChar.equals(calculatorConfig!!.symbolAdd)) {
            // 如果是点，则替换 . 字符
            tmpEquation = replaceLastStr(tmpEquation, lastChar, str)
        } else if (isComputerSymbol(lastChar)) {
            // 如果是运算符号, 并且添加的字符是减号
            if (str.equals(calculatorConfig!!.symbolSub)) {
                if (lastChar.equals(calculatorConfig!!.symbolAdd)) {
                    // 本身为加号
                    tmpEquation = replaceLastStr(tmpEquation, lastChar, str)
                } else if (!lastChar.equals(calculatorConfig!!.symbolSub)) {
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
    private fun addEquationPoint(eq: String, str: String): String {
        var tmpEquation = eq
        val lastChar: String = tmpEquation[tmpEquation.lastIndex] + ""
        if (!isPoint(lastChar)) {
            val index = equation.lastIndexOfAny(getAllOperationSymbol())
            val end = equation.length
            if (!equation.substring(index, end).contains(calculatorConfig!!.symbolPoint)) {
                tmpEquation += str
            }
        }
        return tmpEquation
    }

    /**
     * 添加其它公式
     */
    private fun addOther(eq: String, str: String): String {
        var tmpEquation = eq
        tmpEquation += str
        return tmpEquation
    }

    /**
     * 替换末尾的字符串
     */
    private fun replaceLastStr(str: String, oldStr: String, newStr: String): String {
        val index = str.lastIndexOf(oldStr)
        if (index != -1) {
            return str.replaceRange(index, str.length, newStr)
        }
        return str
    }

    /**
     * 是否为 .
     */
    private fun isPoint(str: String): Boolean {
        return str.equals(calculatorConfig!!.symbolPoint)
    }

    /**
     * 是否为运算符号
     */
    private fun isComputerSymbol(str: String): Boolean {
        return str.equals(calculatorConfig!!.symbolAdd) || str.equals(calculatorConfig!!.symbolSub) || str.equals(calculatorConfig!!.symbolMul) || str.equals(calculatorConfig!!.symbolDiv)
    }

    /**
     * 通知公式
     */
    private fun notifyEquation(str: String) {
        equation = str
        calculatorListener?.onEquationChange(str)
    }

    /**
     * 通知计算结果
     */
    private fun notifyResult(str: String) {
        result = str
        calculatorListener?.onCalculatorResult(str)
    }

    /**
     * 获取说有的运算符号
     */
    private fun getAllOperationSymbol(): List<String> {
        var symbols = ArrayList<String>()
        symbols.add(calculatorConfig!!.symbolAdd)
        symbols.add(calculatorConfig!!.symbolSub)
        symbols.add(calculatorConfig!!.symbolMul)
        symbols.add(calculatorConfig!!.symbolDiv)
        return symbols
    }

    fun calculator() {
        notifyResult(calculator!!.calculator(equation))
    }

    /**
     * 清0
     */
    fun ac() {
        notifyEquation(calculatorConfig!!.BASIC_TEXT_EQUATION)
        notifyResult(calculatorConfig!!.BASIC_TEXT_RESULT)
    }

    /**
     * 删除
     */
    fun del() {
        var tmpEquation = equation
        if (tmpEquation.equals(calculatorConfig!!.BASIC_TEXT_EQUATION)) return
        if (tmpEquation.length <= 0) {
            tmpEquation = calculatorConfig!!.BASIC_TEXT_EQUATION
        } else {
            tmpEquation = tmpEquation.substring(0, tmpEquation.length - 1)
        }
        // 通知改变公式
        notifyEquation(tmpEquation)
    }

}

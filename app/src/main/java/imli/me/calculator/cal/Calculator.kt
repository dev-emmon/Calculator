package imli.me.calculator.cal

import android.util.Log

/**
 * Created by Doots on 2017/7/24.
 */

class Calculator constructor(config: CalculatorConfig) : BasicCalculator, SeniorCalculator {


    // TAG
    val TAG: String = "Calculator"

    // 配置信息
    val config: CalculatorConfig = config

    @Throws(RuntimeException::class)
    override fun add(num1: Double, num2: Double): Double {
        return num1 + num2
    }

    @Throws(RuntimeException::class)
    override fun sub(num1: Double, num2: Double): Double {
        return num1 - num2
    }

    @Throws(RuntimeException::class)
    override fun mul(num1: Double, num2: Double): Double {
        return num1 * num2
    }

    @Throws(RuntimeException::class)
    override fun divided(num1: Double, num2: Double): Double {
        return num1 / num2
    }

    @Throws(RuntimeException::class)
    fun calculator(num1: String, num2: String, symbol: String): String {
        // 转换成 Double 类型
        var dNum1: Double = num1.toDouble()
        var dNum2: Double = num2.toDouble()

        // 计算
        if (config.symbolAdd.equals(symbol)) {
            return "" + add(dNum1, dNum2)
        } else if (config.symbolSub.equals(symbol)) {
            return "" + sub(dNum1, dNum2)
        } else if (config.symbolMul.equals(symbol)) {
            return "" + mul(dNum1, dNum2)
        } else if (config.symbolDiv.equals(symbol)) {
            return "" + divided(dNum1, dNum2)
        }
        return config.ERROR
    }

    override fun calculator(equation: String): String {
//        var regex = "[$config.symbolMul|$config.symbolDivider|$config.symbolAdd|\\$config.symbolSub]".toRegex()
        var regex = ("[" + config.symbolAdd + "|" + config.symbolSub + "|" + config.symbolMul + "|" + config.symbolDiv + "]").toRegex()
        val symbols = regex.find(equation)?.value
        var nums: List<String> = equation.split(regex)

        // 计算
        var resultStr: String = config.ERROR
        try {
            val result = this.calculator(nums[0], nums[1], symbols.toString())
            if (!result.contains("E")) {
                val results = result?.split(config.symbolPoint)
                resultStr = if (results[1]?.toInt() > 0) result else results[0]
            } else {
                resultStr = result
            }
        } catch (e: RuntimeException) {
            Log.e(TAG, "calculator error", e)
        }

        // 回调结果
        return resultStr
    }
}

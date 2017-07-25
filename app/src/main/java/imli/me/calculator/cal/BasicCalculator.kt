package imli.me.calculator.cal

/**
 *
 * 基础运算/操作
 *
 *
 * Created by Doots on 2017/7/19.
 */
interface BasicCalculator {

    /**
     * 加法运算
     */
    @Throws(RuntimeException::class)
    fun add(num1: Double, num2: Double): Double

    /**
     * 减法运算
     */
    @Throws(RuntimeException::class)
    fun sub(num1: Double, num2: Double): Double

    /**
     * 乘法运算
     */
    @Throws(RuntimeException::class)
    fun mul(num1: Double, num2: Double): Double

    /**
     * 除法运算
     */
    @Throws(RuntimeException::class)
    fun divided(num1: Double, num2: Double): Double

    /**
     * 计算
     */
    fun calculator(equation: String): String
}
package imli.me.calculator.cal

/**
 *
 * 计算监听
 *
 * Created by Doots on 2017/7/19.
 */
interface OnCalculatorListener {

    /**
     * 公式改变
     */
    fun onEquationChange(equation: String)

    /**
     * 计算结果
     */
    fun onCalculatorResult(result: String)

}
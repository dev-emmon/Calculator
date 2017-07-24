package imli.me.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import imli.me.calculator.cal.CalculatorIpml
import imli.me.calculator.cal.OnCalculatorListener

class MainActivity : AppCompatActivity() {

    // 计算器
    private var calculatorIpml: CalculatorIpml? = null

    // 显示器
    private var tvShowResult: TextView? = null
    private var tvShowEquation: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findView()
        initialization()
    }


    /**
     *
     */
    private fun findView() {
        tvShowEquation = findViewById(R.id.tv_show_calculator) as TextView
        tvShowResult = findViewById(R.id.tv_show_result) as TextView

        val calculatorContent = findViewById(R.id.layout_calculator_content) as ViewGroup
        for (i in 0..calculatorContent.childCount - 1) {
            calculatorContent.getChildAt(i).setOnClickListener(click())
        }
    }

    fun initialization() {
        calculatorIpml = CalculatorIpml(
                resources.getString(R.string.calculator_symbol_addition),
                resources.getString(R.string.calculator_symbol_subtraction),
                resources.getString(R.string.calculator_symbol_multiply),
                resources.getString(R.string.calculator_symbol_divided)
        )
        calculatorIpml!!.setOnCalculatorListener(onCalculatorListener())
    }



    /**
     * 添加公式
     * @param str
     */
    private fun addEquation(str: String) {
        calculatorIpml!!.addEquation(str)
    }

    /**
     * 清除
     */
    private fun ac() {
        calculatorIpml!!.ac()
    }

    /**
     * 删除
     */
    private fun del() {
        calculatorIpml!!.del()
    }

    /**
     * 计算
     */
    private fun calculator() {
        calculatorIpml!!.calculator()
    }

    /**
     *
     * @return
     */
    fun onCalculatorListener(): OnCalculatorListener {
        return object : OnCalculatorListener {
            override fun onEquationChange(equation: String) {
                tvShowEquation?.text = equation
            }

            override fun onCalculatorResult(result: String) {
                tvShowResult?.text = result
            }

        }
    }

    fun click():  View.OnClickListener {
        return View.OnClickListener { view ->
            val tag = view?.tag as String
            if (tag == resources.getString(R.string.calculator_operate_ac)) {
                // 清除
                ac()
            } else if (tag == resources.getString(R.string.calculator_operate_calculator)) {
                // 计算
                calculator()
            } else if (tag == resources.getString(R.string.calculator_operate_del)) {
                // 删除
                del()
            } else {
                addEquation(tag)
            }
        }
    }
}

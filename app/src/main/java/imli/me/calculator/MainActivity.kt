package imli.me.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import imli.me.calculator.cal.CalculatorIpml

class MainActivity : AppCompatActivity() {

    var calculatorIpml: CalculatorIpml? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()
    }

    fun initialization() {
        calculatorIpml = CalculatorIpml(
                resources.getString(R.string.calculator_symbol_addition),
                resources.getString(R.string.calculator_symbol_subtraction),
                resources.getString(R.string.calculator_symbol_multiply),
                resources.getString(R.string.calculator_symbol_divided)
        )
    }

}

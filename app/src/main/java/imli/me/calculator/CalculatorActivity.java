package imli.me.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import imli.me.calculator.cal.CalculatorIpml;
import imli.me.calculator.cal.OnCalculatorListener;

/**
 * Created by Doots on 2017/7/20.
 */

public class CalculatorActivity extends AppCompatActivity {

    // 计算器
    private CalculatorIpml calculatorIpml;

    // 显示器
    private TextView tvShowResult;
    private TextView tvShowEquation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calculator);
        this.findView();
        this.initialization();
        this.ac();
    }

    /**
     *
     */
    private void findView() {
        tvShowEquation = (TextView) findViewById(R.id.tv_show_calculator);
        tvShowResult = (TextView) findViewById(R.id.tv_show_result);

        ViewGroup calculatorContent = (ViewGroup) findViewById(R.id.layout_calculator_content);
        for (int i = 0; i < calculatorContent.getChildCount(); i ++) {
            calculatorContent.getChildAt(i).setOnClickListener(click());
        }
    }

    /**
     *
     */
    private void initialization() {
        calculatorIpml = new CalculatorIpml(
                getResources().getString(R.string.calculator_symbol_addition),
                getResources().getString(R.string.calculator_symbol_subtraction),
                getResources().getString(R.string.calculator_symbol_multiply),
                getResources().getString(R.string.calculator_symbol_divided)
        );
        calculatorIpml.setOnCalculatorListener(onCalculatorListener());
    }

    /**
     * 添加公式
     * @param str
     */
    private void addEquation(String str) {
        calculatorIpml.addEquation(str);
    }

    /**
     * 清除
     */
    private void ac() {
        calculatorIpml.ac();
    }

    /**
     * 删除
     */
    private void del() {
        calculatorIpml.del();
    }

    /**
     * 计算
     */
    private void calculator() {
        calculatorIpml.calculator();
    }

    /**
     *
     * @return
     */
    private OnCalculatorListener onCalculatorListener() {
        return new OnCalculatorListener() {
            @Override
            public void onEquationChange(@NotNull String equation) {
                tvShowEquation.setText(equation);
            }

            @Override
            public void onCalculatorResult(@NotNull String result) {
                tvShowResult.setText(result);
            }
        };
    }

    /**
     *
     * @return
     */
    private View.OnClickListener click() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tag = (String) view.getTag();
                if (tag.equals(getResources().getString(R.string.calculator_operate_ac))) {
                    // 清除
                    ac();
                } else if (tag.equals(getResources().getString(R.string.calculator_operate_calculator))) {
                    // 计算
                    calculator();
                } else if (tag.equals(getResources().getString(R.string.calculator_operate_del))) {
                    // 删除
                    del();
                } else {
                    addEquation(tag);
                }
            }
        };
    }

}

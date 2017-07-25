package imli.me.calculator.cal

/**
 * Created by Doots on 2017/7/24.
 */

class CalculatorConfig {


    companion object {
        internal val DEF_SYMBOL_ADD: String = "+"
        internal val DEF_SYMBOL_SUB: String = "-"
        internal val DEF_SYMBOL_MUL: String = "*"
        internal val DEF_SYMBOL_DIV: String = "/"
        internal val DEF_SYMBOL_POINT: String = "."
    }

    val ERROR: String = "ERROR"
    val BASIC_TEXT_RESULT = ""
    val BASIC_TEXT_EQUATION = "0"

    // 运算符号
    var symbolAdd: String = DEF_SYMBOL_ADD
    var symbolSub: String = DEF_SYMBOL_SUB
    var symbolMul: String = DEF_SYMBOL_MUL
    var symbolDiv: String = DEF_SYMBOL_DIV
    var symbolPoint: String = DEF_SYMBOL_POINT


    class Builder {

        // 运算符号
        private var symbolAdd: String? = null
        private var symbolSub: String? = null
        private var symbolMul: String? = null
        private var symbolDiv: String? = null
        private var symbolPoint: String? = null

        /**
         * 加法符号
         */
        public fun withSymbolAdd(symbol: String): Builder {
            this.symbolAdd = symbol
            return this
        }

        /**
         * 减法符号
         */
        public fun withSymbolSub(symbol: String): Builder {
            this.symbolSub = symbol
            return this
        }

        /**
         * 乘法符号
         */
        public fun withSymbolMul(symbol: String): Builder {
            this.symbolMul = symbol
            return this
        }

        /**
         * 除法符号
         */
        public fun withSymbolDiv(symbol: String): Builder {
            this.symbolDiv = symbol
            return this
        }

        /**
         * 点 符号
         */
        public fun withSymbolPoint(symbol: String): Builder {
            this.symbolPoint = symbol
            return this
        }

        /**
         * 创建
         */
        public fun create(): CalculatorConfig {
            val config = CalculatorConfig()
            config.symbolAdd = if (this.symbolAdd == null) DEF_SYMBOL_ADD else this.symbolAdd!!
            config.symbolSub = if (this.symbolSub == null) DEF_SYMBOL_SUB else this.symbolSub!!
            config.symbolMul = if (this.symbolMul == null) DEF_SYMBOL_MUL else this.symbolMul!!
            config.symbolDiv = if (this.symbolDiv == null) DEF_SYMBOL_DIV else this.symbolDiv!!
            config.symbolPoint = if (this.symbolPoint == null) DEF_SYMBOL_POINT else this.symbolPoint!!
            return config
        }

        public fun default(): CalculatorConfig {
            val config = CalculatorConfig()
            return config
        }
    }

}

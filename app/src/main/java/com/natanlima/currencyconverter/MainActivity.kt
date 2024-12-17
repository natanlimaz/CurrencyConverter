package com.natanlima.currencyconverter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.natanlima.currencyconverter.databinding.ActivityMainBinding
import com.natanlima.currencyconverter.model.Currency
import com.natanlima.currencyconverter.utils.CurrencySpinnerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onContentChanged() {
        super.onContentChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = CurrencySpinnerAdapter(this, currencies)
        binding.currenciesSpinnerInput.adapter = adapter;
        binding.currenciesSpinnerOutput.adapter = adapter;

        binding.currenciesSpinnerInput.setSelection(1);

        binding.edtCurrencyValueInput.addTextChangedListener { value ->
            val outputIndex = binding.currenciesSpinnerOutput.selectedItemId.toInt();
            val inputIndex = binding.currenciesSpinnerInput.selectedItemId.toInt();
            val valueStr = value.toString();

            if(valueStr.isNotEmpty()) {
                calculate(inputIndex, outputIndex, valueStr);
            }

        }

        binding.btnSwap.setOnClickListener {
            val outputIndex = binding.currenciesSpinnerOutput.selectedItemId.toInt();
            val inputIndex = binding.currenciesSpinnerInput.selectedItemId.toInt();

            binding.currenciesSpinnerOutput.setSelection(inputIndex);
            binding.currenciesSpinnerInput.setSelection(outputIndex);

            val valueStr = binding.edtCurrencyValueInput.text.toString();

            calculate(outputIndex, inputIndex, valueStr);
        }

        binding.btnClean.setOnClickListener {
            binding.edtCurrencyValueInput.setText("");
            binding.edtCurrencyValueOutput.setText("");
        }
    }

    private fun calculate(inputId: Int, outputId: Int, value: String) {
        if(value.toString().isNotEmpty()) {
            val currentValue = value.toString().toDouble();

            val inputCurrency = binding.currenciesSpinnerInput.selectedItem as Currency;
            val outputCurrency = binding.currenciesSpinnerOutput.selectedItem as Currency;

            val result = currencyConverter(currentValue, currencies[inputId], currencies[outputId]);

            val strResult = getString(R.string.exchange_value, currentValue, inputCurrency.currency, result, outputCurrency.currency);

            binding.tvExchangeResult.text = strResult;

            val strValueOutput = getString(R.string.converted_format, result);
            binding.edtCurrencyValueOutput.setText(strValueOutput);
        }
        else {
            binding.edtCurrencyValueOutput.setText("");
        }
    }

    fun currencyConverter(amount: Double, fromCurrency: Currency, toCurrency: Currency): Double {
        return (amount * fromCurrency.value) / toCurrency.value;
    }

}

val currencies = listOf<Currency>(
    Currency(R.drawable.ic_bra, "BRL", 1.0),
    Currency(R.drawable.ic_usa, "USD", 6.09),
    Currency(R.drawable.ic_eur, "EUR", 6.39),
    Currency(R.drawable.ic_gbp, "GBP", 7.71),
    Currency(R.drawable.ic_jpy, "JPY", 0.04),
    Currency(R.drawable.ic_clp, "CLP", 0.0061),
)
package com.example.currencyapp

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyapp.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.convertButton.setOnClickListener { converter() }
        binding.conversionResult.setOnKeyListener{view, keyCode, _ ->
            handleKeyEvent(
              view, keyCode)}
    }

    private fun converter() {
        val stringInTextField = binding.amountInKenyanShillingText.text.toString()
        val amount = stringInTextField.toDoubleOrNull()
        val currencyAmount = when (binding.currencyOptions.checkedRadioButtonId) {
            R.id.Dollar_rate -> 0.0069
            R.id.Euro_rate -> 0.0064
            R.id.Pound_rate -> 0.0055
            R.id.Ugandan_rate -> 25.57
            R.id.Tanzanian_rate -> 17.16
            R.id.Sudanese_rate -> 4.12
            R.id.Norway_rate -> 0.0074
            else -> 0.048
        }
        var tip = currencyAmount * amount!!
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.conversionResult.text = getString(R.string.amount, formattedTip)
    }
    private fun handleKeyEvent(view: View, keyCode:Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the Keyboard
            val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}
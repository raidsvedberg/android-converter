package com.example.convapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var conversionSpinner: Spinner
    private lateinit var input: EditText
    private lateinit var result: TextView
    private lateinit var swapButton: Button
    private lateinit var label: TextView

    private var isReversed = false

    data class Conversion(
        val name: String,
        val fromUnit: String,
        val toUnit: String,
        val convert: (Double) -> Double,
        val reverseConvert: (Double) -> Double
    )

    private val categories = mapOf(

        "Length" to listOf(
            Conversion("Inches ↔ Centimeters", "in", "cm",
                { it * 2.54 }, { it / 2.54 }),
            Conversion("Feet ↔ Meters", "ft", "m",
                { it * 0.3048 }, { it / 0.3048 }),
            Conversion("Yards ↔ Meters", "yd", "m",
                { it * 0.9144 }, { it / 0.9144 }),
            Conversion("Miles ↔ Kilometers", "mi", "km",
                { it * 1.60934 }, { it / 1.60934 })
        ),

        "Area" to listOf(
            Conversion("Square Inches ↔ cm²", "in²", "cm²",
                { it * 6.4516 }, { it / 6.4516 }),
            Conversion("Square Feet ↔ m²", "ft²", "m²",
                { it * 0.092903 }, { it / 0.092903 }),
            Conversion("Square Yards ↔ m²", "yd²", "m²",
                { it * 0.836127 }, { it / 0.836127 }),
            Conversion("Acres ↔ Hectares", "acre", "ha",
                { it * 0.404686 }, { it / 0.404686 })
        ),

        "Weight" to listOf(
            Conversion("Ounces ↔ Grams", "oz", "g",
                { it * 28.3495 }, { it / 28.3495 }),
            Conversion("Pounds ↔ Kilograms", "lb", "kg",
                { it * 0.453592 }, { it / 0.453592 }),
            Conversion("Tons ↔ Metric Tons", "ton", "t",
                { it * 0.907185 }, { it / 0.907185 })
        ),

        "Volume" to listOf(
            Conversion("Teaspoons ↔ mL", "tsp", "mL",
                { it * 4.92892 }, { it / 4.92892 }),
            Conversion("Tablespoons ↔ mL", "tbsp", "mL",
                { it * 14.7868 }, { it / 14.7868 }),
            Conversion("Fluid Ounces ↔ mL", "fl oz", "mL",
                { it * 29.5735 }, { it / 29.5735 }),
            Conversion("Cups ↔ Liters", "cup", "L",
                { it * 0.24 }, { it / 0.24 }),
            Conversion("Pints ↔ Liters", "pt", "L",
                { it * 0.473176 }, { it / 0.473176 }),
            Conversion("Quarts ↔ Liters", "qt", "L",
                { it * 0.946353 }, { it / 0.946353 }),
            Conversion("Gallons ↔ Liters", "gal", "L",
                { it * 3.78541 }, { it / 3.78541 })
        ),

        "Temperature" to listOf(
            Conversion("Fahrenheit ↔ Celsius", "°F", "°C",
                { (it - 32) * 5 / 9 },
                { it * 9 / 5 + 32 })
        )
    )

    private lateinit var categoryNames: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        conversionSpinner = findViewById(R.id.conversionSpinner)
        input = findViewById(R.id.inputValue)
        result = findViewById(R.id.resultText)
        swapButton = findViewById(R.id.swapButton)
        label = findViewById(R.id.conversionLabel)

        categoryNames = categories.keys.toList()

        // tabs
        categoryNames.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateConversionSpinner(tab.position)
                isReversed = false
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // spinner change
        conversionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateLabel()
                performConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // swap
        swapButton.setOnClickListener {
            isReversed = !isReversed
            updateLabel()
            performConversion()
        }

        // auto-convert while typing
        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                performConversion()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // convert on Enter key
        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                performConversion()
                true
            } else false
        }

        updateConversionSpinner(0)
    }

    private fun updateConversionSpinner(categoryIndex: Int) {
        val selectedCategory = categoryNames[categoryIndex]
        val list = categories[selectedCategory]!!

        conversionSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            list.map { it.name }
        )
    }

    private fun performConversion() {
        val valueText = input.text.toString()

        if (valueText.isEmpty() || valueText == "." || valueText == "-") {
            animateResult("")
            return
        }

        val value = valueText.toDoubleOrNull()
        if (value == null) {
            animateResult("")
            return
        }

        val selectedCategory = categoryNames[tabLayout.selectedTabPosition]
        val conversion = categories[selectedCategory]!![conversionSpinner.selectedItemPosition]

        val output = if (!isReversed) {
            conversion.convert(value)
        } else {
            conversion.reverseConvert(value)
        }

        val formattedInput = if (value % 1.0 == 0.0) value.toInt().toString()
        else String.format("%.3f", value)

        val formattedOutput = if (output % 1.0 == 0.0) output.toInt().toString()
        else String.format("%.3f", output)

        val inputUnit = if (!isReversed) conversion.fromUnit else conversion.toUnit
        val outputUnit = if (!isReversed) conversion.toUnit else conversion.fromUnit

        val finalText = "$formattedInput $inputUnit = $formattedOutput $outputUnit"

        animateResult(finalText)
    }

    private fun updateLabel() {
        val selectedCategory = categoryNames[tabLayout.selectedTabPosition]
        val conversion = categories[selectedCategory]!![conversionSpinner.selectedItemPosition]

        label.text = if (!isReversed) {
            "${conversion.fromUnit} → ${conversion.toUnit}"
        } else {
            "${conversion.toUnit} → ${conversion.fromUnit}"
        }
    }

    private fun animateResult(newText: String) {
        if (result.text == newText) return

        result.animate()
            .alpha(0f)
            .scaleX(0.9f)
            .scaleY(0.9f)
            .setDuration(100)
            .withEndAction {
                result.text = newText
                result.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(150)
                    .start()
            }
            .start()
    }
}
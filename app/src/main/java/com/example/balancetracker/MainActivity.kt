package com.example.balancetracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var balanceText: TextView
    private lateinit var amountInput: EditText
    private lateinit var noteInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balanceText = findViewById(R.id.current_balance_text)
        amountInput = findViewById(R.id.amount_input)
        noteInput = findViewById(R.id.note_input)

        findViewById<Button>(R.id.credit_button).setOnClickListener {
            handleTransaction(isCredit = true)
        }

        findViewById<Button>(R.id.debit_button).setOnClickListener {
            handleTransaction(isCredit = false)
        }

        refreshBalanceDisplay()
    }

    override fun onResume() {
        super.onResume()
        refreshBalanceDisplay()
    }

    private fun handleTransaction(isCredit: Boolean) {
        val raw = amountInput.text.toString().trim()
        val amount = raw.toDoubleOrNull()

        if (amount == null || amount <= 0.0) {
            Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        if (isCredit) {
            BalanceStore.addCredit(this, amount)
            Toast.makeText(this, "Credited ${format(amount)}", Toast.LENGTH_SHORT).show()
        } else {
            BalanceStore.addDebit(this, amount)
            Toast.makeText(this, "Debited ${format(amount)}", Toast.LENGTH_SHORT).show()
        }

        amountInput.text.clear()
        noteInput.text.clear()

        // Push the update to the home-screen widget immediately
        BalanceWidgetProvider.refreshAll(this)
        refreshBalanceDisplay()
    }

    private fun refreshBalanceDisplay() {
        balanceText.text = "Current Balance: ${format(BalanceStore.getBalance(this))}"
    }

    private fun format(value: Double): String =
        NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(value)
}

package com.example.balancetracker

import android.content.Context

/**
 * Single source of truth for the balance value.
 * Stored in SharedPreferences so it persists across app/widget restarts.
 */
object BalanceStore {

    private const val PREFS_NAME = "balance_prefs"
    private const val KEY_BALANCE = "balance"

    fun getBalance(context: Context): Double {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_BALANCE, 0f).toDouble()
    }

    private fun setBalance(context: Context, value: Double) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putFloat(KEY_BALANCE, value.toFloat()).apply()
    }

    /** Adds a credit (income) — increases balance. */
    fun addCredit(context: Context, amount: Double) {
        setBalance(context, getBalance(context) + amount)
    }

    /** Adds a debit (payment/expense) — decreases balance. */
    fun addDebit(context: Context, amount: Double) {
        setBalance(context, getBalance(context) - amount)
    }
}

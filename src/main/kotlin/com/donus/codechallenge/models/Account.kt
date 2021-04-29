package com.donus.codechallenge.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.apache.http.util.TextUtils

@Document
data class Account(
    @Id
    val cpf      : String,
    val fullName : String,
    var balance  : Double ? = 0.0,

    ) {
    fun isCPF(document: String): Boolean {
        if (TextUtils.isEmpty(document)) return false

        val numbers = arrayListOf<Int>()

        document.filter { it.isDigit() }.forEach {
            numbers.add(it.toString().toInt())
        }

        if (numbers.size != 11) return false

        //repeticao
        (0..9).forEach { n ->
            val digits = arrayListOf<Int>()
            (0..10).forEach { digits.add(n) }
            if (numbers == digits) return false
        }

        //digito 1
        val dv1 = ((0..8).sumBy { (it + 1) * numbers[it] }).rem(11).let {
            if (it >= 10) 0 else it
        }

        val dv2 = ((0..8).sumBy { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
            if (it >= 10) 0 else it
        }

        return numbers[9] == dv1 && numbers[10] == dv2
    }
    fun validateBalance(balance: Double): Boolean {
        if (balance >= 0.0)
            return true
        return false
    }
    fun validateTransfer(send: Double ): Boolean {
        if (send > 0 && balance!! - send  >= 0.0)
            return true
        return false
    }
    fun validateDeposit(deposit: Double): Boolean {
        if (deposit in 0.0..2000.0)
            return true
        return false
    }
    fun deposit(deposit: Double) {
        balance = balance!! + deposit
    }
    fun transfer(addressed: Account, value : Double){
        if (balance!! >= value){
            balance = balance!! - value
            addressed.deposit(value)
        }
    }
}
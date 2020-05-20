package com.yamin.primeboard.repo

import androidx.paging.ItemKeyedDataSource
import com.yamin.primeboard.model.NumberItem
import com.yamin.primeboard.model.NumberType
import com.yamin.primeboard.utils.getPrimeFactors
import com.yamin.primeboard.utils.hasGcd
import com.yamin.primeboard.utils.isPrime

class NumbersDataSource(
    private val initialNumber: Int,
    private val selectedNumber: Int?
) : ItemKeyedDataSource<Int, NumberItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<NumberItem>
    ) {
        val startNumber = initialNumber
        val amount = params.requestedLoadSize
        callback.onResult(getNumbers(startNumber, amount))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<NumberItem>) {
        val startNumber = params.key
        val amount = params.requestedLoadSize
        callback.onResult(getNumbers(startNumber, amount))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<NumberItem>) {
        // keep the first number above 0
        if (params.key < 2) return
        var amount = params.requestedLoadSize
        var startNumber = params.key - 1 - amount
        if (startNumber < 0) {
            amount += startNumber
            startNumber = 0
        }
        callback.onResult(getNumbers(startNumber, amount))
    }

    override fun getKey(item: NumberItem): Int {
        return item.value + 1
    }

    private fun getNumbers(firstNumber: Int, amount: Int): List<NumberItem> {
        val resultList = arrayListOf<NumberItem>()
        for (i in 0 until amount) {
            resultList.add(buildNumberItem(firstNumber + i))
        }
        return resultList
    }

    private fun buildNumberItem(number: Int): NumberItem {
        val type = if (number.isPrime()) {
            NumberType.Prime
        } else if (selectedNumber != null && number.hasGcd(selectedNumber)) {
            // if the user is long pressing selected number will update
            // and we calculate if the current number has GCD with the selected number
            NumberType.Divided
        } else {
            NumberType.Ordinary
        }
        return NumberItem(value = number, type = type)
    }


}
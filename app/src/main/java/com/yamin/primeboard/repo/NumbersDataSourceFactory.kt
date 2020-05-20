package com.yamin.primeboard.repo

import androidx.paging.DataSource
import com.yamin.primeboard.model.NumberItem
import com.yamin.primeboard.utils.INITIAL_VALUE

class NumbersDataSourceFactory : DataSource.Factory<Int, NumberItem>() {

    private lateinit var curSource: NumbersDataSource
    private var initialNumber: Int = INITIAL_VALUE
    private var selectedNumber: Int? = null

    override fun create(): DataSource<Int, NumberItem> {
        val source = NumbersDataSource(initialNumber, selectedNumber)
        curSource = source
        return source
    }

    fun setSelectedNumber(selectedNumber: NumberItem?, initialNumber: Int) {
        // when the user select a number we update the data source params
        // and invalidate - that will trigger the create function again with the new params
        this.selectedNumber = selectedNumber?.value
        this.initialNumber = initialNumber
        curSource.invalidate()
    }

}
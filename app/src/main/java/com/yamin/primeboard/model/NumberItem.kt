package com.yamin.primeboard.model

import androidx.annotation.ColorRes
import com.yamin.primeboard.R

data class NumberItem(val value: Int, val type: NumberType)

sealed class NumberType {
    @ColorRes
    abstract fun getBackgroundColor(): Int

    object Ordinary : NumberType() {
        override fun getBackgroundColor(): Int {
            return R.color.white100
        }

        override fun toString(): String {
            return "Ordinary"
        }
    }

    object Prime : NumberType() {
        override fun getBackgroundColor(): Int {
            return R.color.red
        }

        override fun toString(): String {
            return "Prime"
        }
    }

    object Divided : NumberType() {
        override fun getBackgroundColor(): Int {
            return R.color.green
        }

        override fun toString(): String {
            return "Divided"
        }
    }
}
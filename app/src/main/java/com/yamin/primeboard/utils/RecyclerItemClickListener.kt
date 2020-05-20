package com.yamin.primeboard.utils

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(
    recyclerView: RecyclerView
) : RecyclerView.OnItemTouchListener {

    private var itemClickedLiveData = MutableLiveData<Pair<Int, Boolean>>()

    private val gestureDetector: GestureDetector =
        GestureDetector(recyclerView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent?) {
                e?.let { event ->
                    recyclerView.findChildViewUnder(event.x, event.y)?.let {
                        itemClickedLiveData.value = recyclerView.getChildAdapterPosition(it) to true
                    }
                }
            }
        })

    fun getItemClickedLiveData(): LiveData<Pair<Int, Boolean>> = itemClickedLiveData

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(e)
        if (e.action == MotionEvent.ACTION_UP && itemClickedLiveData.value?.second == true) {
            itemClickedLiveData.value = 1 to false
        }

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
}

fun RecyclerView.addItemClickLiveData(
    lifecycleOwner: LifecycleOwner,
    clicked: (itemPosition: Int, selected: Boolean) -> Unit
) {
    val itemClickStream = RecyclerItemClickListener(this)
    this.addOnItemTouchListener(itemClickStream)
    itemClickStream.getItemClickedLiveData().observe(lifecycleOwner, Observer {
        clicked.invoke(it.first, it.second)
    })
}

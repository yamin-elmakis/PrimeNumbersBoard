package com.yamin.primeboard.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yamin.primeboard.model.NumberItem
import com.yamin.primeboard.repo.NumbersRepository
import com.yamin.primeboard.utils.PAGE_SIZE
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repo: NumbersRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private fun Disposable.addToDispose() = compositeDisposable.add(this)

    val numberItems = repo.getNumberItems(PAGE_SIZE)
    private val factors = MutableLiveData<Pair<Int, List<Int>>>()
    val factorsLiveData: LiveData<Pair<Int, List<Int>>> = factors

    fun onNumberSelected(selectedNumber: NumberItem?, initialNumber: Int) {
        repo.setSelectedNumber(selectedNumber, initialNumber)
        selectedNumber?.let {
            repo.getFactors(it.value)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribeBy(onSuccess = {
                    factors.postValue(it)
                }, onError = {

                }).addToDispose()
        }
    }


    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

}
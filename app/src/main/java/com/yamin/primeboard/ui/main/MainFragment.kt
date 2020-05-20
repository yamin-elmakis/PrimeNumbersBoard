package com.yamin.primeboard.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.yamin.primeboard.R
import com.yamin.primeboard.ui.ViewModelFactory
import com.yamin.primeboard.utils.SpacesItemDecoration
import com.yamin.primeboard.utils.TABLE_COLUMNS
import com.yamin.primeboard.utils.addItemClickLiveData
import com.yamin.primeboard.utils.safeLet
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.popup_factors.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFragment : Fragment(), KodeinAware {

    private val closest by closestKodein()
    override val kodein by Kodein.lazy { extend(closest) }

    private lateinit var adapter: PrimeBoardAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModelFactory: ViewModelFactory by instance()
    private val viewModel: MainViewModel by viewModels(factoryProducer = { viewModelFactory })

    var popupWindow: PopupWindow? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.numberItems.observe(viewLifecycleOwner, Observer(adapter::submitList))
        viewModel.factorsLiveData.observe(viewLifecycleOwner, Observer {
            updatePopupData(it.first, it.second)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PrimeBoardAdapter(requireContext())
        mainRv.layoutManager = GridLayoutManager(requireContext(), TABLE_COLUMNS)
        mainRv.adapter = adapter
        val itemSpacing: Int = (2 * Resources.getSystem().displayMetrics.density).toInt()
        mainRv.addItemDecoration(SpacesItemDecoration(itemSpacing))

        mainRv.addItemClickLiveData(viewLifecycleOwner, { itemPosition, isSelected ->
            // find the first visible item so we can go back to the same place in the list when
            // we refresh the data
            val gridLayoutManager: GridLayoutManager = mainRv.layoutManager as GridLayoutManager
            val firstVisible = gridLayoutManager.findFirstVisibleItemPosition()

            adapter.currentList?.let {
                Pair(it[itemPosition], it[firstVisible]).safeLet { selected, initial ->
                    val selectedItem = if (!isSelected) {
                        hidePopup()
                        null
                    } else {
                        showPopup(gridLayoutManager.findViewByPosition(itemPosition))
                        selected
                    }
                    viewModel.onNumberSelected(selectedItem, initial.value)
                }
            }
        })
    }

    private fun showPopup(anchorView: View?) {
        anchorView?.let {
            popupWindow = getFactorsPopupWindow()
            popupWindow?.showAsDropDown(it)
        }
    }

    private fun hidePopup() {
        popupWindow?.let { curPopupWindow ->
            curPopupWindow.dismiss()
            popupWindow = null
        }
    }

    private fun getFactorsPopupWindow(): PopupWindow {
        return PopupWindow(requireContext()).apply {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            contentView = createPopupView()
        }
    }

    private fun createPopupView(): View {
        val context = requireContext()
        return LayoutInflater.from(context)
            .inflate(R.layout.popup_factors, (view as? ViewGroup), false)
    }

    private fun updatePopupData(number: Int, factors: List<Int>) {
        popupWindow?.let {
            if (!it.isShowing) return
            it.contentView.popupTitle.text =
                requireContext().getString(R.string.popup_title, number)
            val text = if (factors.isEmpty()) {
                requireContext().getString(R.string.popup_no_factors)
            } else {
                factors.toString()
            }
            it.contentView.popupText.text = text
        }
    }
}

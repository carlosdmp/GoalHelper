package apps.cdmp.goalhelper.presentation.ui.summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import apps.cdmp.diffadapter.diff.DiffAdapter
import apps.cdmp.goalHelper.databinding.SummaryHeaderBinding
import apps.cdmp.goalHelper.databinding.SummaryItemBinding
import apps.cdmp.goalhelper.presentation.ui.summary.mapper.UiMapper
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryItemUI
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryHeaderUI
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryUI

class SummaryAdapter(override var items: MutableList<SummaryUI> = mutableListOf(),
                     private val uiMapper: UiMapper) :
        DiffAdapter<SummaryHolder, SummaryUI>() {

    private object ViewTypes {
        const val ITEM = 0
        const val SEPARATOR = 1
    }

    override fun onBind(holder: SummaryHolder, item: SummaryUI) {
        when (holder) {
            is ItemHolder -> holder.bind(item as SummaryItemUI)
            is HeaderHolder -> holder.bind(uiMapper, item as SummaryHeaderUI)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewTypes.ITEM -> ItemHolder(SummaryItemBinding.inflate(layoutInflater, parent, false))
            else -> HeaderHolder(SummaryHeaderBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun getItemViewType(position: Int) =
            when (items[position]) {
                is SummaryItemUI -> ViewTypes.ITEM
                else -> ViewTypes.SEPARATOR
            }
}
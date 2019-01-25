package apps.cdmp.goalhelper.presentation.ui.summary.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import apps.cdmp.goalHelper.databinding.SummaryHeaderBinding
import apps.cdmp.goalHelper.databinding.SummaryItemBinding
import apps.cdmp.goalhelper.presentation.ui.summary.mapper.UiMapper
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryItemUI
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryHeaderUI

sealed class SummaryHolder(view: View) : RecyclerView.ViewHolder(view)

class ItemHolder(private val binding: SummaryItemBinding) : SummaryHolder(binding.root) {

    fun bind(item: SummaryItemUI) {
        binding.model = item
        binding.cbDone.setOnClickListener {
            item.onClickDone()
        }
        binding.executePendingBindings()
    }
}

class HeaderHolder(private val binding: SummaryHeaderBinding) : SummaryHolder(binding.root) {
    fun bind(uiMapper: UiMapper, header: SummaryHeaderUI) {
        binding.headerText = with(uiMapper){
            header.header.resString()
        }
    }
}
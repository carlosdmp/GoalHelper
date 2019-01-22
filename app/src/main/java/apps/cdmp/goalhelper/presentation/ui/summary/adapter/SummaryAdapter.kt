package apps.cdmp.goalhelper.presentation.ui.summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apps.cdmp.diffadapter.diff.DiffAdapter
import apps.cdmp.goalHelper.databinding.SummaryItemBinding
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryItemUI

class SummaryAdapter(override var items: MutableList<SummaryItemUI> = mutableListOf()) :
    DiffAdapter<SummaryAdapter.ViewHolder, SummaryItemUI>() {

    override fun onBind(holder: ViewHolder, item: SummaryItemUI) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = SummaryItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    class ViewHolder(private val binding: SummaryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SummaryItemUI) {
            binding.name = item.name
            binding.deadline = item.deadline
            binding.done = item.isDone
            binding.cbDone.setOnClickListener {
                item.onClickDone()
            }
            binding.executePendingBindings()
        }
    }
}
package apps.cdmp.diffrecycler.diff

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import apps.cdmp.diffrecycler.DiffModel

abstract class DiffAdapter<VH : RecyclerView.ViewHolder, T : DiffModel<T>> : RecyclerView.Adapter<VH>() {

    abstract var items: MutableList<T>

    abstract fun onBind(holder: VH, item: T)

    fun updateListItems(newItems: List<T>) {
        val diffCallback = DiffUtilCallBack(oldList = items, newList = newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.items.clear()
        this.items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) = onBind(holder, items[position])

}
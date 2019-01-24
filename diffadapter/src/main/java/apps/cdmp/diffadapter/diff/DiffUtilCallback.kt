package apps.cdmp.diffadapter.diff

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import apps.cdmp.diffadapter.DiffModel


class DiffUtilCallBack<T>(private var oldList: List<T>, private var newList: List<T>) : DiffUtil.Callback()
        where T : DiffModel<T> {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].areItemsTheSame(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].areContentsTheSame(newList[newItemPosition])

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}

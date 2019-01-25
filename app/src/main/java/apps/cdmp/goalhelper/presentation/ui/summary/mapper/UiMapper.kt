package apps.cdmp.goalhelper.presentation.ui.summary.mapper

import android.content.res.Resources
import apps.cdmp.goalHelper.R
import apps.cdmp.goalhelper.common.Resource
import apps.cdmp.goalhelper.common.Success
import apps.cdmp.goalhelper.common.remainingTo
import apps.cdmp.goalhelper.data.model.Goal
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryHeaderType
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryHeaderUI
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryItemUI
import apps.cdmp.goalhelper.presentation.ui.summary.uimodel.SummaryUI
import java.util.*

class UiMapper(private val resources: Resources) {
    fun Resource<List<Goal>>.toUI(goalClicked: (id: Int, done: Boolean) -> Unit): List<SummaryUI>? {
        val todayDate = Calendar.getInstance().time
        return when (this) {
            is Success -> data.map {
                SummaryItemUI(
                        id = it.id,
                        name = it.description,
                        deadline = resources.getString(R.string.days_remaining_label) + " " + (todayDate remainingTo it.deadline),
                        isDone = it.isDone,
                        onClickDone = { goalClicked(it.id, it.isDone) }
                )
            }.groupBy { it.isDone }.toSortedMap(kotlin.Comparator { o1, o2 ->
                when {
                    o1 == false && o2 == true -> -1
                    o1 == true && o2 == false -> 1
                    else -> 0
                }
            }).flatMap { m -> (listOf(SummaryHeaderUI(if (m.key) SummaryHeaderType.DONE else SummaryHeaderType.UNDONE)) + m.value) }
            else -> null
        }
    }

    fun SummaryHeaderType.resString() = resources.getString(when (this) {
        SummaryHeaderType.DONE -> R.string.header_done_label
        SummaryHeaderType.UNDONE -> R.string.header_undone_label
    })
}
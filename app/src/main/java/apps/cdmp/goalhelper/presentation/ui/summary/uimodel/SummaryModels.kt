package apps.cdmp.goalhelper.presentation.ui.summary.uimodel

import apps.cdmp.diffadapter.DiffModel
import apps.cdmp.diffadapter.diff.CloneableModel

sealed class SummaryUI : DiffModel<SummaryUI>, CloneableModel<SummaryUI> {

    override fun clone(): SummaryUI =
            when (this) {
                is SummaryItemUI -> copy()
                is SummaryHeaderUI -> copy()
            }

    override fun areItemsTheSame(other: SummaryUI): Boolean =
            when {
                this is SummaryItemUI && other is SummaryItemUI -> id == other.id
                this is SummaryHeaderUI && other is SummaryHeaderUI -> header == other.header
                else -> false
            }

    override fun areContentsTheSame(other: SummaryUI): Boolean =
            when {
                this is SummaryItemUI && other is SummaryItemUI -> equals(other)
                this is SummaryHeaderUI && other is SummaryHeaderUI -> areItemsTheSame(other)
                else -> false
            }
}

data class SummaryItemUI(
        val id: Int,
        val name: String,
        val deadline: String,
        val isDone: Boolean,
        val onClickDone: () -> Unit
) : SummaryUI()

data class SummaryHeaderUI(val header: SummaryHeaderType) : SummaryUI()

enum class SummaryHeaderType { DONE, UNDONE }
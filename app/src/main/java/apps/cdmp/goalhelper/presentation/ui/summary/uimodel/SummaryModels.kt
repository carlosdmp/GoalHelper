package apps.cdmp.goalhelper.presentation.ui.summary.uimodel

import apps.cdmp.diffrecycler.DiffModel

data class SummaryListUI(
    val undoneHeader: String,
    val undoneItemUIS: List<SummaryItemUI>,
    val doneHeader: String,
    val doneItemUIS: List<SummaryItemUI>
)

data class SummaryItemUI(
    val id: Int,
    val name: String,
    val deadline: String,
    val isDone: Boolean,
    val onClickDone: () -> Unit
) : DiffModel<SummaryItemUI> {
    override fun areItemsTheSame(other: SummaryItemUI): Boolean = id == other.id

    override fun areContentsTheSame(other: SummaryItemUI): Boolean = equals(other)

    override fun clone(): SummaryItemUI = copy()

}

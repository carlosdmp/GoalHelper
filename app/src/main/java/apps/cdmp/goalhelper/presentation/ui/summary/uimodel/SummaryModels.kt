package apps.cdmp.goalhelper.presentation.ui.summary.uimodel

import apps.cdmp.diffadapter.DiffModelWithID

data class SummaryListUI(
    val undoneHeader: String,
    val undoneItemUIS: List<SummaryItemUI>,
    val doneHeader: String,
    val doneItemUIS: List<SummaryItemUI>
)

data class SummaryItemUI(
    override val id: Int,
    val name: String,
    val deadline: String,
    val isDone: Boolean,
    val onClickDone: () -> Unit
) : DiffModelWithID<SummaryItemUI, Int> {

    override fun areContentsTheSame(other: SummaryItemUI): Boolean = equals(other)

    override fun clone(): SummaryItemUI = copy()

}

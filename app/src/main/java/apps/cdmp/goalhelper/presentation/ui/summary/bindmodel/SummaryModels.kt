package apps.cdmp.goalhelper.presentation.ui.summary.bindmodel

data class SummaryList(
    val undoneHeader: String,
    val undoneItems: List<SummaryItem>,
    val doneHeader: String,
    val doneItems: List<SummaryItem>
)

data class SummaryItem(val name: String, val deadline: String, val isDone: Boolean, val onClickDone: () -> Unit)

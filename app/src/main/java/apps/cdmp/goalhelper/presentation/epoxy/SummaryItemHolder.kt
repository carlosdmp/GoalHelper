package apps.cdmp.goalhelper.presentation.epoxy

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import apps.cdmp.goalHelper.R
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder


@EpoxyModelClass(layout = R.layout.view_holder_summary_item)
abstract class SummaryItemHolder : EpoxyModelWithHolder<Holder>() {

    @EpoxyAttribute
    lateinit var name: String
    @EpoxyAttribute
    lateinit var deadline: String
    @EpoxyAttribute
    open var isDone: Boolean = false
    @EpoxyAttribute
    var onDoneClick: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        holder.tvName.text = name
        holder.tvDeadline.text = deadline
        holder.cbDone.isChecked = isDone
        holder.cbDone.setOnClickListener(onDoneClick)
    }
}

class Holder : KotlinEpoxyHolder() {
    val tvName by bind<TextView>(R.id.tv_name)
    val tvDeadline by bind<TextView>(R.id.tv_deadline)
    val cbDone by bind<CheckBox>(R.id.cb_done)
}


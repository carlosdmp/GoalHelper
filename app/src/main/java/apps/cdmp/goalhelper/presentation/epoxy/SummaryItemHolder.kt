package apps.cdmp.goalhelper.presentation.epoxy

import android.widget.TextView
import apps.cdmp.goalHelper.R
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import androidx.annotation.StringRes
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyModelWithHolder


@EpoxyModelClass(layout = R.layout.view_holder_summary_item)
abstract class SummaryItemHolder : EpoxyModelWithHolder<Holder>() {

    @EpoxyAttribute lateinit var name: String

    override fun bind(holder: Holder) {
        holder.titleView.text = name
    }
}

class Holder : KotlinEpoxyHolder() {
    val titleView by bind<TextView>(R.id.tv_name)
}
package apps.cdmp.goalhelper.presentation.epoxy

import apps.cdmp.goalHelper.R
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import androidx.annotation.StringRes
import com.airbnb.epoxy.DataBindingEpoxyModel


@EpoxyModelClass(layout = R.layout.view_holder_summary_item)
abstract class ButtonModel : DataBindingEpoxyModel() {
    @EpoxyAttribute
    @StringRes
    var name: String = ""
}
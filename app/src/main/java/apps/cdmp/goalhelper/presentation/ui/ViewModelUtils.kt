package apps.cdmp.goalhelper.presentation.ui

import androidx.lifecycle.MutableLiveData
import apps.cdmp.goalhelper.common.Resource

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

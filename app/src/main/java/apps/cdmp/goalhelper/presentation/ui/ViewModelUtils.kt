package apps.cdmp.goalhelper.presentation.ui

import androidx.lifecycle.MutableLiveData
import apps.cdmp.goalhelper.common.Resource

typealias ResourceObserver<T> = MutableLiveData<Resource<T>>

fun <T> loadingResource() = MutableLiveData<ResourceObserver<T>>()

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

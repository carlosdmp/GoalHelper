package apps.cdmp.goalhelper.common

import androidx.lifecycle.MutableLiveData

typealias ResourceObserver<T> = MutableLiveData<Resource<T>>

fun <T> loadingResource() = MutableLiveData<ResourceObserver<T>>()

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

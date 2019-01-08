package apps.cdmp.goalhelper.bindmodel

data class FormField<T>(var value: T, var error: String? = null) {
    val isOk
        get() = error == null
}
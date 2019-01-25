package apps.cdmp.diffadapter.diff

interface CloneableModel<T> where T : CloneableModel<T> {
    fun clone(): T
}
package apps.cdmp.diffrecycler

interface DiffModel<T> where T : DiffModel<T> {
    fun areItemsTheSame(other: T): Boolean
    fun areContentsTheSame(other: T): Boolean
    fun clone() : T
}

interface DiffModelWithID<T, K> : DiffModel<T> where T : DiffModelWithID<T, K> {
    val id: K
    override fun areItemsTheSame(other: T) = id?.equals(other.id) == true
}
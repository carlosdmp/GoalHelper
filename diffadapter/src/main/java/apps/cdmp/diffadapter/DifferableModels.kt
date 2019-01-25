package apps.cdmp.diffadapter

interface DiffModel<T> where T : DiffModel<T> {
    fun areItemsTheSame(other: T): Boolean
    fun areContentsTheSame(other: T): Boolean
}

interface DiffModelWithID<T, K> :
        DiffModel<T> where T : DiffModelWithID<T, K> {
    val id: K
    override fun areItemsTheSame(other: T) = id?.equals(other.id) == true
}

interface DiffStaticModel<T> :
        DiffModel<T> where T : DiffStaticModel<T> {
    override fun areContentsTheSame(other: T) = areItemsTheSame(other)
}

interface DiffStaticModelWithID<T, K> :
        DiffModel<T> where T : DiffModelWithID<T, K> {
    val id: K
    override fun areItemsTheSame(other: T) = id?.equals(other.id) == true
    override fun areContentsTheSame(other: T) = areItemsTheSame(other)
}
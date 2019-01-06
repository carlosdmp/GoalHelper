package apps.cdmp.goalhelper.common

data class Tuple<K, V>(var first: K, var second: V)

public infix fun <A, B> A.and(that: B): Tuple<A, B> = Tuple(this, that)
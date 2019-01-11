package apps.cdmp.goalhelper.common

data class Tuple<K, V>(var first: K, var second: V)
data class Tuple3<A, B, C>(var first: A, var second: B, var third: C)

public infix fun <A, B> A.and(that: B): Tuple<A, B> = Tuple(this, that)
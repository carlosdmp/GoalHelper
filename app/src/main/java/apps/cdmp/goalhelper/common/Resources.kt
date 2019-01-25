package apps.cdmp.goalhelper.common

data class Error(val throwable: Throwable, val customMessage: String? = null)

sealed class Resource<T> {
    inline fun fold(onData: (T) -> Unit = {}, onError: (Error) -> Unit = {}, onLoading: () -> Unit = {}) {
        when (this) {
            is Success -> onData(data)
            is Failure -> onError(error)
            is Loading -> onLoading()
        }
    }
}

class Success<T>(val data: T) : Resource<T>()
class Failure<T>(val error: Error) : Resource<T>()
class Loading<T>(val tempData:T?) : Resource<T>()

fun <T> successWith(data: T) = Success(data)
fun <T> errorFor(error: Error) = Failure<T>(error)
fun <T> stillLoading(tempData: T? = null) = Loading<T>(tempData)

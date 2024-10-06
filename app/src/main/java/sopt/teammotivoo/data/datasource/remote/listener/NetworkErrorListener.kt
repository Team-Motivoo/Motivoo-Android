package sopt.teammotivoo.data.datasource.remote.listener

interface NetworkErrorListener {
    fun onApiCallFailed()
    fun setOnApiCallFailedCallback(callback: () -> Unit)
    fun clearOnApiCallFailedCallback()
}

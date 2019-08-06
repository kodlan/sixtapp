package com.sbardyuk.sixtapp.datasource.retrofit

data class ResultObject<out T>(
    var resultType: ResultType,
    val data: T? = null,
    val message: String? = null
)
//{
//
//    companion object {
//        fun <T> success(data: T?): ResultObject<T> {
//            return ResultObject(ResultType.SUCCESS, data)
//        }
//
//        fun <T> error(message: String): ResultObject<T> {
//            return ResultObject(ResultType.ERROR,  message = message)
//        }
//    }
//}
//

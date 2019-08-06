package com.sbardyuk.sixtapp.common

interface BaseMapper<in A, out B> {

    fun mapFrom(type: A?): B
}
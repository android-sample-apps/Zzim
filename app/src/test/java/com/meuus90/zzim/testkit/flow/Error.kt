package com.meuus90.zzim.testkit.flow

internal sealed class Error {
    object Empty : Error()
    data class Wrapped(val throwable: Throwable) : Error()
}
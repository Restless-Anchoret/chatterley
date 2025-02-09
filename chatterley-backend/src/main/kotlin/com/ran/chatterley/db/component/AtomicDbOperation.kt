package com.ran.chatterley.db.component

interface AtomicDbOperation {
    // todo: support suspend functions here
    suspend fun <T> masterTransaction(operation: () -> T): T
    suspend fun <T> masterTransactionWithOptResult(operation: () -> T?): T?
    suspend fun <T> replicaTransaction(operation: () -> T): T
    suspend fun <T> replicaTransactionWithOptResult(operation: () -> T?): T?
}

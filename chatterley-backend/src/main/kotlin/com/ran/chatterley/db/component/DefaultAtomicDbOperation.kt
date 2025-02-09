package com.ran.chatterley.db.component

import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Component
class DefaultAtomicDbOperation(transactionManager: PlatformTransactionManager) : AtomicDbOperation {

    private val transactionTemplate = TransactionTemplate(transactionManager)

    override suspend fun <T> masterTransaction(operation: () -> T): T {
        return transactionTemplate.execute { operation() }!!
    }

    override suspend fun <T> masterTransactionWithOptResult(operation: () -> T?): T? {
        return transactionTemplate.execute { operation() }
    }

    override suspend fun <T> replicaTransaction(operation: () -> T): T {
        return transactionTemplate.execute { operation() }!!
    }

    override suspend fun <T> replicaTransactionWithOptResult(operation: () -> T?): T? {
        return transactionTemplate.execute { operation() }
    }
}

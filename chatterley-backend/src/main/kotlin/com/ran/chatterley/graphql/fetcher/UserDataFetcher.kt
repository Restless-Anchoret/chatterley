package com.ran.chatterley.graphql.fetcher

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import com.ran.chatterley.generated.types.User
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment

@DgsComponent
class UserDataFetcher {

    @DgsQuery
    fun user(@InputArgument id: String?): User? {
        val id = id ?: throw IllegalArgumentException("Requesting user by headers is not supported yet")
        return USERS.find { it.id == id }
    }

    @DgsQuery
    fun users(
        env: DataFetchingEnvironment,
        @InputArgument nickNameFilter: String?
    ): Connection<User> {
        val nickNameFilterInLowerCase = nickNameFilter?.lowercase().orEmpty()
        val filteredUsers = USERS.filter { it.nickName.lowercase().contains(nickNameFilterInLowerCase) }
        return SimpleListConnection(filteredUsers).get(env)
    }

    companion object {
        private val USERS = listOf(
            User("1234", "nickolas"),
            User("4321", "rose"),
            User("2345", "michael"),
            User("5432", "joan"),
            User("3456", "ravik"),
        )
    }
}

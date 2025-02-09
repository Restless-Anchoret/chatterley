package com.ran.chatterley.db.dao

import com.ran.chatterley.db.model.User
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class UserDao(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    // todo: run queries in coroutines of the separate dispatcher

    fun insert(user: User) {
        val parameterSource = MapSqlParameterSource()
            .addValue("id", user.id)
            .addValue("nickname", user.nickname)
            .addValue("password_hash", user.passwordHash)
            .addValue("create_time", user.createTime.toEpochMilli())
        jdbcTemplate
            .update("insert into users values (:id, :nickname, :password_hash, :create_time)", parameterSource)
    }

    fun findById(id: String): User? {
        val parameterSource = MapSqlParameterSource()
            .addValue("id", id)
        return jdbcTemplate
            .query("select * from users where id = :id", parameterSource, ROW_MAPPER)
            .firstOrNull()
    }

    fun findByNickname(nickname: String): User? {
        val parameterSource = MapSqlParameterSource()
            .addValue("nickname", nickname)
        return jdbcTemplate
            .query("select * from users where nickname = :nickname", parameterSource, ROW_MAPPER)
            .firstOrNull()
    }

    fun selectForUpdate(id: String): User? {
        val parameterSource = MapSqlParameterSource()
            .addValue("id", id)
        return jdbcTemplate
            .query("select * from users where id = :id for update", parameterSource, ROW_MAPPER)
            .firstOrNull()
    }

    fun update(user: User) {
        val parameterSource = MapSqlParameterSource()
            .addValue("id", user.id)
            .addValue("password_hash", user.passwordHash)
        jdbcTemplate
            .update("update users set password_hash = :password_hash where id = :id", parameterSource)
    }

    companion object {
        val ROW_MAPPER: RowMapper<User> = RowMapper { resultSet, _ ->
            User(
                id = resultSet.getString("id"),
                nickname = resultSet.getString("nickname"),
                passwordHash = resultSet.getString("password_hash"),
                createTime = Instant.ofEpochMilli(resultSet.getLong("create_time"))
            )
        }
    }
}

package com.ran.chatterley

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatterleyBackendApplication

fun main(args: Array<String>) {
	runApplication<ChatterleyBackendApplication>(*args)
}

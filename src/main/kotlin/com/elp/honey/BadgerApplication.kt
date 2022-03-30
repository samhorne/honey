package com.elp.honey

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BadgerApplication

fun main(args: Array<String>) {
	runApplication<BadgerApplication>(*args)
}

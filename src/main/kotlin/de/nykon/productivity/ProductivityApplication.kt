package de.nykon.productivity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductivityApplication

fun main(args: Array<String>) {
	runApplication<ProductivityApplication>(*args)
}

package de.nykon.productivity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ProductivityApplication

fun main(args: Array<String>) {
	runApplication<ProductivityApplication>(*args)
}

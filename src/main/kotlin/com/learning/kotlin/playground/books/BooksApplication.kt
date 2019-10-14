package com.learning.kotlin.playground.books

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
//@ComponentScan(basePackages = [ "com.learning.kotlin.playground.books.rest"] )
class BooksApplication

fun main(args: Array<String>) {
	SpringApplication.run(BooksApplication::class.java, *args)
}
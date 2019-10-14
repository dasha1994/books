package com.learning.kotlin.playground.books.rest

import com.learning.kotlin.playground.books.domain.Book
import com.learning.kotlin.playground.books.service.google.GoogleSheetDataProvider
import org.springframework.web.bind.annotation.*

@RestController
class BookController(private val sheetDataProvider : GoogleSheetDataProvider) {

    @GetMapping(path = ["/getBooks"])
    fun getAll(): List<Book> {
        return sheetDataProvider.getGoogleSheetData()
    }
}
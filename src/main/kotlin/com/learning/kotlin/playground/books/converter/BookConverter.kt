package com.learning.kotlin.playground.books.converter

import com.learning.kotlin.playground.books.domain.Book
import org.springframework.stereotype.Component

@Component
class BookConverter {
    fun toBook(row: MutableList<Any>): Book {
        var book: Book = Book()
        book.id = row[0].toString()
        book.title = row[1].toString()
        return book;
    }
}
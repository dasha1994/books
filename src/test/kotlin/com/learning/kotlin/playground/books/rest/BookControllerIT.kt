package com.learning.kotlin.playground.books.rest

import com.learning.kotlin.playground.books.domain.Book
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Test

class BookControllerIT {
    @Test
    fun test_getAll(){
        //given
        val books = getBookList()

        val service = mockk<BookController>()
        every { service.getAll() } returns books

        //when
        val result = service.getAll()

        //then
        verify { service.getAll() }
        assertEquals(books, result)
    }

    private fun getBookList(): List<Book> {
        val book1 = Book("1", "book1")
        val book2 = Book("2", "book2")
        val book3 = Book("3", "book3")
        return arrayListOf(book1, book2, book3)
    }
}
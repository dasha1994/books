package com.learning.kotlin.playground.books.service.google

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import com.learning.kotlin.playground.books.domain.Book
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.Charset

@Service
class GoogleSheetDataProvider(
        private val credentials : GoogleCredentials,
        private val jsonFactoryInstance: JacksonFactory? = JacksonFactory.getDefaultInstance(),
        private val HTTP_TRANSPORT: NetHttpTransport  = GoogleNetHttpTransport.newTrustedTransport()
) {
    @Value("\${application.name}")
    private lateinit var applicationName : String

    @Value("\${google.sheet.id}")
    private lateinit var googleSheetId : String

    //TODO: put into properties
    @Value("прочитано!A2:H")
    private lateinit var  range : String


    fun getGoogleSheetData() : List<Book> {
        var service: Sheets = Sheets.Builder(HTTP_TRANSPORT, jsonFactoryInstance, credentials.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(applicationName)
                .build()

        println(googleSheetId)
        println(range.toByteArray(Charset.defaultCharset()).toString())


        var response: ValueRange = service.spreadsheets().values()
                .get(googleSheetId, range)
                .execute()


        var values = response.getValues()
        var books : MutableList<Book> = mutableListOf()

        if (values == null || values.isEmpty()) {
            println("No data found.")
        } else {
            System.out.println("Name, Major")

            for (row in values) {
                // Print columns A and H, which correspond to indices 0 and 4.
                try {
                    var book : Book = Book()
                    book.id = row[0].toString()
                    book.title = row[1].toString()
                    println(book)
                    books.add(book)
                }catch (ex : Exception){
                    print(row.toString())
                }

            }
        }
        return books
    }
}




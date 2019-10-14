package com.learning.kotlin.playground.books.service.google

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import com.learning.kotlin.playground.books.converter.BookConverter
import com.learning.kotlin.playground.books.domain.Book
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.Charset

@Service
class GoogleSheetDataProvider(
        private val credentials : GoogleCredentials,
        private val jsonFactoryInstance: JacksonFactory? = JacksonFactory.getDefaultInstance(),
        private val HTTP_TRANSPORT: NetHttpTransport  = GoogleNetHttpTransport.newTrustedTransport(),
        private val bookConverter : BookConverter
) {
    private val logger = LoggerFactory.getLogger(javaClass)

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
        
        var response: ValueRange = service.spreadsheets().values()
                .get(googleSheetId, range)
                .execute()


        var values = response.getValues()
        var books : MutableList<Book> = mutableListOf()

        if (values == null || values.isEmpty()) {
            logger.info("No data found")
        } else {
            logger.info("Name, Major")

            for (row in values) {
                try {
                   books.add(bookConverter.toBook(row))
                }catch (ex : Exception){
                    logger.error(row.toString())
                }
            }
        }
        return books
    }
}




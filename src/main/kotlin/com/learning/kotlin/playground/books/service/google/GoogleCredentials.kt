package com.learning.kotlin.playground.books.service.google

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.SheetsScopes
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

@Component
class GoogleCredentials {
    private val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    private val CREDENTIALS_FOLDER = "credentials" // Directory to store user credentials.

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private val SCOPES = listOf(SheetsScopes.SPREADSHEETS_READONLY)
    private val CLIENT_SECRET_DIR = "client_secret.json"

    @Throws(IOException::class)
    fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        // Load client secrets.
        val `in` = GoogleCredentials::class.java!!.getClassLoader().getResourceAsStream(CLIENT_SECRET_DIR)

        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`!!))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(FileDataStoreFactory(File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build()

        val localServerReceiver = LocalServerReceiver.Builder().setPort(8080).build()
        return AuthorizationCodeInstalledApp(flow, localServerReceiver).authorize("user")
    }
}
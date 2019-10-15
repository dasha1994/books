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
    private val jacksonFactory = JacksonFactory.getDefaultInstance()
    private val credentialsDir = "credentials" // Directory to store user credentials.
    private val scopes = listOf(SheetsScopes.SPREADSHEETS_READONLY)
    private val clientSecretDir = "client_secret.json"

    @Throws(IOException::class)
    fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        //TODO: check options
        val input = GoogleCredentials::class.java!!.getClassLoader().getResourceAsStream(clientSecretDir)

        val clientSecrets = GoogleClientSecrets.load(jacksonFactory, InputStreamReader(input!!))

        val googleAuthorizationCodeFlow = GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jacksonFactory, clientSecrets, scopes)
                .setDataStoreFactory(FileDataStoreFactory(File(credentialsDir)))
                .setAccessType("offline")
                .build()

        val localServerReceiver = LocalServerReceiver.Builder().setPort(8080).build()
        return AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, localServerReceiver).authorize("user")
    }
}
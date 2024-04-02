package com.example.journalapp

import java.sql.Timestamp

data class Journal(
    private val title: String,
    private val thoughts: String,
    private val imageUrl: String,

    private val userId: String,
    private val timeAdded: Timestamp,
    private val username: String
) {

}

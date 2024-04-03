package com.example.journalapp

import android.app.Application

class JournalUser : Application() {

    var username : String? = null
    var userId : String? = null

    companion object {
        var instance : JournalUser? = null
        get(){
            if(field == null){
                //create new instance from JournalUser
                field = JournalUser()
            }
            return field
        }

        private set
    }

}
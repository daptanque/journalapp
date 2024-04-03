package com.example.journalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.journalapp.databinding.ActivityAddJournalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.net.URI

class AddJournalActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddJournalBinding

    //credentials
    var currentUserId: String = ""
    var currentUserName: String = ""

    //Firebase
    lateinit var auth : FirebaseAuth
    lateinit var user : FirebaseUser

    //Firebase Firestore
    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference

    var collectionReference: CollectionReference = db.collection("Journal")
    lateinit var imageUri: URI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_journal)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_journal)

        storageReference = FirebaseStorage.getInstance().getReference()

        auth = FirebaseAuth.getInstance()

        binding.apply {

            postProgressBar.visibility = View.INVISIBLE

            if(JournalUser.instance != null){
                currentUserId  = JournalUser.instance!!.userId.toString()

                currentUserName  = JournalUser.instance!!.username.toString()

                postUsernameTextview.text = currentUserName


            }


        }
    }
}
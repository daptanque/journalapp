package com.example.journalapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.journalapp.databinding.ActivityAddJournalBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.net.URI
import java.util.Date

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
    lateinit var imageUrii: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_journal)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_journal)

        storageReference = FirebaseStorage.getInstance().getReference()

        auth = Firebase.auth

        binding.apply {

            postProgressBar.visibility = View.INVISIBLE

            if(JournalUser.instance != null){
                //currentUserId  = JournalUser.instance!!.userId.toString()

                //currentUserName  = JournalUser.instance!!.username.toString()

                currentUserId = auth.currentUser?.uid.toString()
                currentUserName = auth.currentUser?.displayName.toString()

                postUsernameTextview.text = currentUserName

            }

            postCameraButton.setOnClickListener(){
                var i : Intent = Intent(Intent.ACTION_GET_CONTENT)
                i.setType("image/*")
                startActivityForResult(i, 1)
            }

            postSaveJournalButton.setOnClickListener(){
                saveJournal()
            }

        }
    }

    private fun saveJournal() {
        var title : String = binding.postTitleEt.text.toString().trim()
        var thoughts : String = binding.postDescriptionEt.text.toString().trim()

        binding.postProgressBar.visibility  = View.VISIBLE
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thoughts) && imageUrii != null){
            // Saving the path of images in Storage
            // where  ..../journal_images/our_image.png

            val filePath : StorageReference = storageReference
                .child("journal_images")
                .child("my_image_"+ Timestamp.now().seconds.toString())

            filePath.putFile(imageUrii)
                .addOnSuccessListener(){
                    filePath.downloadUrl.addOnSuccessListener {
                        var imageUri : Uri = it
                        var timestamp : Timestamp = Timestamp(Date())

                        //creating the object of Journal
                        var journal : Journal = Journal(
                            title,
                            thoughts,
                            imageUri.toString(),
                            currentUserId,
                            timestamp,
                            currentUserName
                        )

                        collectionReference.add(journal)
                            .addOnSuccessListener(){
                                binding.postProgressBar.visibility = View.INVISIBLE
                                var i : Intent = Intent(this, JournalList::class.java)
                                startActivity(i)
                                finish()
                            }


                    }
                }.addOnFailureListener(){
                    binding.postProgressBar.visibility = View.INVISIBLE
                }

        }else{
            binding.postProgressBar.visibility = View.INVISIBLE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK){
            if(data != null){
                imageUrii = data.data!! //getting
                binding.postImageView.setImageURI(imageUrii) //showin
            }
        }
    }

    override fun onStart() {
        super.onStart()

        user = auth.currentUser!!
    }

    override fun onStop() {
        super.onStop()

        if(auth!=null){

        }
    }
}
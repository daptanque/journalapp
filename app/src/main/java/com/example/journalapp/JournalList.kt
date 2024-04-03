package com.example.journalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.journalapp.databinding.ActivityJournalListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class JournalList : AppCompatActivity() {

    lateinit var binding: ActivityJournalListBinding

    //Firebase References
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    var db = FirebaseFirestore.getInstance()
    lateinit var storageReference : StorageReference
    var collectionReference : CollectionReference = db.collection("Journal")

    lateinit var journalList : List<Journal>
    lateinit var journalRecyclerAdapter: JournalRecyclerAdapter

    lateinit var noPostsTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_journal_list)

        //Firebase Auth
        firebaseAuth = Firebase.auth
        firebaseUser = firebaseAuth.currentUser!!

        //RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
                LinearLayoutManager(this)

        //Posts arrayList
        journalList = arrayListOf<Journal>()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add ->
                if (firebaseUser != null && firebaseAuth != null){
                    val intent = Intent(this, AddJournalActivity::class.java)
                    startActivity(intent)
            }

            R.id.action_signout ->
                if (firebaseUser != null && firebaseAuth != null){
                    firebaseAuth.signOut()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
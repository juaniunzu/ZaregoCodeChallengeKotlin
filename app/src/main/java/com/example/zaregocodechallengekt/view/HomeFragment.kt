package com.example.zaregocodechallengekt.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zaregocodechallengekt.model.User
import com.example.zaregocodechallengekt.databinding.FragmentHomeBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HomeFragment : Fragment(),
    UserAdapter.UserAdapterListener {

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val reference : CollectionReference = db.collection("PhoneBook")
    private lateinit var adapter : UserAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var fabAddEntry : FloatingActionButton
    private lateinit var binding : FragmentHomeBinding
    private lateinit var listener : HomeFragmentListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        fabAddEntry = binding.fragmentHomeFabAddEntry;
        fabAddEntry.setOnClickListener(View.OnClickListener {
            listener.onClickFab()
        })

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        var query : Query = reference.orderBy("name", Query.Direction.ASCENDING)

        var options : FirestoreRecyclerOptions<User> = FirestoreRecyclerOptions.Builder<User>()
            .setQuery(query, User::class.java)
            .build()

        adapter = UserAdapter(options)
        adapter.listener = this
        recyclerView = binding.fragmentHomeRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.adapterPosition)
                Toast.makeText(context, "Entry deleted", Toast.LENGTH_SHORT).show()
            }
        }

        ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView)

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context as HomeFragmentListener)
    }

    override fun onClickUserAdapter(documentSnapshot: DocumentSnapshot, position: Int) {
        listener.onClickUserHomeFragment(documentSnapshot, position)
    }

    interface HomeFragmentListener{
        fun onClickFab()
        fun onClickUserHomeFragment(documentSnapshot : DocumentSnapshot, position : Int)
    }
}

package com.example.zaregocodechallengekt.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zaregocodechallengekt.R
import com.example.zaregocodechallengekt.model.User
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class UserAdapter constructor(options: FirestoreRecyclerOptions<User>) : FirestoreRecyclerAdapter<User, UserAdapter.UserViewHolder>(options) {

    lateinit var listener : UserAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false)

        return UserViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
        holder.onBind(model)
        holder.itemView.setOnClickListener(View.OnClickListener {
            listener.onClickUserAdapter(snapshots.getSnapshot(holder.adapterPosition), holder.adapterPosition)
        })
    }

    fun deleteItem(position: Int){
        snapshots.getSnapshot(position).reference.delete()
    }

    class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        private var textViewName : TextView = itemView.findViewById(R.id.entryItemTextViewName)
        private var textViewLastName : TextView = itemView.findViewById(R.id.entryItemTextViewLastName)
        private var textViewPhoneNumber : TextView = itemView.findViewById(R.id.entryItemTextViewNumber)

        fun onBind(user : User){
            textViewName.text = user.name;
            textViewLastName.text = user.lastName;
            textViewPhoneNumber.text = user.phoneNumber;
        }

    }

    interface UserAdapterListener{
        fun onClickUserAdapter(documentSnapshot: DocumentSnapshot, position: Int)
    }



}
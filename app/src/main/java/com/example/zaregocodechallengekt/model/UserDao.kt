package com.example.zaregocodechallengekt.model

import com.example.zaregocodechallengekt.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UserDao {

    val reference: CollectionReference = FirebaseFirestore.getInstance().collection("PhoneBook")

    fun addUser(user: User){
        reference.add(user)
    }

    fun modifyUser(userId: String, user: User){
        reference.document(userId).set(user)
    }

}
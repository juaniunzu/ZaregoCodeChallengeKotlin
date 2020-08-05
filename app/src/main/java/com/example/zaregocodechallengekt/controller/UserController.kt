package com.example.zaregocodechallengekt.controller

import android.content.Context
import android.widget.Toast
import com.example.zaregocodechallengekt.model.User
import com.example.zaregocodechallengekt.model.UserDao

class UserController  {

    val userDao: UserDao =
        UserDao()


    fun modifyUser(context : Context?, userId : String, user : User){
        userDao.modifyUser(userId, user)
        Toast.makeText(context, "User updated", Toast.LENGTH_SHORT).show()
    }

    fun addUser(context : Context?, user : User){
        userDao.addUser(user)
        Toast.makeText(context, "User added correctly", Toast.LENGTH_SHORT).show()
    }

}

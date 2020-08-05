package com.example.zaregocodechallengekt.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.example.zaregocodechallengekt.R
import com.example.zaregocodechallengekt.model.User
import com.example.zaregocodechallengekt.controller.UserController
import com.example.zaregocodechallengekt.databinding.FragmentAddUserBinding
import com.google.android.material.textfield.TextInputEditText


class AddUserFragment : Fragment() {

    companion object {
        const val USER : String = "user"
        const val USER_ID : String = "userId"
    }
    private lateinit var editTextName : TextInputEditText
    private lateinit var editTextLastName : TextInputEditText
    private lateinit var editTextNumber : TextInputEditText
    private lateinit var userController : UserController
    private var updateUser : Boolean = false
    private var modifyEntry : Bundle? = null
    private lateinit var binding : FragmentAddUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddUserBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        userController =
            UserController()

        editTextName = binding.fragmentAddUserTextInputEditTextName
        editTextLastName = binding.fragmentAddUserTextInputEditTextLastName
        editTextNumber = binding.fragmentAddUserTextInputEditTextNumber

        modifyEntry = arguments
        if(modifyEntry == null){
            Toast.makeText(context, "Create new entry", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Modify entry", Toast.LENGTH_SHORT).show()
            var user : User = modifyEntry!!.getSerializable(
                USER
            ) as User
            editTextName.setText(user.name)
            editTextLastName.setText(user.lastName)
            editTextNumber.setText(user.phoneNumber)
            updateUser = true;
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_entry_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_entry -> if (checkFields()){
                if(updateUser){
                    updateUser(modifyEntry?.getString(USER_ID)!!)
                    fragmentManager?.popBackStack()
                    fragmentManager?.popBackStack()
                    hideKeyboard(context, binding.root)
                } else {
                    saveUser()
                    fragmentManager?.popBackStack()
                    hideKeyboard(context, binding.root)
                }
            } else {
                Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT).show();
            }else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun checkFields() : Boolean{
        var name = editTextName.text.toString()
        var lastName = editTextLastName.text.toString()
        var number = editTextNumber.text.toString()

        return !(name.trim().isEmpty() || lastName.trim().isEmpty() || number.trim().isEmpty())
    }

    private fun updateUser(userId : String){
        var name = editTextName.text.toString()
        var lastName = editTextLastName.text.toString()
        var number = editTextNumber.text.toString()

        userController.modifyUser(context, userId,
            User(name, lastName, number)
        )
    }

    private fun saveUser(){
        var name = editTextName.text.toString()
        var lastName = editTextLastName.text.toString()
        var number = editTextNumber.text.toString()

        userController.addUser(context,
            User(name, lastName, number)
        )
    }

    private fun hideKeyboard(context: Context?, view: View){
        val inputMethodManager : InputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }

}

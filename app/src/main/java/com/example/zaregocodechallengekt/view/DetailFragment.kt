package com.example.zaregocodechallengekt.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.zaregocodechallengekt.model.User
import com.example.zaregocodechallengekt.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    companion object {
        const val USER : String = "user"
        const val USER_ID : String = "userId"
    }
    private lateinit var name : TextView
    private lateinit var lastName : TextView
    private lateinit var number : TextView
    private lateinit var buttonEdit : Button
    private lateinit var listener : DetailFragmentListener
    private lateinit var binding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        name = binding.fragmentDetailTextViewName
        lastName = binding.fragmentDetailTextViewLastName
        number = binding.fragmentDetailTextViewNumber
        buttonEdit = binding.fragmentDetailButtonEdit

        var userBundle : Bundle = this!!.arguments!!
        var clickedUser : User = userBundle.getSerializable(
            USER
        ) as User
        var clickedUserId : String = userBundle.getString(USER_ID)!!

        name.setText(clickedUser.name)
        lastName.setText(clickedUser.lastName)
        number.setText(clickedUser.phoneNumber)

        buttonEdit.setOnClickListener(View.OnClickListener {
            listener.onClickEditEntry(clickedUserId, clickedUser)
        })


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DetailFragmentListener
    }

    interface DetailFragmentListener{
        fun onClickEditEntry(userId : String, user : User)
    }

}

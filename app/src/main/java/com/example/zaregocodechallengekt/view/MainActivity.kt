package com.example.zaregocodechallengekt.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.zaregocodechallengekt.R
import com.example.zaregocodechallengekt.model.User
import com.google.firebase.firestore.DocumentSnapshot

class MainActivity : AppCompatActivity(), HomeFragment.HomeFragmentListener, DetailFragment.DetailFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment(HomeFragment());


    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.activityMainFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClickFab() {
        setFragment(AddUserFragment())
    }

    override fun onClickUserHomeFragment(documentSnapshot: DocumentSnapshot, position: Int) {
        var detailFragment: DetailFragment =
            DetailFragment()
        var detailBundle: Bundle = Bundle()
        val user = documentSnapshot.toObject(User::class.java)

        detailBundle.putSerializable(DetailFragment.USER, user)
        detailBundle.putString(DetailFragment.USER_ID, documentSnapshot.id)
        detailFragment.arguments = detailBundle
        setFragment(detailFragment)
    }

    override fun onClickEditEntry(userId: String, user: User) {
        var addUserFragment: AddUserFragment =
            AddUserFragment()
        var modifyUser: Bundle = Bundle()

        modifyUser.putString(AddUserFragment.USER_ID, userId)
        modifyUser.putSerializable(AddUserFragment.USER, user)
        addUserFragment.arguments = modifyUser
        setFragment(addUserFragment)

    }


}

package com.example.thebarberbuisness

import android.R.attr.password
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth


class check_auth : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        mAuth!!.createUserWithEmailAndPassword("harshkanani007@gmail.com", "password")
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(FragmentActivity.TAG, "createUserWithEmail:success")
                    Toast.makeText(this@check_auth, "Authentication Successfull" +
                            ".",Toast.LENGTH_SHORT).show()
                    val user = mAuth!!.currentUser
                    Toast.makeText(this@check_auth, user?.uid.toString(),Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    /*Log.w(
                        FragmentActivity.TAG,
                        "createUserWithEmail:failure",
                        task.exception
                    )*/
                    Toast.makeText(this@check_auth, "Authentication failed.",Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }

                // ...
            }

    }
}
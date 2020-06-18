package com.example.thebarberbuisness

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var sp = getSharedPreferences("MySp",Activity.MODE_PRIVATE)
        var unm = sp.getString("unm",null)
        if(unm != null){
            startActivity(Intent(this@Login,Dashboard::class.java))
            finish()
        }

        lblreg.setOnClickListener {
            startActivity(Intent(this@Login,Registration::class.java))

        }
        btnlogin.setOnClickListener {
            progressBar2.visibility= View.VISIBLE
            mAuth = FirebaseAuth.getInstance();

            mAuth!!.signInWithEmailAndPassword(txtunm.text.toString(), txtpwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "signInWithEmail:success")
                        val user = mAuth!!.currentUser
                        progressBar2.visibility= View.GONE
                        var sp=getSharedPreferences("MySp",Activity.MODE_PRIVATE)
                        var edt = sp.edit()
                        if (user != null) {
                            edt.putString("unm","${user.uid.toString()}")
                        }
                        edt.apply()
                        edt.commit()
                        startActivity(Intent(this@Login,Dashboard::class.java))
                        finish()
                        // updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "signInWithEmail:failure", task.exception)
                        progressBar2.visibility= View.GONE
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        // updateUI(null)
                        // ...
                    }

                    // ...
                }


        }
    }
}

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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {

    private var tokensender:String?=null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var sp = getSharedPreferences("MySp",Activity.MODE_PRIVATE)
     //   var unm = sp.getString("unm",null)
        mAuth=FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser

        if(user != null){
            startActivity(Intent(this@Login,Dashboard::class.java))
            finish()
        }

        lblreg.setOnClickListener {
            startActivity(Intent(this@Login,Registration::class.java))

        }
        btnlogin.setOnClickListener {
            progressBar2.visibility= View.VISIBLE
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Shop")

            mAuth!!.signInWithEmailAndPassword(txtunm.text.toString(), txtpwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "signInWithEmail:success")
                        mAuth=FirebaseAuth.getInstance()
                        val user = mAuth!!.currentUser
                        progressBar2.visibility= View.GONE
                        var sp=getSharedPreferences("MySp",Activity.MODE_PRIVATE)
                        var edt = sp.edit()
                        if (user != null) {
                            edt.putString("unm","${user.uid.toString()}")
                            sendtokentoserver()
                            myRef.child(user!!.uid).addListenerForSingleValueEvent(object :ValueEventListener{



                                override fun onCancelled(p0: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    var value=p0.getValue(ShopData::class.java)
                                    sendtokentoserver()
                                    value!!.token= tokensender.toString()
                                    myRef.child(user.uid).setValue(value)
                                }
                            })
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
    fun sendtokentoserver()
    {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                // Log and toast
                val msg = token
                tokensender = msg
                Log.d("TAG", msg)

            })
    }
}

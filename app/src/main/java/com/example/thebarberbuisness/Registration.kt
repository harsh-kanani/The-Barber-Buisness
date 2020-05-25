package com.example.thebarberbuisness

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.SyncStateContract
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.internal.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.coroutines.Continuation


class Registration : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var d:ByteArray?=null
    private var username:String?=null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null )
        {
            filePath=data.data
            var bitmap=MediaStore.Images.Media.getBitmap(contentResolver,filePath)
            imgupload.setImageBitmap(bitmap)

//            imgupload.isDrawingCacheEnabled = true
//            imgupload.buildDrawingCache()
//            val bitmap = (imgupload.drawable as BitmapDrawable).bitmap
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//             d= baos.toByteArray()



        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        lbllogin.setOnClickListener {
            startActivity(Intent(this@Registration,Login::class.java))
            finish()
        }


        imgupload.setOnClickListener {

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }



        btnreg.setOnClickListener {
            uploadImage()
            var shopData=ShopData(txtusernm.text.toString(),txtmail.text.toString(),txtmno.text.toString()," "," ",txtpass.text.toString()," "," ","Open"," "," "," ")
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Shop")
            username=txtusernm.text.toString()
            myRef.child("${txtusernm.text.toString()}").setValue(shopData).addOnCompleteListener {
                Toast.makeText(this@Registration,"Successfully Register",Toast.LENGTH_LONG).show()
                var sp=getSharedPreferences("MySp", Activity.MODE_PRIVATE)
                var edt = sp.edit()
                edt.putString("unm","${txtusernm.text.toString()}")
                edt.apply()
                edt.commit()
                startActivity(Intent(this@Registration,otherInfo::class.java))
                finish()
            }

        }
    }


    private fun uploadImage(){
        storageReference = FirebaseStorage.getInstance().reference

        if(filePath != null){
            progressBar.visibility=View.VISIBLE
            val ref = storageReference?.child("uploads/" +  UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            if (uploadTask != null) {
              var imgdata=  ref
                uploadTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = ref.downloadUrl

                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("Shop")
                        myRef.child(username.toString()).child("imgurl").setValue(imgdata.toString())
                        progressBar.visibility=View.GONE
                        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                    } else {
                        // Handle failures
                    }
                }
                uploadTask.addOnFailureListener{
                    progressBar.visibility=View.GONE

                    Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }

    }
}

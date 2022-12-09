package com.doc.roseya.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.doc.roseya.base.BaseActivity
import com.doc.roseya.R
import com.doc.roseya.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
class RegisterActivity : BaseActivity() ,  View.OnClickListener {
    private lateinit var nama: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var notelp: TextInputEditText
    private lateinit var password: TextInputEditText
    var ResposeCode = "";
    var ResposeMessage = "";
    var ResposeData = "";
    var _id_user = String()
    var _nama = String()
    var _email = String()
    var _Role = String()
    var _no_telp = String()
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = sdf.format(Date())
//    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var tvcirRegisterButton = findViewById<Button>(R.id.cirRegisterButton)
        tvcirRegisterButton.setOnClickListener(this)
        val tv_click_login = findViewById<TextView>(R.id.cirLoginButton)
    tv_click_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        nama = findViewById(R.id.editTextName)
        email = findViewById(R.id.editTextEmail)
        notelp = findViewById(R.id.editTextMobile)
        password = findViewById(R.id.editTextPassword)

//    nama.setText("ww")
//    email.setText("ita@gmail.com")
//    notelp.setText("019898332")
//    password.setText("123")
    }
    private fun KirimDataRegistrasi() {

        val multipartBody: MultipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM) // Header to show we are sending a Multipart Form Data
            .addFormDataPart("nama", nama.text.toString())
            .addFormDataPart("email", email.text.toString())
            .addFormDataPart("no_telp", notelp.text.toString()) // file param
            .addFormDataPart("password", password.text.toString())
            .addFormDataPart("role", "A001")
            .build()

        val request: Request = Request.Builder()
            .url("http://roseya.my.id/api/index.php/Data") // pass the url endpoint of api
            .post(multipartBody) // pass the mulipart object we just created having data
            .build()

        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBodyCopy3 = response.peekBody(Long.MAX_VALUE)
//                        responseBodyCopy.string();
                    val JSONObject = JSONObject(responseBodyCopy3.string())
                    ResposeCode = JSONObject.getString("ResponseCode")
                    ResposeMessage = JSONObject.getString("ResponseMessage")



                    if(ResposeCode.equals("0")){
                        object : Thread() {
                            override fun run() {
                                this@RegisterActivity.runOnUiThread(Runnable {
                                    Toast.makeText(this@RegisterActivity, "Anda Sukses Mendaftar", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                })
                            }
                        }.start()

                    } else if(ResposeCode.equals("1")) {
                        object : Thread() {
                            override fun run() {
                                this@RegisterActivity.runOnUiThread(Runnable {
//                                    val alertDialogBuilder = AlertDialog.Builder(this@RegisterActivity)
                                    val builder = AlertDialog.Builder(this@RegisterActivity, R.style.RoundedCornersDialog)
                                    builder.setTitle("INFO")
                                    builder.setMessage(ResposeMessage)
                                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
//                                        Toast.makeText(applicationContext,
//                                            android.R.string.yes, Toast.LENGTH_SHORT).show()
                                    }

                                    builder.show()
//                                    Toast.makeText(this@RegisterActivity, ResposeMessage, Toast.LENGTH_LONG).show()
                                    //Do your UI operations like dialog opening or Toast here
                                })
                            }
                        }.start()

                    } else {
                        object : Thread() {
                            override fun run() {
                                this@RegisterActivity.runOnUiThread(Runnable {
                                    val builder = AlertDialog.Builder(this@RegisterActivity)
                                    builder.setTitle("INFO")
                                    builder.setMessage(ResposeMessage)
                                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
//                                        Toast.makeText(applicationContext,
//                                            android.R.string.yes, Toast.LENGTH_SHORT).show()
                                    }

                                    builder.show()
//                                    Toast.makeText(this@RegisterActivity, ResposeMessage, Toast.LENGTH_LONG).show()
                                    //Do your UI operations like dialog opening or Toast here
                                })
                            }
                        }.start()
                    }

                } else {
                    // handle different cases for different status codes or dump them all here
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cirRegisterButton ->{

                if (nama.text.toString().isEmpty()){
                    nama.setError("Wajib diisi");

                }
                if (email.text.toString().isEmpty()) {
                    email.setError("Wajib diisi");
                }else if (!email.text.toString().isEmpty()) {
                   if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                        Toast.makeText(this, "Masukan Email yang sesuai !", Toast.LENGTH_SHORT).show();
                    }
                }

                if (notelp.text.toString().isEmpty()) {
                    notelp.setError("Wajib diisi");
                } else if (!notelp.text.toString().isEmpty()){
                    if (!Patterns.PHONE.matcher(notelp.text.toString()).matches()) {
                        Toast.makeText(this, "Masukan No.Telp yang sesuai !", Toast.LENGTH_SHORT).show();
                    }
                }
                if (password.text.toString().isEmpty()) {
                    password.setError("Wajib diisi");
                }

                if (!nama.text.toString().isEmpty() && !email.text.toString().isEmpty() && !notelp.text.toString().isEmpty() && !password.text.toString().isEmpty()
                    &&Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() && Patterns.PHONE.matcher(notelp.text.toString()).matches()){
                    KirimDataRegistrasi()
                }

            }
        }
    }



}
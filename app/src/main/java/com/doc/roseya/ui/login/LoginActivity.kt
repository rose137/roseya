package com.doc.roseya.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.doc.roseya.base.BaseActivity
import com.doc.roseya.model.LoginModel
import com.doc.roseya.session.PrefManager
import com.doc.roseya.MainActivity
import com.doc.roseya.R
import com.doc.roseya.ui.register.RegisterActivity
import com.google.android.material.textfield.TextInputEditText
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : BaseActivity() ,  View.OnClickListener {
    private lateinit var email: TextInputEditText
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
    private lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        checkLogin()
        var tv_click_Login = findViewById<Button>(R.id.cirLoginButton)
        tv_click_Login.setOnClickListener(this)

        val tv_click_Register = findViewById<TextView>(R.id.cirRegisterButton)
        tv_click_Register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            // your code to perform when the user clicks on the TextView
//            Toast.makeText(this@LoginActivity, "You clicked on TextView 'Click Me'.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(){
        prefManager = PrefManager(this)
        email = findViewById(R.id.editTextEmail)
        password = findViewById(R.id.editTextPassword)
        email.setText("ita@gmail.com")
        password.setText("123")
    }

    private fun checkLogin(){
        if (prefManager.isLogin()!!){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun GetDataLogin() {

        val multipartBody: MultipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM) // Header to show we are sending a Multipart Form Data
            .addFormDataPart("email", email.text.toString()) // file param
            .addFormDataPart("password", password.text.toString())
            .build()

        val request: Request = Request.Builder()
            .url("http://roseya.my.id/api/index.php/Data?email="+email.text.toString()+"&password="+password.text.toString()+"") // pass the url endpoint of api
            .get() // pass the mulipart object we just created having data
            .build()

        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBodyCopy2 = response.peekBody(Long.MAX_VALUE)
//                        responseBodyCopy.string();
                    val JSONObject = JSONObject(responseBodyCopy2.string())
                    ResposeCode = JSONObject.getString("ResponseCode")
                    ResposeMessage = JSONObject.getString("ResponseMessage")



                    if(ResposeCode.equals("0")){
                        ResposeData = JSONObject.getString("data")
                        var Data = ResposeData.replace("[","").replace("]","")
                        val JSONObject2 = JSONObject(Data)
                        _id_user = JSONObject2.getString("id_user")
                        _nama = JSONObject2.getString("nama")
                        _email = JSONObject2.getString("email")
                        _Role = JSONObject2.getString("Role")
                        _no_telp = JSONObject2.getString("no_telp")
                        Thread {

                            val data = db.LoginDao().getSingle(email.text.toString())
                            if (data == null){

                                db.LoginDao().insert(
                                    LoginModel(
                                        id_user = _id_user,
                                        nama = _nama,
                                        email = _email,
                                        Role = _Role,
                                        no_telp = _no_telp,
                                        IsLogin = "1",
                                        date = currentDate
                                    )
                                )
                                prefManager.setLoggin(true)
                                prefManager.setemail(email.text.toString())

                                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                                startActivity(intent)
                            } else if (data != null){
//                                db.LoginDao().deleteAll()
                                db.LoginDao().update(
                                    LoginModel(
                                        id = data.id,
                                        id_user = _id_user,
                                        nama = _nama,
                                        email = _email,
                                        Role = _Role,
                                        no_telp = _no_telp,
                                        IsLogin = "1",
                                        date = currentDate
                                    )
                                )
                                prefManager.setLoggin(true)
                                prefManager.setemail(email.text.toString())

                                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                                startActivity(intent)
                            }
                        }.start()
                    } else if(ResposeCode.equals("1")) {
                        object : Thread() {
                            override fun run() {
                                this@LoginActivity.runOnUiThread(Runnable {
                                    Toast.makeText(this@LoginActivity, "Email / Password Salah", Toast.LENGTH_SHORT).show()
                                    //Do your UI operations like dialog opening or Toast here
                                })
                            }
                        }.start()

                    } else {
                        object : Thread() {
                            override fun run() {
                                this@LoginActivity.runOnUiThread(Runnable {
                                    Toast.makeText(this@LoginActivity, "Email / Password Salah", Toast.LENGTH_SHORT).show()
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
            R.id.cirLoginButton ->{

                if (email.text.toString().isEmpty()){
                    email.setError("Wajib diisi");

                }
                if (password.text.toString().isEmpty()) {
                    password.setError("Wajib diisi");
                }

                if ( !email.text.toString().isEmpty() && !password.text.toString().isEmpty()){
                    GetDataLogin()
                }
            }
        }
    }

}
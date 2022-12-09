package com.doc.roseya.ui.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.doc.roseya.R
import com.doc.roseya.room.databaseDao.DatabaseDAO
import com.doc.roseya.session.PrefManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.item_image.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.math.log

class ProfileFragment : Fragment() {
    private lateinit var prefManager: PrefManager
    lateinit var db: DatabaseDAO
    lateinit var database: DatabaseDAO
    private lateinit var txt_name: TextView
    private lateinit var txtphone: TextView
    private lateinit var txtemail: TextView
    private  lateinit var txt_addinformation : TextView
    private lateinit var btn_ok : Button
    private lateinit var btn_cancel : Button
    private lateinit var btn_ok_pass : Button
    private lateinit var btn_cancel_pass : Button
    private lateinit var txt_input_alamat: TextInputEditText
    private lateinit var txt_pass: TextInputEditText
    private lateinit var txt_konfrim_pass: TextInputEditText
    private lateinit var alamat : String
    companion object {
        fun newInstance() = ProfileFragment()
    }

//    private lateinit var viewModel: ProfileViewModel

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        prefManager = PrefManager(requireActivity())
        database = DatabaseDAO.getInstance(requireActivity())
        db = Room.databaseBuilder(requireActivity(), DatabaseDAO::class.java, "rose.db")
            .allowMainThreadQueries().build()
        val data = db.LoginDao().getUserName(prefManager.getemail().toString())
        txt_name = rootView.findViewById(R.id.txt_username)
        txtphone = rootView.findViewById(R.id.txt_phone)
        txtemail = rootView.findViewById(R.id.txt_email)
        txt_addinformation = rootView.findViewById(R.id.txt_add_information)



        txt_name.text =   data.nama.toString()
        txtphone.text = data.no_telp.toString()
        txtemail.text = data.email.toString()


        txt_addinformation.setOnClickListener {
            showAlertDialog(R.layout.dialog_update_alamat)


        }

        return rootView;
    }


    private fun showAlertDialog(dialogNegativeLayout: Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        val view = View.inflate(context,R.layout.dialog_update_alamat,null);
        btn_ok = view.findViewById(R.id.btnOkDialogUpdateAlamat)
        btn_cancel = view.findViewById(R.id.btncancelDialogUpdateAlamat)
        txt_input_alamat = view.findViewById(R.id.editTextAlamat)
        alertDialogBuilder.setView(view)
        val alertDialog = alertDialogBuilder.create();
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
        btn_cancel.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
        btn_ok.setOnClickListener(View.OnClickListener {

            if (txt_input_alamat.text.toString().isEmpty() || txt_input_alamat.text.toString() == null){
                txt_input_alamat.setError("Wajib diisi");
            } else {
                alamat = txt_input_alamat.text.toString()
                showAlertDialog2(R.layout.dialog_konfirmasi_password)
            }



        })
    }

    private fun showAlertDialog2(dialogNegativeLayout: Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        val view = View.inflate(context,R.layout.dialog_konfirmasi_password,null);
        btn_ok_pass = view.findViewById(R.id.btnOkDialogKonfirmasiPassword)
        btn_cancel_pass = view.findViewById(R.id.btnCancelDialogKonfirmasiPassword)
        txt_pass = view.findViewById(R.id.editTextPassword)
        txt_konfrim_pass = view.findViewById(R.id.editTextKonfirmPassword)
        alertDialogBuilder.setView(view)
        val alertDialog = alertDialogBuilder.create();
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
        btn_cancel_pass.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
        btn_ok_pass.setOnClickListener(View.OnClickListener {
           if (txt_pass.text.toString().isEmpty() || txt_pass.text.toString() == null){
               txt_pass.setError("Wajib diisi");
           }
            if (txt_konfrim_pass.text.toString().isEmpty() || txt_konfrim_pass.text.toString() == null){
                txt_konfrim_pass.setError("Wajib diisi");
            }
            if (!txt_pass.text.toString().isEmpty() && !txt_konfrim_pass.text.toString().isEmpty()){
                updateAlamat()
            }
        })

    }

    private fun updateAlamat() {
        val multipartBody: MultipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", prefManager.getemail().toString())
            .addFormDataPart("password", txt_pass.text.toString())
            .addFormDataPart("alamat", alamat)
            .build()

        val request: Request = Request.Builder()
            .url("http://192.168.56.1/api2/index.php/Updatealamat")
            .put(multipartBody)
            .build()

        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBodyUpdateAlamat = response.peekBody(Long.MAX_VALUE)
//                        responseBodyCopy.string();
                    val JSONObject = JSONObject(responseBodyUpdateAlamat.string())
                    Log.d("RESPONSE", responseBodyUpdateAlamat.string())
                }
            }

        })
    }
}
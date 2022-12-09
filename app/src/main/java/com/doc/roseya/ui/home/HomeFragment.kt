package com.doc.roseya.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.room.Room
import com.doc.roseya.R
import com.doc.roseya.adapter.IlmuwanMainAdapter
import com.doc.roseya.adapter.SliderPagerAdapter
import com.doc.roseya.decoration.BannerSlider
import com.doc.roseya.decoration.SliderIndicator
import com.doc.roseya.fragment.FragmentJadwalSholat
import com.doc.roseya.fragment.FragmentSlider
import com.doc.roseya.model.IlmuwanModel
import com.doc.roseya.room.databaseDao.DatabaseDAO
import com.doc.roseya.session.PrefManager
import com.doc.roseya.ui.alquran.MainAlquranActivity
import com.doc.roseya.ui.doa.DoaActivity
import com.doc.roseya.ui.ilmuwan.DetailIlmuwanActivity
import com.doc.roseya.ui.ilmuwan.data.ItemClickSupport
import com.doc.roseya.ui.ilmuwan.data.MainData
import com.doc.roseya.ui.kisahnabi.KisahNabiActivity
import com.doc.roseya.ui.login.LoginActivity
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_kategori.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    lateinit var db: DatabaseDAO
    lateinit var database: DatabaseDAO

    private lateinit var txt_tanggal: TextView
    private lateinit var txt_hari: TextView
    private lateinit var txt_lokasi: TextView
    private lateinit var txt_name: TextView
    private lateinit var lyt_alquran : ImageView
    private lateinit var btn_time : ImageView
    private lateinit var btn_lokasi_masjid : ImageView
    private lateinit var txt_detailLocation: LinearLayout //detalilLocation
    private lateinit var txt_lokasi_dtl: TextView
    private lateinit var btn_positive : Button
    private lateinit var btn_negative : Button
    private lateinit var btn_negative_alamat: Button
    private lateinit var  lyt_surat : ImageView
    private lateinit var  lyt_kisahnabi : ImageView
    lateinit var AlamatDetail: String
    lateinit var strDateNow: String
    var localeIndonesia = Locale("id", "ID")
    var client: FusedLocationProviderClient? = null
    private val myClipboard: ClipboardManager? = null
    private val myClip: ClipData? = null
    //    val appContext = requireContext().applicationContext
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var prefManager: PrefManager

    private lateinit var rvMain: RecyclerView
//    private val rvMain: RecyclerView? = null
    private var mAdapter: SliderPagerAdapter? = null
    private var mIndicator: SliderIndicator? = null
    var one: Animation? = null
    var two: Animation? = null
    private lateinit var  mLinearLayout : LinearLayout
    private lateinit var  bannerSlider : BannerSlider
    private val list: ArrayList<IlmuwanModel> = ArrayList<IlmuwanModel>()


    companion object {
        fun newInstance() = HomeFragment()

    }

    fun HomeFragment() {
        // Required empty public constructor
    }
//    private lateinit var viewModel: HomeViewModel

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        prefManager = PrefManager(requireActivity())
        prefManager.getemail();
        database = DatabaseDAO.getInstance(requireActivity())
        db = Room.databaseBuilder(requireActivity(), DatabaseDAO::class.java, "rose.db")
            .allowMainThreadQueries().build()
//        prefHelper.getStringFromShared(SharedPrefKeys.companyCode)
        val data = db.LoginDao().getSingle(prefManager.getemail().toString())
        bannerSlider = rootView.findViewById(R.id.sliderView)
        mLinearLayout = rootView.findViewById(R.id.pagesContainer)
        //        txtFooter = findViewById(R.id.txt_footer);
        rvMain = rootView.findViewById<RecyclerView>(R.id.rv_main)
        rvMain.setHasFixedSize(true)
        list.addAll(MainData.getListData())
        // load animation

        // load animation
        one = AnimationUtils.loadAnimation(requireActivity(), R.anim.one)
        two = AnimationUtils.loadAnimation(requireActivity(), R.anim.two)
        rvMain.startAnimation(one)

        showRecyclerList()

        setupSlider()
//        thiscontext = container?.getContext();
        val dateNow = Calendar.getInstance().time
        strDateNow = DateFormat.format("d MMMM yyyy", dateNow) as String
        val currentDateDay: String = SimpleDateFormat("EEEE", localeIndonesia).format(Date())
        txt_tanggal = rootView.findViewById(R.id.tanggal)
        txt_hari = rootView.findViewById(R.id.hari)
        txt_lokasi = rootView.findViewById(R.id.tvCurrentLocation)
        txt_detailLocation = rootView.findViewById(R.id.detalilLocation)
        txt_name = rootView.findViewById(R.id.txt_name)
        lyt_alquran = rootView.findViewById(R.id.cvQuran)
        btn_time = rootView.findViewById(R.id.layoutTime)
        btn_lokasi_masjid = rootView.findViewById(R.id.layoutMosque)
        lyt_surat = rootView.findViewById(R.id.cvSurat)
//        btn_positive = rootView.findViewById(R.id.btnPo)
        lyt_kisahnabi = rootView.findViewById(R.id.cvCerita)

        txt_tanggal.setText(strDateNow)
        txt_hari.setText(currentDateDay)
        txt_name.setText(data.nama)


        val jadwalSholat = FragmentJadwalSholat.newInstance("Jadwal Shalat")
        btn_time.setOnClickListener {
            jadwalSholat.show(
                requireActivity().supportFragmentManager, jadwalSholat.tag
            )
        }

        btn_lokasi_masjid.setOnClickListener {
            showAlertDialog(R.layout.dialog_negative_layout)


        }

        lyt_alquran.setOnClickListener(){
            val intent = Intent(requireActivity(), MainAlquranActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        lyt_surat.setOnClickListener(){
            val intent = Intent(requireActivity(), DoaActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        lyt_kisahnabi.setOnClickListener(){
            val intent = Intent(requireActivity(), KisahNabiActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        client = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            // When permission is granted
            // Call method
            getCurrentLocation();
        } else {

            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                100)
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }
//            Toast.makeText(requireActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
    }



    return rootView;//inflater.inflate(R.layout.fragment_home, container, false)

}

    private fun showRecyclerList() {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvMain)
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        rvMain.layoutManager = linearLayoutManager
        val listMainAdapter = IlmuwanMainAdapter(requireActivity())
        listMainAdapter.listMain = list
        rvMain.adapter = listMainAdapter

        ItemClickSupport.addTo(rvMain).setOnItemClickListener(object :
            ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {
                // Menyimpan data ke dalam model
                val mainModel = IlmuwanModel()
                mainModel.setName(list[position].getName())
                mainModel.setYear(list[position].getYear())
                mainModel.setDesc(list[position].getDesc())
                mainModel.setPhoto(list[position].getPhoto())

                // Berpindah halaman dengan membawa data yang sudah disimpan di dalam model
                startActivity(
                    Intent(requireActivity(), DetailIlmuwanActivity::class.java).putExtra(
                        "EXTRA_KEY",
                        mainModel
                    )
                )


            }
        })
    }

    private fun setupSlider() {
        bannerSlider.setDurationScroll(800)
        val fragments: MutableList<Fragment> = ArrayList()

        //link image

        //link image
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_17b56894-2608-4509-a4f4-ad86aa5d3b62.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_7da28e4c-a14f-4c10-8fec-845578f7d748.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/18/85531617/85531617_87a351f9-b040-4d90-99f4-3f3e846cd7ef.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/20/85531617/85531617_03e76141-3faf-45b3-8bcd-fc0962836db3.jpg"))

        mAdapter = SliderPagerAdapter(requireActivity().supportFragmentManager, fragments)
        bannerSlider.adapter = mAdapter
        mIndicator = SliderIndicator(requireActivity(), mLinearLayout, bannerSlider, R.drawable.indicator_circle)
        mIndicator?.setPageCount(fragments.size)
        mIndicator?.show()
    }

    private fun showAlertDialog(dialogNegativeLayout: Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        val view = View.inflate(context,R.layout.dialog_negative_layout,null);
        btn_negative = view.findViewById(R.id.btnDialog)
        alertDialogBuilder.setView(view)
        val alertDialog = alertDialogBuilder.create();
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
        btn_negative.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
    }

    @SuppressLint("MissingPermission")
private fun getCurrentLocation() {
    // Initialize Location manager
    val locationManager =
        requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    // Check condition
    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    ) {
        client!!.lastLocation.addOnCompleteListener { task ->
            val location = task.result
            if (location != null) {
try{
    var geocoder = Geocoder(requireActivity(), Locale.getDefault())
    var addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
    var detail : String = addresses[0].getAddressLine(0)
    var stateName: String = addresses[0].subAdminArea


    txt_lokasi.setText(stateName)
    AlamatDetail = detail;
}catch (ex:Exception){
    fragmentManager?.beginTransaction()?.detach(requireParentFragment())?.attach(requireParentFragment())?.commit();
}

                // set longitude
//                    tvLongitude.setText(
//                        location
//                            .longitude.toString())
            } else {
                val locationRequest: LocationRequest =
                    LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000).setFastestInterval(1000).setNumUpdates(1)

                // Initialize location call back
                val locationCallback: LocationCallback = object : LocationCallback() {
                    override fun onLocationResult(
                        locationResult: LocationResult,
                    ) {
                        val location1 = locationResult.lastLocation
                        if (location1 != null) {
                            try{
                                var geocoder = Geocoder(requireActivity(), Locale.getDefault())
                                var addresses: List<Address> = geocoder.getFromLocation(location1.latitude, location1.longitude, 1)
                                var detail : String = addresses[0].getAddressLine(0)
                                var stateName: String = addresses[0].subAdminArea


                                txt_lokasi.setText(stateName)
                                AlamatDetail = detail;
                            }catch (ex:Exception){
                                fragmentManager?.beginTransaction()?.detach(requireParentFragment())?.attach(requireParentFragment())?.commit();
                            }
                        }
                        // Set longitude
//                            tvLongitude.setText(
//                                location1
//                                    .getLongitude().toString())
                    }
                }

                client!!.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.myLooper())
            }
        }
    } else {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

//    txt_detailLocation.setOnClickListener()

    txt_detailLocation.setOnClickListener(object : View.OnClickListener {
        override fun onClick(view: View?) {
            object : Thread() {
                override fun run() {
                    requireActivity().runOnUiThread(Runnable {
                        showAlertDialog2(R.layout.dialog_alamat_detail)
//                                    val alertDialogBuilder = AlertDialog.Builder(this@RegisterActivity)
//                        val builder = AlertDialog.Builder(requireActivity(), R.style.RoundedCornersDialog)
//                        builder.setTitle("DETAIL ALAMAT")
//                        builder.setMessage(AlamatDetail)
//                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
////                                        Toast.makeText(applicationContext,
////                                            android.R.string.yes, Toast.LENGTH_SHORT).show()
//                        }
//                        builder.setNeutralButton("COPY"){ dialog, which ->
//                            var txt = AlamatDetail
//                            var myClip = ClipData.newPlainText("text", txt);
//                            myClipboard?.setPrimaryClip(myClip);
//                            Toast.makeText(requireContext(), "Text Copied",
//                                Toast.LENGTH_SHORT).show();
//                        }
//
//                        builder.show()
//                                    Toast.makeText(this@RegisterActivity, ResposeMessage, Toast.LENGTH_LONG).show()
                        //Do your UI operations like dialog opening or Toast here
                    })
                }
            }.start()
        }

    })
}
    private fun showAlertDialog2 (dialogNegativeLayout: Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())

        val view = View.inflate(context,R.layout.dialog_alamat_detail,null);
        btn_negative_alamat = view.findViewById(R.id.btnDialogAlamat)
        txt_lokasi_dtl = view.findViewById(R.id.textalamat)
        txt_lokasi_dtl.setText(AlamatDetail)
        alertDialogBuilder.setView(view)
        val alertDialog = alertDialogBuilder.create();
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
//        alertDialog.setMessage(AlamatDetail)
        alertDialog.show();
        btn_negative_alamat.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
    }

}
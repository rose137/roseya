package com.doc.roseya.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "login")
data class LoginModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "iduser")
    @SerializedName("iduser") var id_user: String? = null,

    @ColumnInfo(name = "nama")
    @SerializedName("nama") var nama: String? = null,

    @ColumnInfo(name = "email")
    @SerializedName("email") var email: String? = null,

    @ColumnInfo(name = "role")
    @SerializedName("role") var Role: String? = null,

    @ColumnInfo(name = "no_telp")
    @SerializedName("no_telp") var no_telp: String? = null,


    @ColumnInfo(name = "islogin")
    @SerializedName("islogin") var IsLogin: String? = null,

    @ColumnInfo(name = "date")
    @SerializedName("date") var date: String? = null,



) : Parcelable
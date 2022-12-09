package com.doc.roseya.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

class IlmuwanModel() : Parcelable {
    private var name: String? = null
    private var year: String? = null
    private var desc: String? = null
    private var photo: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        year = parcel.readString()
        desc = parcel.readString()
        photo = parcel.readString()
    }


    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
    fun getYear(): String? {
        return year
    }

    fun setYear(year: String?) {
        this.year = year
    }

    fun getDesc(): String? {
        return desc
    }

    fun setDesc(desc: String?) {
        this.desc = desc
    }

    fun getPhoto(): String? {
        return photo
    }

    fun setPhoto(photo: String?) {
        this.photo = photo
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(year)
        dest.writeString(desc)
        dest.writeString(photo)
    }


    fun MainModel() {}

//    private fun MainModel(`in`: Parcel) {
//        name = `in`.readString()
//        year = `in`.readString()
//        desc = `in`.readString()
//        photo = `in`.readString()
//    }

    companion object CREATOR : Creator<IlmuwanModel> {
        override fun createFromParcel(parcel: Parcel): IlmuwanModel {
            return IlmuwanModel(parcel)
        }

        override fun newArray(size: Int): Array<IlmuwanModel?> {
            return arrayOfNulls(size)
        }
    }


}
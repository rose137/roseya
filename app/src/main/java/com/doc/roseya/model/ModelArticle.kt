package com.doc.roseya.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName



@SuppressLint("ParcelCreator")
data class ModelArticle(
        @SerializedName("source")
        var modelSource: ModelSource?,

        @SerializedName("author")
        var author: String = "",

        @SerializedName("title")
        var title: String = "",

        @SerializedName("description")
        var description: String = "",

        @SerializedName("url")
        var url: String = "",

        @SerializedName("urlToImage")
        var urlToImage: String = "",

        @SerializedName("publishedAt")
        var publishedAt: String = ""
) : Parcelable {
        override fun describeContents(): Int {
                TODO("Not yet implemented")
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
                TODO("Not yet implemented")
        }
}

data class ModelSource(
        @SerializedName("id")
        val id: String? = "",

        @SerializedName("name")
        val name: String? = ""
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(id)
                parcel.writeString(name)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<ModelSource> {
                override fun createFromParcel(parcel: Parcel): ModelSource {
                        return ModelSource(parcel)
                }

                override fun newArray(size: Int): Array<ModelSource?> {
                        return arrayOfNulls(size)
                }
        }
}

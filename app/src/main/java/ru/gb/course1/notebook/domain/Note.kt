package ru.gb.course1.notebook.domain

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator

class Note : Parcelable {
    var idNote = 0
    var title: String?
    var description: String?
    var timeCreate: String? = null

    constructor(idNote: Int, title: String?, description: String?, timeCreate: String?) {
        this.idNote = idNote
        this.title = title
        this.description = description
        this.timeCreate = timeCreate
    }

    constructor(title: String?, description: String?) {
        this.title = title
        this.description = description
    }

    constructor(`in`: Parcel) {
        idNote = `in`.readInt()
        title = `in`.readString()
        description = `in`.readString()
        timeCreate = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(idNote)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(timeCreate)
    }

    companion object CREATOR : Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}


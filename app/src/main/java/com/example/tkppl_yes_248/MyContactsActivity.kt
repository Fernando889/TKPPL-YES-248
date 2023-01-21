package com.example.tkppl_yes_248

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.database.Cursor
import android.provider.ContactsContract
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_contacts.*

class MyContactsActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<Cursor> {
    var displayName = ContactsContract.Contacts.DISPLAY_NAME
    var number = ContactsContract.CommonDataKinds.Phone.NUMBER
    var myListContact: MutableList<myContacts> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_contacts)
        LoaderManager.getInstance(this).initLoader(1, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        var MyContentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        var myProjection = arrayOf(displayName, number)
        return CursorLoader(
            this, MyContentUri, myProjection,
            null, null, "$displayName ASC"
        )
    }

    @SuppressLint("Range")
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        myListContact.clear()
        if (data != null) {
            data.moveToFirst()
            while (!data.isAfterLast) {
                myListContact.add(
                    myContacts(
                        nama = data.getString(data.getColumnIndex(displayName)),
                        nomorHp = data.getString(data.getColumnIndex(number))
                    )
                )
                data.moveToNext()
            }
        }
        val contactAdapter = myContactsAdapter(myListContact)
        recycleContacts.apply {
            layoutManager = LinearLayoutManager(this@MyContactsActivity)
            adapter = contactAdapter
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        recycleContacts.adapter?.notifyDataSetChanged()
    }
}
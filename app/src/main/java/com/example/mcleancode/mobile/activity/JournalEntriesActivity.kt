package com.example.mcleancode.mobile.activity

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.ArrayAdapter
import android.widget.ListView

import com.example.mcleancode.mobile.R
import com.example.mcleancode.mobile.database.helpers.MobileDataSource
import com.example.mcleancode.mobile.database.schema.LocationSchema

class JournalEntriesActivity : FragmentActivity() {

    private val mLocations = ArrayList<String>()
    private var mMobileDataSource: MobileDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_entries)
        mMobileDataSource = MobileDataSource(this)
    }

    override fun onResume() {
        super.onResume()

        mMobileDataSource!!.open()

        val listView = listView()
        val cursor = mMobileDataSource!!.selectAllLocations()

        populateLocationsList(listView, cursor)
        registerLocationsEvents(listView)
    }

    override fun onPause() {
        super.onPause()
        mMobileDataSource!!.close()
    }

    private fun listView(): ListView {
        return findViewById(R.id.journalEntriesList) as ListView
    }

    private fun populateLocationsList(listView: ListView, cursor: Cursor) {
        mLocations.clear()

        cursor.moveToFirst()

        while(!cursor.isAfterLast) {
            val nameIndex = cursor.getColumnIndex(LocationSchema.Table.COLUMN_NAME_TITLE)
            val name = cursor.getString(nameIndex)
            mLocations.add(name)
            cursor.moveToNext()
        }
        cursor.close()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mLocations)
        listView.adapter = arrayAdapter

    }

    private fun registerLocationsEvents(listView: ListView) {
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, JournalEntryActivity::class.java)
            startActivity(intent)
        }
    }
}

package com.example.tkppl_yes_248

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_get_help.*

class GetHelpActivity : AppCompatActivity() {

    private lateinit var listViewAdapter: ExpandableListViewAdapter
    private lateinit var chapterList : List<String>
    private lateinit var topicList : HashMap<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_help)

        //back actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showList()
        Log.d("GetHelp", "Sedang Berada di Menu Get Help")

        listViewAdapter = ExpandableListViewAdapter(this, chapterList, topicList)
        eListView.setAdapter(listViewAdapter)
    }

    private fun showList() {

        chapterList = ArrayList()
        topicList = HashMap()

        (chapterList as ArrayList<String>).add("Cara Order")
        (chapterList as ArrayList<String>).add("Cara Menggunakan HairStyle Filter")
        (chapterList as ArrayList<String>).add("App Version")

        val topic1 : MutableList<String> = ArrayList( )
        topic1.add("1. Login Terlebih Dahulu")
        topic1.add("2. Menuliskan Nama Anda terlebih dahulu di dalam Application tersebut, jika tidak menuliskan nama Anda maka tidak bisa mengordernya.")
        topic1.add("3. Di bagian Home anda dapat memilih HairStyle sesuai keinginan masing - masing.")
        topic1.add("4. Jika sudah tau mau pilih HairStyle yang mana langsung klik HairStyle tersebut dan akan lanjut ke Halaman Detailnya.")
        topic1.add("5. Setelah itu ada menu tambahan apakah mau cukur atau tidak setelah memilih dan klik Order maka akan masuk ke dalam Maps untuk mengordernya.")
        topic1.add("6. Setelah memilih lokasi yang fix maka teken Order Now dan sistem akan mencari tukang cukur.")
        topic1.add("7. Memasuki ke halaman dimana ada ID Card si tukang cukur yang muncul, ada juga namanya tercantum di dalam ID Card nya.")
        topic1.add("8. Setelah itu, sewaktu mengklik Track Order maka akan muncul Detail dari si Barber.")
        topic1.add("9. Sewaktu Barber sudah sampai maka akan muncul sebuah Toast untuk menyatakan barbernya sudah sampai di lokasi.")
        topic1.add("10. Setelah itu anda mengklik next, akan muncul sebuah detail Payment beserta qrcode untuk membayar melalui OVO.")
        topic1.add("11. Setelah Payment Selesai maka akan muncul Payment Successfull dan akan balik ke halaman Login")

        val topic2 : MutableList<String> = ArrayList( )
        topic2.add("1. Mengklik Fab Button dengan Icon kamera.")
        topic2.add("2. Memilih terlebih dahulu filter HairStylenya.")
        topic2.add("3. Setelah itu baru Capture Image.")
        topic2.add("4. Sesudah Capture Image maka akan balik ke halaman sebelumnya.")

        val topic3 : MutableList<String> = ArrayList()
        topic3.add("1.0.1")

        topicList[chapterList[0]] = topic1
        topicList[chapterList[1]] = topic2
        topicList[chapterList[2]] = topic3
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
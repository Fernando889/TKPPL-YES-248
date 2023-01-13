package com.example.tkppl_yes_248

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tkppl_yes_248.MyContactsActivity
import com.example.tkppl_yes_248.R
import com.example.tkppl_yes_248.ScheduleActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AppointmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppointmentFragment : Fragment(), EmployeeBarberAdapter.OnExploreClickListener{

    lateinit var floatingActionButton: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appointment, container, false)
    }
    //variable untuk implementasi database data
    private lateinit var discussionlist: ArrayList<EmployeeBarber>
    private lateinit var discussionRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycleExplore)
        val itemList = createDummy(10)
        recyclerView?.adapter = EmployeeBarberAdapter(itemList,this@AppointmentFragment)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        floatingActionButton = view.findViewById(R.id.fab_btn_contacts)
        floatingActionButton.setOnClickListener {
            val intent1 = Intent(view.context, MyContactsActivity::class.java)
            startActivity(intent1)

            Log.d("Home", "Sedang Memasuki Menu Filter")
        }
    }

    override fun onExploreClicked(position: Int, item: EmployeeBarber) {
        val intent = Intent(context, ScheduleActivity::class.java).apply {
            putExtra(INTENT_PARCELABLE, item)
        }
        startActivity(intent)
    }
    private fun createDummy (n : Int) : ArrayList<EmployeeBarber>{
        val listOfItem = arrayListOf<EmployeeBarber>()
        for (i in 0..n){
            listOfItem.add(EmployeeBarber(R.drawable.ic_baseline_person_24, "Barber $i", "Professional"))
        }
        return listOfItem
    }


    companion object {
        val INTENT_PARCELABLE  = "OBJECT_INTENT"

        @JvmStatic
        fun newInstance(myObject: EmployeeBarber): AppointmentFragment {
            val fragment = AppointmentFragment()
            val args = Bundle()
            args.putParcelable(HomeFragment.INTENT_PARCELABLE, myObject)
            fragment.arguments = args
            return fragment
        }
    }
}
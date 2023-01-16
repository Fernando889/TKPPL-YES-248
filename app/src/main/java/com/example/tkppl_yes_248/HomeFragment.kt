package com.example.tkppl_yes_248

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val EXTRA_STATUS = "EXTRA_STATUS"
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var getHelpButton: ImageButton
    lateinit var uploadButton: ImageButton
    lateinit var recyclerView: RecyclerView
    lateinit var resultTextView: TextView
    lateinit var greetingtext: TextView
    lateinit var floatingActionButton: FloatingActionButton
    private lateinit var myObject: HairStyle
    lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            myObject = requireArguments().getParcelable(INTENT_PARCELABLE)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //result textView
        resultTextView = view.findViewById(R.id.textGreeting)
        greetingtext = view.findViewById(R.id.textView2)

        //setText
        resultTextView.text = "Hi "+ "Customer" + " 👋"
        greetingtext.text = "Welcome Back!"

        getHelpButton = view.findViewById(R.id.getHelpBut)
        getHelpButton.setOnClickListener {
            val intent0 = Intent(view.context, GetHelpActivity::class.java)
            startActivity(intent0)

            Log.d("Home", "Sedang Memasuki Menu GetHelp")
        }

        /*uploadButton = view.findViewById(R.id.uploadBut)
        uploadButton.setOnClickListener {
            val intent0 = Intent(view.context, ManageDataActivity::class.java)
            startActivity(intent0)

            Log.d("Home", "Sedang Memasuki Menu manageData")
        }*/

        floatingActionButton = view.findViewById(R.id.fab_btn)
        floatingActionButton.setOnClickListener {
            val intent1 = Intent(view.context, ChooseFilterActivity::class.java)
            startActivity(intent1)

            Log.d("Home", "Sedang Memasuki Menu Filter")
        }

        recyclerView = view.findViewById(R.id.rv)
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        val hairStyleList = listOf<HairStyle>(
            HairStyle(
                "HairStyle 1",
                "Rp 60.000",
                R.drawable.hairstyle1
            ),
            HairStyle(
                "HairStyle 2",
                "Rp 60.000",
                R.drawable.hairstyle2
            ),
            HairStyle(
                "HairStyle 3",
                "Rp 60.000",
                R.drawable.hairstyle3
            ),
            HairStyle(
                "HairStyle 4",
                "Rp 60.000",
                R.drawable.hairstyle4
            ),
            HairStyle(
                "HairStyle 5",
                "Rp 60.000",
                R.drawable.hairstyle5
            ),
            HairStyle(
                "HairStyle 6",
                "Rp 60.000",
                R.drawable.hairstyle6
            ),
            HairStyle(
                "HairStyle 7",
                "Rp 50.000",
                R.drawable.hairstyle7
            ),
            HairStyle(
                "HairStyle 8",
                "Rp 50.000",
                R.drawable.hairstyle8
            )
        )

        recyclerView.setHasFixedSize(true)
        val adapter = CustomAdapter(view.context, hairStyleList) {
            val intent2 = Intent(view.context, DetailActivity::class.java)
            intent2.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent2)
        }
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        val INTENT_PARCELABLE  = "OBJECT_INTENT"

        @JvmStatic
        fun newInstance(myObject: HairStyle): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putParcelable(INTENT_PARCELABLE, myObject)
            fragment.arguments = args
            return fragment
        }

    }
}
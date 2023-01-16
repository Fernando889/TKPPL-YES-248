package com.example.tkppl_yes_248

import com.example.tkppl_yes_248.R
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class EmployeeBarber(val ImageProfile: Int = R.drawable.ic_baseline_person_24,
                          val Username: String = "Guest",
                          val DiscussionContent: String = "test") : Parcelable
package com.example.hackio

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hackio.databinding.FragmentUpcomingBinding
import android.provider.AlarmClock
import android.provider.CalendarContract
import androidx.annotation.RequiresApi
import java.util.*


class UpcomingFragment : Fragment(),Uplisten {

    private val sharedViewModel: ContestViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentUpcomingBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.dataup = sharedViewModel
        // Giving the binding access to the OverviewViewModel
        // Sets the adapter of the photosGrid RecyclerView
        binding.recyclerViewup.adapter = Upadapter(this)

        return binding.root

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpcomingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onclicked(hit: ContestsItem) {
        val bundle = Bundle().apply {
            putSerializable("contest", hit)
        }
        val intent = Intent(activity, Wbview::class.java)
        intent.putExtra("website", hit.url)
        startActivity(intent)
    }
    @RequiresApi(Build.VERSION_CODES.S)
    override fun timerupcoming(foodPhoto: ContestsItem) {
        val year:String = foodPhoto.start_time.substring(0,4)
        val month:String =foodPhoto.start_time.substring(5,7)
        val date:String = foodPhoto.start_time.substring(8,10)
        val hour:String =foodPhoto.start_time.substring(11,13)
        val minutes:String =foodPhoto.start_time.substring(14,16)
        val year2:String = foodPhoto.end_time.substring(0,4)
        val month2:String =foodPhoto.end_time.substring(5,7)
        val date2:String = foodPhoto.end_time.substring(8,10)
        val hour2:String =foodPhoto.end_time.substring(11,13)
        val minutes2:String =foodPhoto.end_time.substring(14,16)
        val beginCal: Calendar = Calendar.getInstance()
        beginCal.set(year.toInt(),month.toInt(),date.toInt(),hour.toInt(),minutes.toInt())
        val endcal:Calendar= Calendar.getInstance()
        endcal.set(year2.toInt(),month2.toInt(),date2.toInt(),hour2.toInt(),minutes2.toInt())
        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI;
        intent.putExtra(CalendarContract.Events.TITLE,foodPhoto.name);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, foodPhoto.site);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, foodPhoto.in_24_hours);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCal.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endcal.timeInMillis);
        startActivity(intent);
    }
}
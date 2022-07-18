package com.steve.msms.presentation.ui

import android.Manifest
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.steve.msms.R
import com.steve.msms.databinding.FragmentPieChartBinding
import com.steve.msms.domain.model.SmsData
import com.steve.msms.presentation.viewmodel.FragmentPieChartViewModel
import com.steve.msms.utils.EventObject
import com.steve.msms.utils.SMS_PERMISSION_REQUEST
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
@ActivityScoped
class FragmentPieChart : Fragment(R.layout.fragment_pie_chart) {

    private lateinit var binding: FragmentPieChartBinding
    private lateinit var smsData: SmsData
    private val viewModel: FragmentPieChartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("The user in Loan Application")

        setHasOptionsMenu(true)

        binding = FragmentPieChartBinding.bind(view)
        (activity as AppCompatActivity).supportActionBar!!.title = "Transactions"
        getSmsPermission()

    }


    private fun getSmsPermission() {

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_SMS),
                SMS_PERMISSION_REQUEST
            )

        } else {
          getMessages()


        }

        EventObject.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { message ->
                if (message == "Permission Granted") {
                   getMessages()
                } else {
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun getMessages() {
                lifecycleScope.launch {
                    viewModel.sms.observe(viewLifecycleOwner, Observer {




//                        if (it != null) {
//                           // setPie(it)
          //                 smsData = it
//                            Log.d("SmsHere", it.toString())
//                        } else {
//                            Log.d("SmsHere", "Empty")
//                        }
                    })
                }
    }
//
//    private fun SaveData(smsData: SmsData){
//        binding.btnSave.setOnClickListener() {
//
//            //val smsData = viewModel.sms
//            val csv = File(this.filesDir, "test.csv")
//            val valuesSeparator = ","
//            val lineTerminator = "\r\n"
//            val rowTerminator = "["
//            //write header, overwrite file if it has existed
//            csv.writeText(
//                smsData.keys.joinToString(
//                    separator = valuesSeparator,
//                    postfix = lineTerminator
//                )
//            )
//            //write rows
//            val nRows = smsData.values.maxOf { it.size }   // if size of all lists are not equal
//            //val nRows =   Sdata.values.first().size        // if size of all lists are equal
//            for (i in 0 until nRows) {
//                val row = smsData.values
//                    //.map { it[i] }               // if it's guaranteed that size of all lists is the same
//                    .map { it.getOrElse(i) { "" } } // otherwise
//                    .joinToString(separator = valuesSeparator, postfix = lineTerminator)
//                csv.appendText(row)
//
//            }
//            Log.d(TAG, "CSV VIEW: $smsData")
//            Toast.makeText(context, "Data Saved to Documents", Toast.LENGTH_SHORT).show()
//        }
//
//    }

//    private fun setPie(smsData: SmsData) {
//
//
//        binding.anyChart.setProgressBar(binding.progress)
//        binding.creditAmount.text = requireContext().getString(
//            R.string.amount_debited_or_credited,
//            viewModel.getformatedAmount(smsData.TotalCreditedAmount)
//        )
//        binding.debitAmount.text = requireContext().getString(
//            R.string.amount_debited_or_credited,
//            viewModel.getformatedAmount(smsData.totalDebitedAmount)
//        )
//
//        val data = ArrayList<DataEntry>()
//        data.add(ValueDataEntry("Total Income", smsData.TotalCreditedAmount))
//        data.add(ValueDataEntry("Total Expenses", smsData.totalDebitedAmount))
//        val pie = AnyChart.pie()
//
//        pie.setOnClickListener(object :
//            ListenersInterface.OnClickListener(arrayOf("x", "value")) {
//            override fun onClick(event: Event) {
//                lifecycleScope.launch(Dispatchers.Main) {
//                    delay(100)
//                    openFragmentList(smsData, event)
//                }
//            }
//        })
//        pie.data(data)
//
//        binding.anyChart.setChart(pie)
//
//    }
//
//    override fun onPause() {
//        binding.anyChart.clear()
//        super.onPause()
//    }
//
//    private fun openFragmentList(
//        smsData: SmsData,
//        event: Event
//    ) {
//        val name = event.data["x"].toString()
//
//        val bundle = Bundle()
//
//        if (name == "Total Income") {
//
//            bundle.putParcelableArrayList(
//                "list",
//                smsData.creditSmsList as java.util.ArrayList<out Parcelable>
//            )
//            bundle.putString("type", "Credit Messages")
//
//            binding.root.findNavController().navigate(R.id.toFragmentList, bundle)
//
//        } else {
//            bundle.putParcelableArrayList(
//                "list",
//                smsData.DebitSmsList as java.util.ArrayList<out Parcelable>
//            )
//            bundle.putString("type", "Debit Messages")
//
//            binding.root.findNavController().navigate(R.id.toFragmentList, bundle)
//
//        }
//
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        if (item.itemId == R.id.action_search) {
//            binding.root.findNavController().navigate(R.id.toFragmentList)
//        }
//        return super.onOptionsItemSelected(item)
//    }
//

}

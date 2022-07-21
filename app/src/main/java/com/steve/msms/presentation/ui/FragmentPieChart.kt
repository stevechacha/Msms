package com.steve.msms.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.steve.msms.R
import com.steve.msms.data.csv.CsvUtils
import com.steve.msms.databinding.FragmentPieChartBinding
import com.steve.msms.domain.model.Message
import com.steve.msms.domain.model.SmsData
import com.steve.msms.domain.model.User
import com.steve.msms.presentation.viewmodel.FragmentPieChartViewModel
import com.steve.msms.utils.EventObject
import com.steve.msms.utils.SMS_PERMISSION_REQUEST
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

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
            })
        }
    }

//
//    private fun Registration() {
//        val email = binding.Name.text.toString()
//        val password = binding.passwordd.text.toString()
//
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(context, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        Timber.tag(ContentValues.TAG).d("Attempting to create user with email: %s", email)
//
//        // Firebase Authentication to create a user with email and password
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                if (!it.isSuccessful) return@addOnCompleteListener
//
//                // else if successful
//                it.result.user?.let { it1 ->
//                    Timber.tag(ContentValues.TAG).d("Successfully created user with uid: %s", it1.uid)
//                }
//
//                uploadImageToFirebaseStorage()
//            }
//            .addOnFailureListener {
//                Timber.tag(ContentValues.TAG).d("Failed to create user: %s", it.message)
//                Toast.makeText(
//                    context,
//                    "Failed to create user: ${it.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//    }
//
//    @SuppressLint("TimberArgCount")
//    private fun uploadImageToFirebaseStorage() {
//
//        // Storage
//        val filename = UUID.randomUUID().toString()
//
//        val messageList = arrayListOf<Message>()
//
//        val mFile = this?.let { CsvUtils.toCsvFile(it, messageList, "sms_data") }
//        Timber.i("File Exists: ${mFile.exists()}")
//        mFile?.toUri()
//
//        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
//        ref.putFile(mFile.toUri())
//            .addOnSuccessListener {
//                Timber.tag(ContentValues.TAG).d("Successfully uploaded image: %s", )
//
//                ref.downloadUrl.addOnSuccessListener {
//                    Timber.tag(ContentValues.TAG).d("File Location: %s", it)
//
//                    val list = arrayListOf<Message>()
//
//                    saveUserToFirebaseDatabase(list)
//                }
//            }
//            .addOnFailureListener {
//                Timber.tag(ContentValues.TAG).d("Failed to upload image to storage: %s", it.message)
//            }
//    }
//
//    private fun saveUserToFirebaseDatabase(list: List<Message>) {
//        val uid = FirebaseAuth.getInstance().uid ?: ""
//        val rf = FirebaseDatabase.getInstance("https://msms-81a20-default-rtdb.firebaseio.com/").getReference("/users/$uid")
//
//        val user = User(uid, binding.Name.text.toString(), list)
//
//        rf.setValue(user)
//            .addOnSuccessListener {
//                Timber.tag(ContentValues.TAG).d("Finally we saved the user to Firebase Database")
//            }
//            .addOnFailureListener {
//                Timber.tag(ContentValues.TAG).d("Failed to set value to database: %s", it.message)
//            }
//    }

}


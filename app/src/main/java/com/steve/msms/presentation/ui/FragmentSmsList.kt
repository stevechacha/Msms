package com.steve.msms.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.steve.msms.R
import com.steve.msms.databinding.FragmentSmsListBinding
import com.steve.msms.domain.model.Message
import com.steve.msms.presentation.adapter.MessageAdapter
import com.steve.msms.presentation.ui.AddTagModalSheet
import com.steve.msms.presentation.viewmodel.FragmentListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class FragmentSmsList : Fragment(R.layout.fragment_sms_list) {
    private lateinit var binding: FragmentSmsListBinding
    private val viewModel: FragmentListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        Timber.i("The user is in Sms ")

        binding = FragmentSmsListBinding.bind(view)


        val list = arguments?.getParcelableArrayList<Message>("list") as List<Message>

        setAdapter(list)


    }

    private fun setAdapter(list: List<Message>) {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter =
            MessageAdapter(
                list as ArrayList<Message>,
//                { tag: String?, message: Message -> addOrEditClicked(tag, message) },
//                { message: Message -> checkIfMessageHasTag(message) }
            )

    }



    private fun checkIfMessageHasTag(message: Message): String? =
        viewModel.checkIfMessagedHasTag(message.id)

    private fun addOrEditClicked(tag: String?, message: Message) {

        val modalSheet =
            AddTagModalSheet()

        val bundle = Bundle()
        bundle.putString("tag", tag)
        bundle.putParcelable("message", message)
        modalSheet.arguments = bundle
        modalSheet.show(requireActivity().supportFragmentManager, "")

    }
}

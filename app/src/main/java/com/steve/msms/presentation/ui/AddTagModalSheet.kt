package com.steve.msms.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.steve.msms.R
import com.steve.msms.databinding.AddTagLayoutBinding
import com.steve.msms.domain.model.Message
import com.steve.msms.presentation.viewmodel.AddTagViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTagModalSheet() : BottomSheetDialogFragment() {

    private lateinit var binding: AddTagLayoutBinding

    private val viewModel: AddTagViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = AddTagLayoutBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.viewModel = viewModel

        val bundle = this.arguments
        val message = bundle!!.getParcelable<Message>("message")
        val tag = bundle.getString("tag")
        viewModel.message = message

        if (tag != null) viewModel.tag = tag

        viewModel.tagAdded.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                dialog?.dismiss()
            }
        })


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val bottomSheet = dialog!!.findViewById<View>(R.id.design_bottom_sheet)
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        behavior.peekHeight = (resources.displayMetrics.heightPixels * 3 / 4).toInt()
        view?.requestLayout()

    }
}
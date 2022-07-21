package com.example.nycschools.UI

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nycschools.Model.Scores
import com.example.nycschools.Network.NetworkState
import com.example.nycschools.ViewModel.SchoolsViewModel
import com.example.nycschools.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding : FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[SchoolsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater)

        viewModel.scoresLV.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NetworkState.LOADING -> {
                    // loding message or spinner
                    binding.pbLoading.isIndeterminate = true
                }
                is NetworkState.SUCCESS<*> -> {
                    val scores = (state as NetworkState.SUCCESS<List<Scores>>).response
                    // here you display the score in the screen

                    binding.pbLoading.visibility = View.GONE
                    // should only be one in the list but eh?
                    binding.apply {
                        for (item in scores)
                        {
                            tvDetailsNumTakers.text = item.num_of_sat_test_takers
                            tvDetailsWriting.text = item.sat_writing_avg_score
                            tvDetailsMath.text = item.sat_math_avg_score
                            tvDetailsReading.text = item.sat_critical_reading_avg_score
                        }
                    }
                }
                is NetworkState.ERROR -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.apply {
                        tvDetailsSchoolName.visibility = View.GONE
                        tvDetailsDBN.visibility = View.GONE
                        tvDetailsNumTakers.visibility = View.GONE
                        tvDetailsWriting.visibility = View.GONE
                    }
                    Toast.makeText(requireContext(), "No scores available", Toast.LENGTH_LONG).show()
                    // you reset the scores view
                }
            }
        }

        viewModel.schools?.let {
            // here i set the name, overview, location, website
            binding.apply {
                tvDetailsDBN.text = it.dbn
                tvDetailsSchoolName.text = it.schoolName
            }
        } ?: run {
            AlertDialog.Builder(requireActivity())
                .setTitle("ERROR HAS OCCURRED")
                .setMessage("NO SCHOOL SET TO GET DETAILS")
                .setNegativeButton("CANCEL") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }



        viewModel.getSATScoresBySchool()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
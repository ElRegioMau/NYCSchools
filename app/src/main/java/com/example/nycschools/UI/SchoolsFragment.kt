package com.example.nycschools.UI

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nycschools.Adapters.SchoolsRecyclerViewAdapter
import com.example.nycschools.Model.Schools
import com.example.nycschools.Network.NetworkState
import com.example.nycschools.R
import com.example.nycschools.ViewModel.SchoolsViewModel
import com.example.nycschools.databinding.FragmentSchoolsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SchoolsFragment : Fragment() {

    private var _binding : FragmentSchoolsBinding? = null
    private val binding: FragmentSchoolsBinding get() = _binding!!

    private val schoolAdapter by lazy {
        SchoolsRecyclerViewAdapter {
            viewModel.schools = it
            // move to details
            setSchool(it)
        }
    }

    //private lateinit var schoolAdapter2: SchoolsRecyclerViewAdapter

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[SchoolsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolsBinding.inflate(LayoutInflater.from(context),container,false)

        configureObserver()
        return binding.root
    }

    private fun configureObserver() {
        viewModel.schoolsLV.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NetworkState.LOADING -> {
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_LONG).show()
                    // loding message or spinner
                }
                is NetworkState.SUCCESS<*> -> {
                    Toast.makeText(requireContext(), "Success!", Toast.LENGTH_LONG).show()
                    val schools = (state as NetworkState.SUCCESS<List<Schools>>).response
                    binding.apply {
                        //schoolAdapter2 = SchoolsRecyclerViewAdapter(schools as MutableList<Schools>, openDetails = ::setSchool)
                        schoolAdapter.setSchool(schools)
                        //rvSchoolList.layoutManager = LinearLayoutManager(requireContext())
                        rvSchoolList.adapter = schoolAdapter

                    }
                    //binding.rvSchoolList.adapter = schoolAdapter
                    // HERE you update the adapter for the recycler view
                }
                is NetworkState.ERROR -> {
                    AlertDialog.Builder(requireActivity())
                        .setTitle("ERROR HAS OCCURRED")
                        .setMessage(state.error.localizedMessage)
                        .setNegativeButton("CANCEL") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
            }
        }
    }

    private fun setSchool(school: Schools)
    {
        viewModel.setSchool(school)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fcvContainer, DetailsFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
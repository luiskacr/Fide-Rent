package com.example.fide_rent.ui.rent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.fide_rent.databinding.FragmentSelectDatesBinding
import com.example.fide_rent.viewmodel.SelectDateViewModel

class Select_dates_Fragment : Fragment() {

    //Get Data from Fragment Navigation
    private val args by navArgs<Select_dates_FragmentArgs>()

    private var _binding: FragmentSelectDatesBinding? = null

    private val binding get() = _binding!!
    private lateinit var selectDateViewModel: SelectDateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        selectDateViewModel = ViewModelProvider(this).get(SelectDateViewModel::class.java)
        _binding = FragmentSelectDatesBinding.inflate(inflater,container,false)



        binding.cardata.text = args.car.toString()

        // Inflate the layout for this fragment
        return binding.root
    }

}
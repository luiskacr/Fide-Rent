package com.example.fide_rent.ui.rent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fide_rent.adapter.CarSelectorAdapter
import com.example.fide_rent.databinding.FragmentSelectCarBinding
import com.example.fide_rent.viewmodel.SelectCarViewModel


class select_car : Fragment() {

    private var _binding: FragmentSelectCarBinding? = null

    private val binding get() = _binding!!
    private lateinit var selectCarViewModel: SelectCarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectCarViewModel = ViewModelProvider(this).get(SelectCarViewModel::class.java)
        _binding = FragmentSelectCarBinding.inflate(inflater,container,false)

        val CarSelectorAdapter = CarSelectorAdapter()
        val recycler = binding.carsOptions

        recycler.adapter = CarSelectorAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        selectCarViewModel.getCars.observe(viewLifecycleOwner){
            cars -> CarSelectorAdapter.setCars(cars)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}
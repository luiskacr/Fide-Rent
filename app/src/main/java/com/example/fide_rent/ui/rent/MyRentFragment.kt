package com.example.fide_rent.ui.rent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fide_rent.adapter.RentSelectorAdapter
import com.example.fide_rent.databinding.FragmentMyRentBinding
import com.example.fide_rent.viewmodel.MyRentViewModel

class MyRentFragment : Fragment() {

    private var _blinding: FragmentMyRentBinding? = null

    private val binding get() = _blinding!!
    private lateinit var myRentViewModel: MyRentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myRentViewModel = ViewModelProvider(this).get(MyRentViewModel::class.java)
        _blinding = FragmentMyRentBinding.inflate(inflater,container,false)

        val rentAdapter = RentSelectorAdapter()
        val recycler = binding.rentsOptions
        recycler.adapter = rentAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        myRentViewModel.getRents.observe(viewLifecycleOwner){
            rents -> rentAdapter.setRents(rents)
        }

        return binding.root
    }

}
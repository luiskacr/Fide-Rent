package com.example.fide_rent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fide_rent.R
import com.example.fide_rent.databinding.FragmentHomeBinding
import com.example.fide_rent.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //Botón WhatsApp
        binding.btnWhats.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_contactFragment)
        }

        //Show TextName
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        setup()

        return binding.root
    }


    private fun setup(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
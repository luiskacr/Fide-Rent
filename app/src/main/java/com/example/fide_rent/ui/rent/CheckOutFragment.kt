package com.example.fide_rent.ui.rent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fide_rent.R
import com.example.fide_rent.customs.CustomAlerts
import com.example.fide_rent.databinding.FragmentCheckOutBinding
import com.example.fide_rent.model.Rent
import com.example.fide_rent.viewmodel.CheckOutViewModel
import com.squareup.picasso.Picasso


class CheckOutFragment : Fragment() {

    private val args by navArgs<CheckOutFragmentArgs>()

    private var _blinding: FragmentCheckOutBinding? = null

    private val binding get() = _blinding!!
    private lateinit var checkOutViewModel: CheckOutViewModel

    val alerts: CustomAlerts = CustomAlerts()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        checkOutViewModel = ViewModelProvider(this).get(CheckOutViewModel::class.java)
        _blinding = FragmentCheckOutBinding.inflate(inflater,container,false)

        setup()


        return binding.root
    }

    fun setup(){

        fillCart()

        binding.bntBackDates.setOnClickListener {
            val accion = CheckOutFragmentDirections
                .actionCheckOutFragmentToSelectDatesFragment(args.rent.car)

            view?.findNavController()?.navigate(accion)
        }

        binding.btnFinal.setOnClickListener {

            val newRent = Rent("",args.rent.startDate,args.rent.endDate, args.rent.days,args.rent.total,args.rent.car)
            checkOutViewModel.saveRent(newRent)

            val accion = CheckOutFragmentDirections
                .actionCheckOutFragmentToNavHome()

            alerts.fragmentToast(getString(R.string.select_date_confirm),requireContext())

            view?.findNavController()?.navigate(accion)
        }

    }

    fun fillCart(){

        binding.carName.text = args.rent.car.name
        binding.carPrice.text = getString(R.string.card_info_price) + args.rent.car.price
        binding.carType.text = getString(R.string.card_info_type)+ args.rent.car.type
        binding.starDate.text = getString(R.string.card_info_start_date) + args.rent.startDate.replace('.','/')
        binding.endDate.text = getString(R.string.card_info_end_date) + args.rent.endDate.replace('.','/')
        binding.total.text =getString(R.string.card_info_total)  + args.rent.total

        if(args.rent.car.photo != null){
            Picasso.get().load(args.rent.car.photo).into(binding.imagen)
        }else{
            //Set a default Image if not exist on FireStore
        }

    }

}
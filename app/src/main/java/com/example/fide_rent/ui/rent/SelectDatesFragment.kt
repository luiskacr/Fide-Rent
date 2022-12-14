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
import com.example.fide_rent.databinding.FragmentSelectDatesBinding
import com.example.fide_rent.model.Car
import com.example.fide_rent.model.Rent
import com.example.fide_rent.viewmodel.SelectDateViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class SelectDatesFragment : Fragment() {

    //Get Data from Fragment Navigation
    private val args by navArgs<SelectDatesFragmentArgs>()

    private var _binding: FragmentSelectDatesBinding? = null

    private val binding get() = _binding!!
    private lateinit var selectDateViewModel: SelectDateViewModel

    companion object {
        var globalStartDate = ""
        var globalEndDate = ""
        var globalDays = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        selectDateViewModel = ViewModelProvider(this).get(SelectDateViewModel::class.java)
        _binding = FragmentSelectDatesBinding.inflate(inflater,container,false)


        setup()

        // Inflate the layout for this fragment
        return binding.root
    }

    fun setup(){

        fillCardSelection()

        binding.btnSelectDates.setOnClickListener {
            showDateRange()
        }

        binding.inputDates.setOnClickListener {
            showDateRange()
        }

        binding.bntBack.setOnClickListener {
            val accion = SelectDatesFragmentDirections
                .actionSelectDatesFragmentToSelectCar()

            view?.findNavController()?.navigate(accion)
        }

        binding.btnNext.setOnClickListener {

            if(binding.btnNext.isEnabled){
                val car = carCreate()

                val reservation = Rent("",globalStartDate,globalEndDate,globalDays,(globalDays * car.price.toInt()),car)

                val accion = SelectDatesFragmentDirections
                    .actionSelectDatesFragmentToCheckOutFragment(reservation)

                view?.findNavController()?.navigate(accion)
            }
        }

    }

    private fun fillCardSelection(){

        val car = carCreate()

        binding.carName.text = car.name
        binding.carPrice.text = "Precio Por Dia: $" + car.price
        binding.carType.text = "Transmisión: " + car.type

        if(car.photo != null){
            Picasso.get().load(car.photo).into(binding.imagen)
        }else{
            //Set a default Image if not exist on FireStore
        }

    }

    private fun showDateRange(){
        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .apply {
                    setTitleText(R.string.date_picker_Tittle)
                    setPositiveButtonText(R.string.date_picker_save)
                }.build()

        dateRangePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            val startDate = dateSelected.first
            val endDate = dateSelected.second

            val msdiff = endDate - startDate
            val daysDiff = TimeUnit.MILLISECONDS.toDays(msdiff)

            if (startDate != null && endDate != null) {
                val finalStarDate = convertLongToTime(startDate)
                val finalEndDate = convertLongToTime(endDate)

                binding.inputDates.text =  "Desde: "+ " " + finalStarDate.replace('.','/') + " Hasta " + finalEndDate.replace('.','/')


                globalStartDate = finalStarDate
                globalEndDate = finalEndDate

                globalDays = daysDiff.toInt()

                binding.btnNext.isEnabled = true
            }
        }

    }

    fun carCreate(): Car{

        val documentId = args.car.documentId
        val name = args.car.name
        val type = args.car.type
        val price = args.car.price
        val photo = args.car.photo

        return  Car(documentId,name,type,price,photo)
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault())
        return format.format(date)
    }

}
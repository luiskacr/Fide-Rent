package com.example.fide_rent.ui.rent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fide_rent.R
import com.example.fide_rent.customs.CustomAlerts
import com.example.fide_rent.databinding.FragmentUpdateRentBinding
import com.example.fide_rent.model.Rent
import com.example.fide_rent.viewmodel.UpdateRentViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class UpdateRentFragment : Fragment() {


    private val args by navArgs<UpdateRentFragmentArgs>()

    private var _binding: FragmentUpdateRentBinding? = null
    private val binding get() = _binding!!
    private lateinit var updateRenteViewModel: UpdateRentViewModel

    val alerts: CustomAlerts = CustomAlerts()

    companion object {
        var globalrent = Rent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        updateRenteViewModel = ViewModelProvider(this).get(UpdateRentViewModel::class.java)
        _binding = FragmentUpdateRentBinding.inflate(inflater,container,false)

        setup()

        return binding.root
    }

    fun setup(){

        globalrent = args.rent

        fillData()

        binding.btnUpdateDate.setOnClickListener {
            showDateRange()
        }

        binding.btnUpdate.setOnClickListener {
            updateRenteViewModel.saveRent(globalrent)

            val accion = UpdateRentFragmentDirections.actionUpdateRentFragmentToNavHome()
            alerts.fragmentToast(getString(R.string.update_update_msj),requireContext())
            view?.findNavController()?.navigate(accion)
        }

        binding.btnDelete.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle(getString(R.string.update_modal_tittle))
            builder.setMessage( getString(R.string.update_modal_msg) )
            builder.setPositiveButton( getString(R.string.update_modal_accept) ){DialogInterface, with->
                val rent = args.rent
                updateRenteViewModel.deleteRent(rent)
                val accion = UpdateRentFragmentDirections.actionUpdateRentFragmentToNavHome()
                alerts.fragmentToast(getString(R.string.update_delete_msj),requireContext())
                view?.findNavController()?.navigate(accion)
            }
            builder.setNegativeButton( getString(R.string.update_modal_cancel) ){DialogInterface, with->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    fun fillData(){
        binding.carName.text = args.rent.car.name
        binding.carPrice.text = "Precio Por Dia: $" + args.rent.car.price
        binding.carType.text = "Transmisión: " + args.rent.car.type

        if(args.rent.car.photo != null){
            Picasso.get().load(args.rent.car.photo).into(binding.imagen)
        }else{
            //Set a default Image if not exist on FireStore
        }

        binding.inputDates.text ="Desde: " +  args.rent.startDate.replace('.','/') + " Hasta " + args.rent.endDate.replace('.','/')

        binding.total.text = getString(R.string.card_info_total) + args.rent.total.toString()
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

                binding.inputDates.text =  "Desde: "  + finalStarDate.replace('.','/') + " Hasta " + finalEndDate.replace('.','/')

                globalrent.startDate = finalStarDate
                globalrent.endDate = finalEndDate
                globalrent.days = daysDiff.toInt()

                globalrent.total = globalrent.days * globalrent.car.price.toInt()

                binding.total.text = getString(R.string.card_info_total) + globalrent.total.toString()

            }
        }

    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault())
        return format.format(date)
    }

}
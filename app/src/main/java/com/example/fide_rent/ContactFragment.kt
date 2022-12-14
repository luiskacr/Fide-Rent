package com.example.fide_rent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.fide_rent.customs.CustomAlerts
import com.example.fide_rent.databinding.FragmentContactBinding
import com.example.fide_rent.viewmodel.HomeViewModel
import java.net.URLEncoder

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val alerts: CustomAlerts = CustomAlerts()
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentContactBinding.inflate(inflater, container, false)


        //Botón WhatsApp
        binding.btnSendWhats.setOnClickListener {
            sendWhatsApp()
        }

        //Botón Correo
        binding.btnSendMail.setOnClickListener {
            sendMail()
        }

        return binding.root
    }

    private fun sendWhatsApp(){
        val i = Intent(Intent.ACTION_VIEW)
        val url = "https://api.whatsapp.com/send?phone=50686385151&text="
        i.setPackage("com.whatsapp")
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun sendMail(){

        val mail = "fabry110698@gmail.com"
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL,arrayOf(mail))
        startActivity (intent)
    }
}
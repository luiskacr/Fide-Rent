package com.example.fide_rent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fide_rent.R
import com.example.fide_rent.databinding.FragmentRentSelectorBinding
import com.example.fide_rent.model.Rent
import com.example.fide_rent.ui.rent.MyRentFragmentDirections
import com.squareup.picasso.Picasso

class RentSelectorAdapter:RecyclerView.Adapter<RentSelectorAdapter.RentViewHolder>() {

    private var listRents = emptyList<Rent>()

    inner class RentViewHolder(private val itemBinding: FragmentRentSelectorBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun draw(rent: Rent){
            itemBinding.carName.text = rent.car.name
            itemBinding.carPrice.text =  "Precio Por Dia: $" + rent.car.price.toString()
            itemBinding.carType.text = "Transmisión: " + rent.car.type
            itemBinding.starDate.text = "Fecha de Inicio: " + rent.startDate.replace(".","/")
            itemBinding.endDate.text = "Fecha Fin: " + rent.endDate.replace(".","/")
            itemBinding.total.text = "Precio Total: $" + rent.total.toString()
            if(rent.car.photo != null){
                Picasso.get().load(rent.car.photo).into(itemBinding.imagen)
            }else{
                //Set a default Image if not exist on FireStore
            }

            itemBinding.rentSelector.setOnClickListener{
                val accion = MyRentFragmentDirections
                    .actionMyRentToUpdateRentFragment(rent)

                itemView.findNavController().navigate(accion)
            }
        }
    }

    fun setRents(rents: List<Rent>){
        listRents = rents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentViewHolder {
        val itemBinding = FragmentRentSelectorBinding
            .inflate(LayoutInflater.from(parent.context),
                parent,false)

        return RentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RentViewHolder, position: Int) {
        val rent = listRents[position]
        holder.draw(rent)
    }

    override fun getItemCount(): Int {
        return listRents.size
    }
}
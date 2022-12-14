package com.example.fide_rent.adapter

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fide_rent.databinding.FragmentCarSelectorBinding
import com.example.fide_rent.model.Car
import com.squareup.picasso.Picasso
import com.example.fide_rent.ui.rent.select_carDirections

class CarSelectorAdapter: RecyclerView.Adapter<CarSelectorAdapter.CarViewHolder>()  {

    private var carList = emptyList<Car>()


    inner class CarViewHolder(private val itemBinding: FragmentCarSelectorBinding ): RecyclerView.ViewHolder(itemBinding.root){
        fun draw(car: Car){
            itemBinding.carName.text = car.name
            itemBinding.carPrice.text = "Precio Por Dia: $" + car.price
            itemBinding.carType.text = "Transmisión: " + car.type

            if(car.photo != null){
                Picasso.get().load(car.photo).into(itemBinding.imagen)
            }else{
                //Set a default Image if not exist on FireStore
            }
            itemBinding.carSelector.setOnClickListener{
               val accion = select_carDirections.
               actionSelectCarToSelectDatesFragment(car)

                itemView.findNavController().navigate(accion)
            }

        }
    }

    fun setCars(cars: List<Car>){
        carList = cars
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemBinding = FragmentCarSelectorBinding.
            inflate(LayoutInflater.from(parent.context),
            parent,false)
        return CarViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.draw(car)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

}
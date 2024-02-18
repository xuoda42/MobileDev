package com.example.list.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.list.data.Couriers
import com.example.list.repository.AppRepository

class CouriersViewModel : ViewModel() {

    var couriersList: MutableLiveData<List<Couriers>> = MutableLiveData()
    private var _couriers : Couriers? = null

    val group
        get() = _couriers

    init {
        AppRepository.getInstance().listOfCouriers.observeForever {
            couriersList.postValue(AppRepository.getInstance().companyCouriers)
        }

        AppRepository.getInstance().couriers.observeForever {
            _couriers = it
        }

        AppRepository.getInstance().company.observeForever {
            couriersList.postValue(AppRepository.getInstance().companyCouriers)
        }
    }

    fun deleteGroup(){
        if (group != null)
            AppRepository.getInstance().deleteCourier(group!!)
    }

    fun appendGroup(groupName: String){
        val couriers = Couriers()
        couriers.name = groupName
        couriers.companyID = company?.id!!
        AppRepository.getInstance().addCourier(couriers)
    }

    fun updateGroup(groupName: String){
        if (_couriers != null){
            _couriers!!.name = groupName
            AppRepository.getInstance().updateCourier(_couriers!!)
        }
    }

    fun setCurrentGroup(position: Int){
        if((couriersList.value?.size?: 0) > position)
            couriersList.value?.let { AppRepository.getInstance().setCurrentGroup(it.get(position))}
    }

    fun setCurrentGroup(couriers: Couriers){
        AppRepository.getInstance().setCurrentGroup(couriers)
    }

    val getCurrentListPosition
        get() = couriersList.value?.indexOfFirst{ it.id == group?.id} ?: -1




    val company
        get() = AppRepository.getInstance().company.value
}
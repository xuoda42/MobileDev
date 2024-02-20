package com.example.list.fragments


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.list.data.TourGuides
import com.example.list.data.Orders
import com.example.list.repository.AppRepository
import kotlinx.coroutines.launch
import java.util.Date


class OrdersViewModel : ViewModel() {
    var ordersList: MutableLiveData<List<Orders>> = MutableLiveData()

    var _orders: Orders?= null
    var isTvTNTapped: Boolean = false

    val order
        get()=_orders

    lateinit var tourGuides: TourGuides



    fun set_TourGuide_ByAdress(tourGuides: TourGuides){
        this.tourGuides = tourGuides
        AppRepository.getInstance().listOfOrders.observeForever {
            ordersList.postValue(AppRepository.getInstance().getTourGuideOrdersByAdress(tourGuides.id))
        }
        AppRepository.getInstance().orders.observeForever {
            _orders = it
        }
    }

    ////////////////////////////////////////////////////////
    fun set_TourGuide_ByDate(tourGuides: TourGuides){
        this.tourGuides = tourGuides
        AppRepository.getInstance().listOfOrders.observeForever {
            ordersList.postValue(AppRepository.getInstance().getTourGuideOrdersByDate(tourGuides.id).reversed())
        }
        AppRepository.getInstance().orders.observeForever {
            _orders = it
        }
    }
    fun set_TourGuide_ByTime(tourGuides: TourGuides){
        this.tourGuides = tourGuides
        AppRepository.getInstance().listOfOrders.observeForever {
            ordersList.postValue(AppRepository.getInstance().getTourGuideOrdersByTime(tourGuides.id))
        }
        AppRepository.getInstance().orders.observeForever {
            _orders = it
        }
    }
    fun set_TourGuide_ByTimeTravel(tourGuides: TourGuides){
        this.tourGuides = tourGuides
        AppRepository.getInstance().listOfOrders.observeForever {
            ordersList.postValue(AppRepository.getInstance().getTourGuideOrdersByTimeTravel(tourGuides.id).reversed())
        }
        AppRepository.getInstance().orders.observeForever {
            _orders = it
        }
    }


    /////////////////////////////////////////////////////////////    /////////////////////////////////////////////////////////////

    fun filterItems(query: String) {
        viewModelScope.launch {
            val filteredList = mutableListOf<Orders>()
            ordersList.value?.map {
                if (it.address.contains(query, true)) filteredList.add(it)
            }
            ordersList.postValue(filteredList)
        }
    }

    /////////////////////////////////////////////////////////////    /////////////////////////////////////////////////////////////

    fun deleteOrder(){

        if (order!=null)
            AppRepository.getInstance().deleteOrder(order!!)
    }


    private val selectedName = MutableLiveData<String>()


    fun setSelectedName(name: String) {
        selectedName.value = name
    }


    fun setCurrentOrder(orders: Orders){
        AppRepository.getInstance().setCurrentStudent(orders)
    }


}



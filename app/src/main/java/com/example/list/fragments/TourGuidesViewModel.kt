package com.example.list.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.list.data.TourGuides
import com.example.list.repository.AppRepository

class TourGuidesViewModel : ViewModel() {

    var tourGuidesList: MutableLiveData<List<TourGuides>> = MutableLiveData()
    private var _tourGuides : TourGuides? = null

    val group
        get() = _tourGuides

    init {
        AppRepository.getInstance().listOfTourGuides.observeForever {
            tourGuidesList.postValue(AppRepository.getInstance().companyTourGuides)
        }

        AppRepository.getInstance().tourGuides.observeForever {
            _tourGuides = it
        }

        AppRepository.getInstance().company.observeForever {
            tourGuidesList.postValue(AppRepository.getInstance().companyTourGuides)
        }
    }

    fun deleteGroup(){
        if (group != null)
            AppRepository.getInstance().deleteTourGuide(group!!)
    }

    fun appendGroup(groupName: String){
        val tourGuides = TourGuides()
        tourGuides.name = groupName
        tourGuides.companyID = company?.id!!
        AppRepository.getInstance().addTourGuide(tourGuides)
    }

    fun updateGroup(groupName: String){
        if (_tourGuides != null){
            _tourGuides!!.name = groupName
            AppRepository.getInstance().updateTourGuide(_tourGuides!!)
        }
    }

    fun setCurrentGroup(position: Int){
        if((tourGuidesList.value?.size?: 0) > position)
            tourGuidesList.value?.let { AppRepository.getInstance().setCurrentGroup(it.get(position))}
    }

    fun setCurrentGroup(tourGuides: TourGuides){
        AppRepository.getInstance().setCurrentGroup(tourGuides)
    }

    val getCurrentListPosition
        get() = tourGuidesList.value?.indexOfFirst{ it.id == group?.id} ?: -1




    val company
        get() = AppRepository.getInstance().company.value
}
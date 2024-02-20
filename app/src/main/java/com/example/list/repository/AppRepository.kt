package com.example.list.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.preference.PreferenceManager

import com.example.list.ApplicationList2
import com.example.list.api.APPEND_COMPANY
import com.example.list.api.APPEND_MAKERS
import com.example.list.api.APPEND_DOCUMENTS
import com.example.list.api.DELETE_COMPANY
import com.example.list.api.DELETE_MAKERS
import com.example.list.api.DELETE_DOCUMENTS
import com.example.list.api.ListAPI
import com.example.list.api.ListConnection
import com.example.list.api.PostCompany
import com.example.list.api.PostResult
import com.example.list.api.PostTourGuides
import com.example.list.api.PostOrders
import com.example.list.api.UPDATE_COMPANY
import com.example.list.api.UPDATE_MAKERS
import com.example.list.api.UPDATE_DOCUMENTS

import com.example.list.data.Company
import com.example.list.data.Companies
import com.example.list.data.TourGuides
import com.example.list.data.TourGuidesed
import com.example.list.data.Orders
import com.example.list.data.Ordered
import com.example.list.data.User
import com.example.list.data.UserLogin
import com.example.list.database.ListDatabase
import com.example.list.myConsts.TAG
import com.example.list1110.R
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppRepository {
    companion object {

        private var INSTANCE: AppRepository? = null

        fun getInstance(): AppRepository {
            if (INSTANCE == null) {
                INSTANCE = AppRepository()
            }
            return INSTANCE?:
            throw IllegalStateException("Repository is not inisializated")
        }

    }

    var company: MutableLiveData<Company> = MutableLiveData()
    var tourGuides: MutableLiveData<TourGuides> = MutableLiveData()
    var orders: MutableLiveData<Orders> = MutableLiveData()

    fun setCurrentCompany(_company: Company){
        company.postValue(_company)
    }

    fun loadData(){
        fetchCompanys()
    }

    fun setCurrentGroup(_tourGuides:TourGuides){
        tourGuides.postValue(_tourGuides)
    }

    val companyTourGuides
        get() = listOfTourGuides.value?.filter{it.companyID == (company.value?.id?:0)}?.sortedBy { it.name } ?:  listOf()

    fun setCurrentStudent(_orders:Orders){
        orders.postValue( _orders)
    }


    //////////////////////////////////////////////////////////////////
    fun getTourGuideOrdersByAdress(tourGuideID: Int) =
        (listOfOrders.value?.filter {it.tourGuideID == tourGuideID }?.sortedBy { it.address } ?:listOf())

    fun getTourGuideOrdersByDate(tourGuideID: Int) =
        listOfOrders.value?.filter {it.tourGuideID == tourGuideID }?.sortedBy { it.date } ?:listOf()

    fun getTourGuideOrdersByTime(tourGuideID: Int) =
        listOfOrders.value?.filter {it.tourGuideID == tourGuideID }?.sortedBy { it.time } ?:listOf()

    fun getTourGuideOrdersByTimeTravel(tourGuideID: Int) =
        listOfOrders.value?.filter {it.tourGuideID == tourGuideID }?.sortedBy { it.timeTravel } ?:listOf()
    /////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////
    private val listDB by lazy { OfflineDBRepository(ListDatabase.getDatabase(ApplicationList2.context).listDAO()) }

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)


    val listOfOrders: LiveData<List<Orders>> = listDB.getAllOrders().asLiveData()
    val listOfCompany: LiveData<List<Company>> = listDB.getCompany().asLiveData()
    val listOfTourGuides: LiveData<List<TourGuides>> = listDB.getAllTourGuides().asLiveData()

    private var listAPI = ListConnection.getClient().create(ListAPI::class.java)

    fun fetchCompanys(){
        listAPI.getCompanys().enqueue(object: Callback<Companies> {
            override fun onFailure(call: Call<Companies>, t :Throwable){
                Log.d(TAG,"Ошибка получения списка туристических агенств", t)
            }
            override fun onResponse(
                call : Call<Companies>,
                response: Response<Companies>
            ){
                if (response.code()==200){
                    val companys= response.body()
                    val items =companys?.items?:emptyList()
                    Log.d(TAG,"Получен список туристических агенств $items")
                    myCoroutineScope.launch{
                        listDB.deleteAllCompany()
                        for (f in items){
                            listDB.insertCompany(f)
                        }
                    }
                    fetchTourGuides()
                }
            }
        })
    }


    private fun updateCompanies(postCompany: PostCompany){
        listAPI.postCompany(postCompany)
            .enqueue(object : Callback<PostResult> {
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>){
                    if (response.code()==200) fetchCompanys()
                }
                override fun onFailure(call: Call<PostResult>, t: Throwable){
                    Log.d(TAG,"Ошибка записи туристического агенства",t)
                }
            })
    }

    private fun updateTourGuides(postTourGuides: PostTourGuides){
        listAPI.postTourGuide(postTourGuides)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response: Response<PostResult>){
                    if (response.code()==200) fetchCompanys()
                }
                override fun onFailure(call:Call<PostResult>,t: Throwable){
                    Log.d(TAG,"Ошибка записи туристического агенства",t)
                }
            })
    }


    fun addCompany(company: Company){
        updateCompanies(PostCompany(APPEND_COMPANY, company))
    }
    fun updateCompany(company: Company){
        updateCompanies(PostCompany(UPDATE_COMPANY, company))
    }

    fun deleteCompany(company: Company){
        updateCompanies(PostCompany(DELETE_COMPANY, company))
    }

    fun fetchTourGuides(){
        listAPI.getTourGuides().enqueue(object: Callback<TourGuidesed> {
            override fun onFailure(call: Call<TourGuidesed>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка гидов", t)
            }

            override fun onResponse(
                call: Call<TourGuidesed>,
                response: Response<TourGuidesed>
            ) {
                if (response.code() == 200) {
                    val tourGuides = response.body()
                    val items = tourGuides?.items ?: emptyList()
                    Log.d(TAG, "Получен список гидов $items")
                    myCoroutineScope.launch {
                        listDB.deleteAllTourGuides()
                        for (g in items) {
                            listDB.insertTourGuides(g)
                        }
                    }
                    fetchOrders()
                }
            }
        })
    }

    fun addTourGuide(tourGuides: TourGuides){
        updateTourGuides(PostTourGuides(APPEND_MAKERS, tourGuides))
    }

    fun updateTourGuide(tourGuides: TourGuides){
        updateTourGuides(PostTourGuides(UPDATE_MAKERS, tourGuides))
    }

    fun deleteTourGuide(tourGuides: TourGuides){
        updateTourGuides(PostTourGuides(DELETE_MAKERS, tourGuides))
    }


    fun fetchOrders(){
        listAPI.getOrders().enqueue(object : Callback<Ordered> {
            override fun onFailure(call: Call<Ordered>, t : Throwable){
                Log.d(TAG,"Ошибка получения списка заказов",t)
            }
            override fun onResponse(
                call: Call<Ordered>,
                response: Response<Ordered>
            ){
                if(response.code()==200){
                    val Orders = response.body()
                    val items = Orders?.items?: emptyList()
                    Log.d(TAG,"Получен список заказов $items")
                    myCoroutineScope.launch {
                        listDB.deleteAllOrders()
                        for (s in items){
                            listDB.insertOrders(s)
                        }
                    }
                }
            }
        })
    }

    private fun updateOrders(postOrders: PostOrders){
        listAPI.postOrders(postOrders)
            .enqueue(object : Callback<PostResult> {
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>){
                    if (response.code()==200) fetchOrders()
                }
                override fun onFailure(call: Call<PostResult>, t:Throwable){
                    Log.d(TAG,"Ошибка записи тура", t)
                }
            })
    }

    fun addOrder(orders: Orders){
        updateOrders(PostOrders(APPEND_DOCUMENTS, orders))
    }

    fun deleteOrder(orders: Orders){
        updateOrders(PostOrders(DELETE_DOCUMENTS,orders))
    }

    fun updateOrder(orders: Orders){
        updateOrders(PostOrders(UPDATE_DOCUMENTS,orders))
    }
    /////////////////////////////////////////////////////////////////////
    private fun registrationQuery(user: User) {
        listAPI.registration(user)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response:Response<PostResult>){
                    if (response.code()==200)
                        ApplicationList2.setIsAdmin(true)
                }
                override fun onFailure(call:Call<PostResult>,t:Throwable){
                    Log.d(TAG,"Ошибка регистрации", t.fillInStackTrace())
                    ApplicationList2.makeToast("Ошибка авторизации")
                    ApplicationList2.setIsAdmin(false)
                }
            })

    }
    private fun loginQuery(user: UserLogin) {
        listAPI.login(user)
            .enqueue(object : Callback<PostResult>{
                override fun onResponse(call:Call<PostResult>,response:Response<PostResult>){
                    if (response.code()==200) {
                        ApplicationList2.setIsAdmin(true)
                        val a = ApplicationList2.isAdmin
                        Log.d(TAG, "Успех входа ${a}")
                    }
                    if (response.code()==401) {
                        ApplicationList2.makeToast("Неверный логин/пароль")
                    }
                }
                override fun onFailure(call:Call<PostResult>,t:Throwable){
                    Log.d(TAG,"Ошибка входа", t.fillInStackTrace())
                    ApplicationList2.setIsAdmin(false)
                }
            })
    }

    fun registration(user: User) {
        registrationQuery(user)
    }

    fun login(usered: User, user: UserLogin)
    {
        registrationQuery(usered)
        loginQuery(user)
    }

    fun logout() {
        ApplicationList2.setIsAdmin(false)
    }

}
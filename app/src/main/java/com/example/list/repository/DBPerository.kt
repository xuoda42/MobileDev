package com.example.list.repository

import com.example.list.data.Company
import com.example.list.data.Couriers
import com.example.list.data.Orders
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DBRepository {
    fun getCompany(): Flow<List<Company>>
    suspend fun insertCompany(company: Company)
    suspend fun updateCompany(company: Company)
    suspend fun insertAllCompany(companyList: List<Company>)
    suspend fun deleteCompany(company: Company)
    suspend fun deleteAllCompany()

    fun getAllCouriers(): Flow<List<Couriers>>
    fun getCompanyCouriers(companyID : UUID): Flow<List<Couriers>>
    suspend fun insertCouriers(couriers: Couriers)
    suspend fun deleteCouriers(couriers: Couriers)
    suspend fun deleteAllCouriers()

    fun getAllOrders(): Flow<List<Orders>>
    fun getCouriersOrders(makersID : UUID): Flow<List<Orders>>
    suspend fun insertOrders(orders: Orders)
    suspend fun deleteOrders(orders: Orders)
    suspend fun deleteAllOrders()
}
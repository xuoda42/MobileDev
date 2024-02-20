package com.example.list.repository

import com.example.list.data.Company
import com.example.list.data.TourGuides
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

    fun getAllTourGuides(): Flow<List<TourGuides>>
    fun getCompanyTourGuides(companyID : UUID): Flow<List<TourGuides>>
    suspend fun insertTourGuides(tourGuides: TourGuides)
    suspend fun deleteTourGuides(tourGuides: TourGuides)
    suspend fun deleteAllTourGuides()

    fun getAllOrders(): Flow<List<Orders>>
    fun getTourGuidesOrders(makersID : UUID): Flow<List<Orders>>
    suspend fun insertOrders(orders: Orders)
    suspend fun deleteOrders(orders: Orders)
    suspend fun deleteAllOrders()
}
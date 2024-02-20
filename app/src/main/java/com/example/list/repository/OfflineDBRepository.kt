package com.example.list.repository

import com.example.list.data.Company
import com.example.list.data.TourGuides
import com.example.list.data.Orders
import com.example.list.database.ListDAO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class OfflineDBRepository(val dao: ListDAO) : DBRepository {

    override fun getCompany(): Flow<List<Company>> =dao.getCompany()
    override suspend fun insertCompany(company: Company) = dao.insertCompany(company)
    override suspend fun updateCompany(company: Company) =dao.updateCompany(company)
    override suspend fun insertAllCompany(companyList: List<Company>) =dao.insertAllCompany(companyList)
    override suspend fun deleteCompany(company: Company) =dao.deleteCompany(company)
    override suspend fun deleteAllCompany() =dao.deleteAllCompany()

    override fun getAllTourGuides(): Flow<List<TourGuides>> =dao.getAllTourGuides()
    override fun getCompanyTourGuides(companyID : UUID): Flow<List<TourGuides>> =dao.getCompanyTourGuides(companyID)
    override suspend fun insertTourGuides(tourGuides: TourGuides) =dao.insertTourGuide(tourGuides)
    override suspend fun deleteTourGuides(tourGuides: TourGuides) =dao.deleteTourGuide(tourGuides)
    override suspend fun deleteAllTourGuides() =dao.deleteAllTourGuides()

    override fun getAllOrders(): Flow<List<Orders>> =dao.getAllOrders()
    override fun getTourGuidesOrders(makersID : UUID): Flow<List<Orders>> =dao.getTourGuideOrders(makersID)
    override suspend fun insertOrders(orders: Orders) =dao.insertOrder(orders)
    override suspend fun deleteOrders(orders: Orders) =dao.deleteOrder(orders)
    override suspend fun deleteAllOrders() =dao.deleteAllOrders()
}
package com.example.list.repository

import com.example.list.data.Company
import com.example.list.data.Couriers
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

    override fun getAllCouriers(): Flow<List<Couriers>> =dao.getAllCouriers()
    override fun getCompanyCouriers(companyID : UUID): Flow<List<Couriers>> =dao.getCompanyCouriers(companyID)
    override suspend fun insertCouriers(couriers: Couriers) =dao.insertCourier(couriers)
    override suspend fun deleteCouriers(couriers: Couriers) =dao.deleteCourier(couriers)
    override suspend fun deleteAllCouriers() =dao.deleteAllCouriers()

    override fun getAllOrders(): Flow<List<Orders>> =dao.getAllOrders()
    override fun getCouriersOrders(makersID : UUID): Flow<List<Orders>> =dao.getCourierOrders(makersID)
    override suspend fun insertOrders(orders: Orders) =dao.insertOrder(orders)
    override suspend fun deleteOrders(orders: Orders) =dao.deleteOrder(orders)
    override suspend fun deleteAllOrders() =dao.deleteAllOrders()
}
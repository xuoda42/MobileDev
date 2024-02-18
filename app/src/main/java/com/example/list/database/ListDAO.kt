package com.example.list.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.list.data.Company
import com.example.list.data.Couriers
import com.example.list.data.Orders
import com.example.list.data.User
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ListDAO {


    @Query("SELECT * FROM Company order by company_name")
    fun getCompany(): Flow<List<Company>>

    @Insert(entity = Company::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: Company)

    @Update(entity = Company::class)
    suspend fun updateCompany(company: Company)

    @Insert(entity = Company::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCompany(companyList: List<Company>)

    @Delete(entity = Company::class)
    suspend fun deleteCompany(company: Company)

    @Query("DELETE FROM Company")
    suspend fun deleteAllCompany()

    @Query("SELECT * FROM Couriers")
    fun getAllCouriers(): Flow<List<Couriers>>

    @Query("SELECT * FROM Couriers where company_id=:companyID")
    fun getCompanyCouriers(companyID : UUID): Flow<List<Couriers>>

    @Insert(entity = Couriers::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourier(couriers: Couriers)

    @Delete(entity = Couriers::class)
    suspend fun deleteCourier(couriers: Couriers)

    @Query("DELETE FROM Couriers")
    suspend fun deleteAllCouriers()

    @Query("SELECT * FROM Orders")
    fun getAllOrders(): Flow<List<Orders>>

    @Query("SELECT * FROM Orders where courier_id=:courierID")
    fun getCourierOrders(courierID : UUID): Flow<List<Orders>>

    @Insert(entity = Orders::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orders: Orders)

    @Delete(entity = Orders::class)
    suspend fun deleteOrder(orders: Orders)

    @Query("DELETE FROM Orders")
    suspend fun deleteAllOrders()

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id=:userId")
    fun getUserById(userId: UUID): Flow<User>

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)


}
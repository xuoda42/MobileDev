package com.example.list.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.list.data.Company
import com.example.list.data.TourGuides
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

    @Query("SELECT * FROM TourGuides")
    fun getAllTourGuides(): Flow<List<TourGuides>>

    @Query("SELECT * FROM TourGuides where company_id=:companyID")
    fun getCompanyTourGuides(companyID : UUID): Flow<List<TourGuides>>

    @Insert(entity = TourGuides::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTourGuide(tourGuides: TourGuides)

    @Delete(entity = TourGuides::class)
    suspend fun deleteTourGuide(tourGuides: TourGuides)

    @Query("DELETE FROM TourGuides")
    suspend fun deleteAllTourGuides()

    @Query("SELECT * FROM Orders")
    fun getAllOrders(): Flow<List<Orders>>

    @Query("SELECT * FROM Orders where tour_guide_id=:tourGuideID")
    fun getTourGuideOrders(tourGuideID : UUID): Flow<List<Orders>>

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
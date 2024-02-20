package com.example.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import com.example.list.data.Orders
import com.example.list.fragments.CompanyFragment
import com.example.list.fragments.TourGuidesFragment
import com.example.list.fragments.OrdersFragment
import com.example.list.fragments.OrdersViewModel
import com.example.list.interfaces.MainActivityInterface
import com.example.list.repository.AppRepository
import com.example.list1110.R
import com.example.lsit.fragments.LoginFragment

class MainActivity : AppCompatActivity(), MainActivityInterface {
    interface Edit {
        fun append()
        fun update()
        fun delete()
    }
    private lateinit var viewModel: OrdersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this ){
            if (supportFragmentManager.backStackEntryCount > 0){
                supportFragmentManager.popBackStack()
                when (activeFragment){
                    NamesOfFragment.COMPANY ->{
                        finish()
                    }
                    NamesOfFragment.TOURT_GUIDES ->{
                        activeFragment=NamesOfFragment.COMPANY
                    }
                    NamesOfFragment.ORDERS ->{
                        activeFragment = NamesOfFragment.TOURT_GUIDES
                    }
                    NamesOfFragment.LOGIN->{
                        activeFragment=NamesOfFragment.COMPANY
                    }
                    else -> {}
                }
                updateMenu(activeFragment)
            }
            else{
                finish()
            }
        }
        showFragment(activeFragment, null)
    }

    private var _miAppendCompany:  MenuItem? =null
    private var _miUpdateCompany:  MenuItem? =null
    private var _miDeleteCompany:  MenuItem? =null
    private var _miAppendTourGuide:  MenuItem? =null
    private var _miUpdateTourGuide:  MenuItem? =null
    private var _miDeleteTourGuide:  MenuItem? =null
    private var _miSignup: MenuItem ?= null
    private var _miLogin: MenuItem ?= null
    private var _miExit: MenuItem ?= null


    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
     menuInflater.inflate(R.menu.main_menu, menu)
        _miAppendCompany = menu?.findItem(R.id.miNewCompany)
        _miUpdateCompany = menu?.findItem(R.id.miUpdateCompany)
        _miDeleteCompany = menu?.findItem(R.id.miDeleteCompany)
        _miAppendTourGuide = menu?.findItem(R.id.miAppendTourGuide)
        _miUpdateTourGuide = menu?.findItem(R.id.miUpdateTourGuide)
        _miDeleteTourGuide = menu?.findItem(R.id.miDeleteTourGuide)
        _miLogin = menu?.findItem(R.id.miLogin)
        _miExit = menu?.findItem(R.id.miExit)

        updateMenu(activeFragment)
        return true
    }

    var activeFragment : NamesOfFragment = NamesOfFragment.COMPANY
    private fun updateMenu(fragmentType: NamesOfFragment){
        _miAppendCompany?.isVisible = (
                fragmentType == NamesOfFragment.COMPANY && ApplicationList2.isAdmin)
        _miUpdateCompany?.isVisible = (
                fragmentType == NamesOfFragment.COMPANY && ApplicationList2.isAdmin)
        _miDeleteCompany?.isVisible = (
                fragmentType == NamesOfFragment.COMPANY && ApplicationList2.isAdmin)
        _miAppendTourGuide?.isVisible = (
                fragmentType == NamesOfFragment.TOURT_GUIDES && ApplicationList2.isAdmin)
        _miUpdateTourGuide?.isVisible = (
                fragmentType == NamesOfFragment.TOURT_GUIDES && ApplicationList2.isAdmin)
        _miDeleteTourGuide?.isVisible = (
                fragmentType == NamesOfFragment.TOURT_GUIDES && ApplicationList2.isAdmin)

        _miLogin?.isVisible = (
                fragmentType == NamesOfFragment.COMPANY && !ApplicationList2.isAdmin)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{

        return when (item.itemId){
            R.id.miNewCompany->{
                val fedit:Edit = CompanyFragment.getInstance()
                fedit.append()
                true
            }

            R.id.miUpdateCompany  ->{
                val fedit:Edit = CompanyFragment.getInstance()
                fedit.update()
                true
            }

            R.id.miDeleteCompany->{
                val fedit:Edit = CompanyFragment.getInstance()
                fedit.delete()
                true
            }
            R.id.miAppendTourGuide->{
                val fedit:Edit = TourGuidesFragment.getInstance()
                fedit.append()
                true
            }

            R.id.miUpdateTourGuide  ->{
                val fedit:Edit = TourGuidesFragment.getInstance()
                fedit.update()
                true
            }

            R.id.miDeleteTourGuide  ->{
                val fedit:Edit = TourGuidesFragment.getInstance()
                fedit.delete()
                true
            }

            R.id.miLogin -> {
                showFragment(NamesOfFragment.LOGIN)
                true
            }
            R.id.miExit -> {
                AppRepository.getInstance().logout()
                true
            }
            else-> super.onOptionsItemSelected(item)

        }
    }


    override fun showFragment(fragmentType: NamesOfFragment, orders: Orders?) {
        when (fragmentType){
            NamesOfFragment.COMPANY -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcMain, CompanyFragment.getInstance())
                    .addToBackStack(null)
                    .commit()
            }
            NamesOfFragment.TOURT_GUIDES -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcMain, TourGuidesFragment.getInstance())
                    .addToBackStack(null)
                    .commit()
            }
            NamesOfFragment.ORDERS -> {
                if (orders != null)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fcMain, OrdersFragment.newInstance(orders))
                        .addToBackStack(null)
                        .commit()

            }
            NamesOfFragment.LOGIN->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcMain, LoginFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        }
        activeFragment = fragmentType
        updateMenu(fragmentType)
    }





    override fun updateTitle(newTitle: String) {
        title= newTitle
    }

    override fun onStop(){
//        AppRepository.getInstance().saveData()
        super.onStop()
    }

}
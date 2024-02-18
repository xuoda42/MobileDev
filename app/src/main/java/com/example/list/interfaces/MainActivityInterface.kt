package com.example.list.interfaces

import com.example.list.NamesOfFragment
import com.example.list.data.Orders

interface MainActivityInterface {
    fun updateTitle(newTitle: String)
    fun showFragment(fragmentType: NamesOfFragment, orders: Orders? = null)
}
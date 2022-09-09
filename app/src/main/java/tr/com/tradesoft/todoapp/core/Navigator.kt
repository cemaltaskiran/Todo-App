package tr.com.tradesoft.todoapp.core

import androidx.fragment.app.Fragment

interface Navigator {
    fun navigate(fragment: Fragment, addToBackStack: Boolean = false)
    fun popBack()
}
package tr.com.tradesoft.todoapp.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tr.com.tradesoft.todoapp.MainActivity
import tr.com.tradesoft.todoapp.R

abstract class NavigatorFragment: Fragment() {

    protected var navigator: Navigator? = null

    abstract val title: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is Navigator) {
            navigator = activity as Navigator
        }
        (activity as? MainActivity)?.let {
            it.supportActionBar?.title = title
        }
    }

}
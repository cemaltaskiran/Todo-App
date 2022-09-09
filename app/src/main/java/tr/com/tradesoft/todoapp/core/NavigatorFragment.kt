package tr.com.tradesoft.todoapp.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class NavigatorFragment: Fragment() {

    protected var navigator: Navigator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is Navigator) {
            navigator = activity as Navigator
        }
    }

}
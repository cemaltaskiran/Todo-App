package tr.com.tradesoft.todoapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import tr.com.tradesoft.todoapp.core.Navigator
import tr.com.tradesoft.todoapp.ui.login.LoginFragment

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission(Manifest.permission.USE_EXACT_ALARM, 11)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }

    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            requestPermissions(arrayOf(permission), requestCode)
            println("REQUEST_PERMISSION")
        } else {
            println("PERMISSION_GRANTED")
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun navigate(fragment: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .apply {
                if (addToBackStack) {
                    addToBackStack(null)
                }
            }
            .commit()
    }

    override fun popBack() {
        supportFragmentManager.popBackStack()
    }
}
package com.omidrezabagherian.totishop.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.omidrezabagherian.totishop.R
import com.omidrezabagherian.totishop.core.Values
import com.omidrezabagherian.totishop.databinding.ActivityMainBinding
import com.omidrezabagherian.totishop.domain.model.createorder.Billing
import com.omidrezabagherian.totishop.domain.model.createorder.CreateOrder
import com.omidrezabagherian.totishop.domain.model.createorder.Shipping
import com.omidrezabagherian.totishop.ui.bag.BagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainSharedPreferences: SharedPreferences
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplashScreen()

        mainSharedPreferences = getSharedPreferences(Values.SHARED_PREFERENCES, MODE_PRIVATE)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        initBottomNavigation()

        setOrder()

        setContentView(mainBinding.root)
    }

    private fun initSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.isLoading.value
            }
        }
    }

    private fun setOrder() {
        val createOrder = CreateOrder(
            billing = Billing(
                address_1 = "کرج - فردیس - خیابان داوری - کوچه عباسی - پلاک ۳۰ - واحد ۳",
                email = "omidrezabagherian@yahoo.com",
                first_name = "اميدرضا",
                last_name = "باقریان اسفندانی",
                phone = "09028501761"
            ),
            shipping = Shipping(
                address_1 = "کرج - فردیس - خیابان داوری - کوچه عباسی - پلاک ۳۰ - واحد ۳",
                first_name = "اميدرضا",
                last_name = "باقریان اسفندانی",
            ),
            line_items = emptyList(),
            shipping_lines = emptyList()
        )

        mainViewModel.setOrders(createOrder)

        val mainSharedPreferencesEditor = mainSharedPreferences.edit()

        if (mainSharedPreferences.getInt(Values.ID_ORDER_SHARED_PREFERENCES, 0) == 0) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.setProductBagList.collect {
                        mainSharedPreferencesEditor.putInt(Values.ID_ORDER_SHARED_PREFERENCES, it.id)
                        mainSharedPreferencesEditor.commit()
                        mainSharedPreferencesEditor.apply()
                    }
                }
            }
        }
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMain)
                    as NavHostFragment
        navController = navHostFragment.navController

        mainBinding.bottomNavigationViewMain.setupWithNavController(navController)

        mainBinding.bottomNavigationViewMain.setOnItemSelectedListener { tab ->
            val fragmentMain = navController.currentDestination?.id?.plus(1)
            val fragmentNext = tab.itemId
            if (fragmentMain != fragmentNext) {
                when (tab.itemId) {
                    R.id.houseTab -> {
                        navController.navigate(R.id.houseFragment)
                    }
                    R.id.categoryTab -> {
                        navController.navigate(R.id.categoryFragment)
                    }
                    R.id.bagTab -> {
                        navController.navigate(R.id.bagFragment)
                    }
                    R.id.userTab -> {
                        navController.navigate(R.id.loginFragment)
                    }
                }
            }
            true
        }
    }

}
package com.omidrezabagherian.totishop.ui.login

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.omidrezabagherian.totishop.R
import com.omidrezabagherian.totishop.core.NetworkManager
import com.omidrezabagherian.totishop.core.Values
import com.omidrezabagherian.totishop.core.Values.EMAIL_SHARED_PREFERENCES
import com.omidrezabagherian.totishop.core.Values.ID_SHARED_PREFERENCES
import com.omidrezabagherian.totishop.core.Values.PASSWORD_SHARED_PREFERENCES
import com.omidrezabagherian.totishop.core.Values.SHARED_PREFERENCES
import com.omidrezabagherian.totishop.databinding.FragmentLoginBinding
import com.omidrezabagherian.totishop.domain.model.createcustomer.CreateCustomer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.HashMap

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var loginBinding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var loginSharedPreferences: SharedPreferences
    private val navController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBinding = FragmentLoginBinding.bind(view)

        registerPage()

        checkInternet()

    }

    private fun dialogCheckInternet() {
        val dialog = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_check_internet, null)
        val buttonCheckInternet: Button = dialogView.findViewById(R.id.buttonCheckInternet)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        val customDialog = dialog.create()
        customDialog.show()

        buttonCheckInternet.setOnClickListener {
            customDialog.dismiss()
            checkInternet()
        }
    }

    private fun checkInternet() {
        val networkConnection = NetworkManager(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnect ->
            if (isConnect) {
                checkLoginApp()
            } else {
                dialogCheckInternet()
            }
        }
    }

    private fun checkLoginApp() {
        loginSharedPreferences =
            requireActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        val id = loginSharedPreferences.getInt(ID_SHARED_PREFERENCES, 0)
        val email = loginSharedPreferences.getString(EMAIL_SHARED_PREFERENCES, "")
        val password = loginSharedPreferences.getString(PASSWORD_SHARED_PREFERENCES, "")

        if (email!!.isNotEmpty() && password!!.isNotEmpty()) {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToUserFragment(id))
        } else {
            loginApp()
        }
    }

    private fun loginApp() {
        loginSharedPreferences =
            requireActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        val loginSharedPreferencesEditor = loginSharedPreferences.edit()

        loginBinding.materialButtonLoginSubmit.setOnClickListener {
            loginViewModel.getCustomer(loginBinding.textInputEditTextLoginEmail.text.toString())
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    loginViewModel.customerInfo.collect { customer ->
                        if (customer.isEmpty()) {
                            Toast.makeText(requireContext(), "حساب وجود ندارد", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            if (loginBinding.textInputEditTextLoginPassword.text.toString() == customer[0].billing.phone) {
                                loginSharedPreferencesEditor.putInt(
                                    ID_SHARED_PREFERENCES,
                                    customer[0].id
                                )
                                loginSharedPreferencesEditor.putString(
                                    EMAIL_SHARED_PREFERENCES,
                                    loginBinding.textInputEditTextLoginEmail.text.toString()
                                )
                                loginSharedPreferencesEditor.putString(
                                    PASSWORD_SHARED_PREFERENCES,
                                    loginBinding.textInputEditTextLoginPassword.text.toString()
                                )
                                loginSharedPreferencesEditor.commit()
                                loginSharedPreferencesEditor.apply()
                                navController.navigate(
                                    LoginFragmentDirections.actionLoginFragmentToUserFragment(
                                        customer[0].id
                                    )
                                )

                                Toast.makeText(requireContext(), "وارد شد", Toast.LENGTH_SHORT)
                                    .show()

                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "رمز عبور اشتباه هست",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }
                }
            }
        }


    }

    private fun registerPage() {
        loginBinding.textViewLoginRegister.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

}
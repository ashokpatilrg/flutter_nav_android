package com.poc.nav.flutter_nav_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.lock.AuthenticationCallback
import com.auth0.android.lock.Lock
import com.auth0.android.result.Credentials

class SecondActivity : AppCompatActivity() {

    private lateinit var account: Auth0
    private lateinit var lock: Lock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        account = Auth0("ZH9D5TudHQLTvFVdM57kf9C9t9Z2rIMP", "dev-dxequij81gydcom5.us.auth0.com")

        showLockWidget()
    }

    private fun showLockWidget() {
        lock = Lock.newBuilder(account, callback)
            .withScope("openid profile offline_access email read:current_user")
            .allowLogIn(true)
            .withScheme("demo")
            .hideMainScreenTitle(true)
            .allowSignUp(false)
            .allowForgotPassword(true)
            .closable(true)
            .build(this)
        startActivity(lock.newIntent(this))
    }

    private val callback = object : AuthenticationCallback() {
        override fun onAuthentication(credentials: Credentials) {
            Log.e(
                "TAG",
                "onAuthentication: auth0 authentication success ${
                    credentials.accessToken
                } \n ${credentials.refreshToken} ",
            )
            // Authenticated
            Toast.makeText(this@SecondActivity, "Authenticated", Toast.LENGTH_SHORT).show()
        }

        override fun onError(error: AuthenticationException) {
            Log.e("TAG", "onError: auth0 callback error ${error.message}")
            // Exception occurred
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lock.onDestroy(this)
    }
}
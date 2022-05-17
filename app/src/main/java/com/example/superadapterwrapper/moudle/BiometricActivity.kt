package com.example.superadapterwrapper.moudle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.example.superadapterwrapper.base.BaseActivity
import com.idonans.lang.util.ToastUtil
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.example.superadapterwrapper.databinding.ActivityBiometricBinding
import com.socks.library.KLog
import okhttp3.internal.Util
import java.util.concurrent.Executor

class BiometricActivity : BaseActivity() {
    private val TAG = "BiometricActivity"
    private lateinit var binding: ActivityBiometricBinding
    override fun setContentView() {
        binding = ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initView() {}
    override fun initData(savedInstanceState: Bundle) {
        binding.biometricLogin.setOnClickListener {
            val biometricManager = BiometricManager.from(this@BiometricActivity)
            when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    showBiometricPrompt()
                    ToastUtil.show("应用可以进行生物识别技术进行身份验证。")
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> ToastUtil.show("该设备上没有搭载可用的生物特征功能。")
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> ToastUtil.show("生物识别功能当前不可用。")
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> ToastUtil.show("用户没有录入生物识别数据。")
                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                    TODO()
                }
                BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                    TODO()
                }
                BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                    TODO()
                }
            }
        }
        binding.bindBiometric.setOnClickListener {
            val token = java.util.UUID.randomUUID().toString()
            showBiometricPromptForEncryption()
        }
    }

    private val handler = Handler()
    private val executor = Executor { command -> handler.post(command) }

    private fun showBiometricPromptForEncryption() {
        val canAuthenticate = BiometricManager.from(applicationContext)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        when (canAuthenticate) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val biometricPrompt = createBiometricPrompt(this, ::encryptAndStoreServerToken)
                val promptInfo = createPromptInfo(this)
                //biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject())

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> ToastUtil.show("该设备上没有搭载可用的生物特征功能。")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> ToastUtil.show("生物识别功能当前不可用。")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> ToastUtil.show("用户没有录入生物识别数据。")
            else -> {
                ToastUtil.show("生物识别功能当前不可用。")
            }
        }
    }

    private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
        authResult.cryptoObject?.cipher?.apply {

        }
    }

    //生物认证的setting
    private fun showBiometricPrompt() {
        val promptInfo = PromptInfo.Builder()
            .setTitle("Biometric login for my app") //设置大标题
            .setSubtitle("Log in using your biometric credential") // 设置标题下的提示
            .setNegativeButtonText("取消") //设置取消按钮
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        //需要提供的参数callback
        val biometricPrompt = BiometricPrompt(this,
            executor, object : BiometricPrompt.AuthenticationCallback() {
                //各种异常的回调
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                //认证成功的回调
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    val authenticatedCryptoObject = result.cryptoObject
                    // User has verified the signature, cipher, or message
                    // authentication code (MAC) associated with the crypto object,
                    // so you can use it in your app's crypto-driven workflows.
                }

                //认证失败的回调
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        // 显示认证对话框
        biometricPrompt.authenticate(promptInfo)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, BiometricActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun createBiometricPrompt(
        activity: AppCompatActivity,
        processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errCode, errString)
                KLog.d(TAG, "errCode is $errCode and errString is: $errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                KLog.d(TAG, "Biometric authentication failed for unknown reason.")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                KLog.d(TAG, "Authentication was successful")
                processSuccess(result)
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }

    private fun createPromptInfo(activity: AppCompatActivity): BiometricPrompt.PromptInfo =
        PromptInfo.Builder().apply {
            setTitle("Biometric login for my app") //设置大标题
            setSubtitle("Log in using your biometric credential") // 设置标题下的提示
            setNegativeButtonText("取消") //设置取消按钮
            setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        }.build()
}
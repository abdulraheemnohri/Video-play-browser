package com.videoplay.browser.security

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

/**
 * Manages biometric authentication (fingerprint/face unlock) for the app.
 */
class BiometricLock(private val context: Context) {

    private val executor: Executor = ContextCompat.getMainExecutor(context)
    private lateinit var biometricPrompt: BiometricPrompt
    private var onAuthenticationSucceeded: (() -> Unit)? = null
    private var onAuthenticationFailed: (() -> Unit)? = null
    private var onAuthenticationError: ((Int, String) -> Unit)? = null

    fun showBiometricPrompt(
        activity: FragmentActivity,
        title: String = "Unlock VIDEOPlay",
        subtitle: String = "Authenticate to access the app",
        onSucceeded: () -> Unit,
        onFailed: () -> Unit,
        onError: (Int, String) -> Unit
    ) {
        this.onAuthenticationSucceeded = onSucceeded
        this.onAuthenticationFailed = onFailed
        this.onAuthenticationError = onError

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onAuthenticationSucceeded?.invoke()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onAuthenticationFailed?.invoke()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onAuthenticationError?.invoke(errorCode, errString.toString())
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }

    fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun cancelAuthentication() {
        if (::biometricPrompt.isInitialized) {
            biometricPrompt.cancelAuthentication()
        }
    }
}

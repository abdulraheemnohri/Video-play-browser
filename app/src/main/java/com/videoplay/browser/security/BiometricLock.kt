package com.videoplay.browser.security

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

/**
 * Manages biometric authentication (fingerprint/face unlock) for the app.
 */
class BiometricLock(private val context: Context) {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private var onAuthenticationSucceeded: (() -> Unit)? = null
    private var onAuthenticationFailed: (() -> Unit)? = null
    private var onAuthenticationError: ((Int, String) -> Unit)? = null

    init {
        executor = ContextCompat.getMainExecutor(context)
    }

    /**
     * Shows the biometric authentication prompt.
     * @param activity The activity to attach the prompt to.
     * @param title The title of the prompt.
     * @param subtitle The subtitle of the prompt.
     * @param onSucceeded Callback for successful authentication.
     * @param onFailed Callback for failed authentication.
     * @param onError Callback for authentication errors.
     */
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
            .setAllowedAuthenticators(BiometricPrompt.AUTHENTICATOR_BIOMETRIC_STRONG)
            .setNegativeButtonText("Cancel")
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

    /**
     * Checks if biometric authentication is available on the device.
     */
    fun isBiometricAvailable(): Boolean {
        return BiometricPrompt.isDeviceCredentialAllowed(context)
    }

    /**
     * Cancels the biometric prompt if it is showing.
     */
    fun cancelAuthentication() {
        if (::biometricPrompt.isInitialized) {
            biometricPrompt.cancelAuthentication()
        }
    }
}

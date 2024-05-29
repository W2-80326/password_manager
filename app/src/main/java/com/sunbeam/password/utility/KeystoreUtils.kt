package com.sunbeam.password.utility

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object KeystoreUtils
{
    private const val KEY_ALIAS = "MyKey"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"

    fun generateKey(){
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE)
        val keyGenParaSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setRandomizedEncryptionRequired(true)
            .build()
        keyGenerator.init(keyGenParaSpec)
        keyGenerator.generateKey()
    }

    fun getKey() : SecretKey? {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        return keyStore.getKey(KEY_ALIAS,null) as SecretKey?
    }
}
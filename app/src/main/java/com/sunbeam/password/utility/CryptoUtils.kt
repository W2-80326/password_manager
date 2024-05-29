package com.sunbeam.password.utility

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object CryptoUtils {
    private const val TRANSFORMATION = "AES/ECB/PKCS5Padding"

    fun encrypt(pass : String, key : SecretKey) : String{
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE,key)
        val encryptedByte = cipher.doFinal(pass.toByteArray())
        return Base64.encodeToString(encryptedByte,Base64.DEFAULT)
    }

    fun decrypt(encryptPass : String, key: SecretKey) : String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE,key)
        val decodeBytes = Base64.decode(encryptPass,Base64.DEFAULT)
        val decryptedByte = cipher.doFinal(decodeBytes)
        return String(decryptedByte)
    }
}
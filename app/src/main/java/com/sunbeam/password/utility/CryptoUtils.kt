package com.sunbeam.password.utility

import android.util.Base64
import android.util.Log
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object CryptoUtils {
    private const val TRANSFORMATION = "AES/CBC/PKCS7Padding"

    fun generateIV() : ByteArray{
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        Log.e("Main","$iv mklb")
        return iv
    }

    fun encrypt(pass : String, key : SecretKey) : Pair<String, String>{
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = generateIV()
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE,key,ivSpec)
        val encryptedByte = cipher.doFinal(pass.toByteArray())
        val encryptedData = Base64.encodeToString(encryptedByte,Base64.DEFAULT)
        val encodedIv = Base64.encodeToString(iv, Base64.DEFAULT)
        return Pair(encryptedData, encodedIv)
    }

    fun decrypt(encryptPass : String, key: SecretKey, iv: String) : String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val ivBytes = Base64.decode(iv, Base64.DEFAULT)
        val ivSpec = IvParameterSpec(ivBytes)
        cipher.init(Cipher.DECRYPT_MODE,key,ivSpec)
        val decodeBytes = Base64.decode(encryptPass,Base64.DEFAULT)
        val decryptedByte = cipher.doFinal(decodeBytes)
        return String(decryptedByte)
    }
}
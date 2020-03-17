package com.kotlin.test.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testButton.setOnClickListener {
            Toast.makeText(this, "Button Clicked..!", Toast.LENGTH_LONG).show()
            testCertificatePinning()
        }
    }

    fun testCertificatePinning() {
        // Define host names
        val hostName = "publicobject.com"
        val baseUrl = "https://$hostName"

        // Create certificate pinning builder
        val certificatePinner = CertificatePinner.Builder()
            .add(hostName, "sha256//r8udi/Mxd6pLO7y7hZyUMWq8YnFnIWXCqeHsTDRqy8=",
                "sha256/YLh1dUR9y6Kja30RrAn7JKnbQG/uEtLMkBgFF2Fuihg=",
                "sha256/Vjs8r4z+80wjNcr1YKepWQboSIRi63WsWXhIMN+eWys=")
            .build()

        // Create OkHttpClient with certificate pinning builder
        val okHttpClientBuilder = OkHttpClient.Builder()
        val okHttpClient = okHttpClientBuilder
            .certificatePinner(certificatePinner)
            .build()

        // Create request
        val request = Request.Builder()
            .url(baseUrl)
            .build()

        // Execute request using OkHttpClient
        val response = okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("OKHTTP", "Request Failed : ${e.message}")
                this@MainActivity.runOnUiThread { Toast.makeText(this@MainActivity, "Response Failed..!", Toast.LENGTH_LONG).show() }
            }

            override fun onResponse(call: Call, response: Response) {
                this@MainActivity.runOnUiThread { Toast.makeText(this@MainActivity, "Response Successful..!", Toast.LENGTH_LONG).show() }
            }

        })
    }
}

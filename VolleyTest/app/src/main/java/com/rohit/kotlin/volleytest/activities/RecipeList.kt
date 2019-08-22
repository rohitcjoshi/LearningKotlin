package com.rohit.kotlin.volleytest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rohit.kotlin.volleytest.R
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    var volleyReq: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recipe_list)

        volleyReq = Volley.newRequestQueue(this)

    }

    fun getRecipe(url: String, queryIngredient: String, querySearch: String) {
        val recipeReq = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                response: JSONObject ->
                try {

                } catch (e: JSONException) {e.printStackTrace()}
            },
            Response.ErrorListener {
                error: VolleyError? ->
                Log.d("Error:===>", "  " + error.toString())
            })
    }
}

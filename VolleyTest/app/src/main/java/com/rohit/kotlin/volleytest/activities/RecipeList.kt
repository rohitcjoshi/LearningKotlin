package com.rohit.kotlin.volleytest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rohit.kotlin.volleytest.R
import com.rohit.kotlin.volleytest.data.RecipeListAdapter
import com.rohit.kotlin.volleytest.model.QUERY
import com.rohit.kotlin.volleytest.model.Recipe
import com.rohit.kotlin.volleytest.model.SERVER_LINK
import kotlinx.android.synthetic.main.layout_recipe_list.*
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    var volleyReq: RequestQueue? = null
    var recipeList: ArrayList<Recipe>? = null

    var recipeAdapter: RecipeListAdapter? = null
    var layoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recipe_list)

        volleyReq = Volley.newRequestQueue(this)
        recipeList = ArrayList<Recipe>()


        layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeListAdapter(recipeList!!, this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recipeAdapter

        getRecipe(SERVER_LINK, "ginger", "soup")
    }

    fun getRecipe(url: String, queryIngredient: String, querySearch: String) {
        val recipeReq = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                response: JSONObject ->
                try {
                    val resultArray = response.getJSONArray("results")
                    for(i in 0..resultArray.length() - 1) {
                        val recipeObj = resultArray.getJSONObject(i)
                        val title = recipeObj.getString("title")
                        val link = recipeObj.getString("href")
                        val thumbnail = recipeObj.getString("thumbnail")
                        val ingredients = recipeObj.getString("ingredients")

                        val recipe = Recipe()
                        recipe.title = title
                        recipe.link = link
                        recipe.thumbnailUrl = thumbnail
                        recipe.ingredients = ingredients
                        recipeList!!.add(recipe)

                        Log.d("RECIPE:==>", " Title: $title")
                    }
                    recipeAdapter!!.notifyDataSetChanged()
                } catch (e: JSONException) {e.printStackTrace()}
            },
            Response.ErrorListener {
                error: VolleyError? ->
                Log.d("Error:===>", "  " + error.toString())
            })
        volleyReq!!.add(recipeReq)
    }
}

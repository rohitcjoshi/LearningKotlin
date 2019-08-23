package com.rohit.kotlin.volleytest.data

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.volleytest.R
import com.rohit.kotlin.volleytest.model.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val list: ArrayList<Recipe>,
                        private val context: Context): RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecipeListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size-1
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.ViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvRecipeTitle)
        val thumbnail = itemView.findViewById<ImageView>(R.id.ivThumbnail)
        val ingredients = itemView.findViewById<TextView>(R.id.tvIngredientTitle)
        val btnLink = itemView.findViewById<Button>(R.id.btnLink)

        fun bindView(recipe: Recipe) {
            title.text = recipe.title
            ingredients.text = recipe.ingredients
            if(!TextUtils.isEmpty(recipe.thumbnailUrl)) {
                Picasso.get().load(recipe.thumbnailUrl)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(thumbnail);
            } else {
                Picasso.get().load(R.mipmap.ic_launcher)
            }
            btnLink.setOnClickListener {  }
        }
    }
}
package com.example.retrofit_test.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_test.R
import com.example.retrofit_test.databinding.ListItemBinding
import com.example.retrofit_test.retrofit.Product
import kotlinx.android.synthetic.main.list_item.view.*

class ProductAdapter : ListAdapter<Product, ProductAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ListItemBinding.bind(view)

        fun bind(product: Product)= with(binding){
            title.text = product.name
            category.text = product.category

            dateExpired.text = "Термін: ${product.expiredDate}"
            lot.text = "LOT"
            count.text = "${product.count}шт."
            dateExpired.setTextColor(Color.GRAY)

            if (product.ref == null) {
                ref.text = ""
                ref.height = 0
            } else {
                ref.text = "REF: ${product.ref}"
            }

            if (product.smn_code == null) {
                smn.text = ""
                smn.height = 0
            } else {
                smn.text = "SMN Code: ${product.smn_code}"
            }

        }
    }

    class Comparator : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        holder.itemView.setOnClickListener {
            val clipboardManager = holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val text = "${it.title.text}\n${it.category.text}\n" +
                    "${it.ref.text}\n" +
                    "${it.smn.text}"
            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)

//            Toast.makeText(holder.itemView.context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
        }

        holder.itemView.setOnLongClickListener {
            it.context.startActivity(shareIntent)
            return@setOnLongClickListener true
        }


    }
}
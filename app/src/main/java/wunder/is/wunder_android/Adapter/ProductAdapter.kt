package wunder.`is`.wunder_android

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product.view.*
import org.apache.commons.lang3.text.WordUtils
import wunder.`is`.wunder_android.Adapter.CustomViewHolder


class ProductAdapter(val productArray: Array<ProductFeed>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return productArray.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_product, parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val semibold = Typeface.createFromAsset(holder.view.context!!.assets, "fonts/NunitoSans-SemiBold.ttf")
        val extrabold = Typeface.createFromAsset(holder.view.context!!.assets, "fonts/NunitoSans-ExtraBold.ttf")

        val title = holder.view.textView_title
        title.setTypeface(semibold, Typeface.NORMAL)
        title.text = WordUtils.capitalize(productArray[position].title)

        val logo = holder.view.imageView_logo
        Picasso.with(holder.view.context).load(productArray[position].logoUrl).into(logo)



    }
}
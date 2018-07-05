package wunder.`is`.wunder_android

import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_card.view.*
import org.apache.commons.lang3.text.WordUtils
import wunder.`is`.wunder_android.Adapter.CustomViewHolder


class CategoryCardAdapter(val categoryArray: Array<CategoryFeed>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return categoryArray.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_category_card, parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val medium = Typeface.createFromAsset(holder.view.context!!.assets, "fonts/Montserrat-Medium.ttf")
        val extrabold = Typeface.createFromAsset(holder.view.context!!.assets, "fonts/NunitoSans-ExtraBold.ttf")

        val title = holder.view.textView_title
        title.setTypeface(medium, Typeface.BOLD)
        title.text = WordUtils.capitalize(categoryArray[position].title)

        val readmore = holder.view.textView_readmore
        readmore.setTypeface(extrabold, Typeface.NORMAL)

        readmore.setOnClickListener {
            val manager = (holder.view.context as AppCompatActivity).supportFragmentManager
            val transaction = manager.beginTransaction()
            var fragment: Fragment = ProductList(categoryArray[position].alias, categoryArray[position].title)
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
            transaction.replace(R.id.content_main, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }



    }
}
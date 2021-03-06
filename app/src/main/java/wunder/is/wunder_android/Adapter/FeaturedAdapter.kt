package wunder.`is`.wunder_android

import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_featured.view.*
import wunder.`is`.wunder_android.Adapter.CustomViewHolder
import wunder.`is`.wunder_android.R.id.imageView


class FeaturedAdapter(val featuredArray: Array<FeaturedFeed>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return featuredArray.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_featured, parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val semibold = Typeface.createFromAsset(holder.view.context!!.assets, "fonts/Nickainley-Normal.ttf")
        val extrabold = Typeface.createFromAsset(holder.view.context!!.assets, "fonts/NunitoSans-ExtraBold.ttf")

        val title = holder.view.textView_title
        title.setTypeface(semibold, Typeface.BOLD)
        title.text = featuredArray[position].title

        val logo = holder.view.imageView_logo
        Picasso.with(holder.view.context).load(featuredArray[position].logoUrl).into(logo)

        val readmore = holder.view.textView_readmore
        readmore.setTypeface(extrabold, Typeface.NORMAL)

        readmore.setOnClickListener {
            val manager = (holder.view.context as AppCompatActivity).supportFragmentManager
            val transaction = manager.beginTransaction()
            var fragment: Fragment = Product(featuredArray[position].category, featuredArray[position].alias)
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
            transaction.replace(R.id.content_main, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }



    }
}
package wunder.`is`.wunder_android

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

class Home: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

        setupFont()

        setupFeatured()

        setupCategory()

        setupNavigation()
    }

    private fun setupFont() {

        val bold = Typeface.createFromAsset(context!!.assets, "fonts/NunitoSans-Bold.ttf")
        val semibold = Typeface.createFromAsset(context!!.assets, "fonts/NunitoSans-SemiBold.ttf")


        textView_navigationtitle.setTypeface(bold, Typeface.NORMAL)
        textView_featured.setTypeface(semibold, Typeface.NORMAL)
        textView_featuredline.setTypeface(semibold, Typeface.NORMAL)
        textView_categories.setTypeface(semibold, Typeface.NORMAL)
        textView_categoriesline.setTypeface(semibold, Typeface.NORMAL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    private fun setupFeatured() {
        recyclerView_featured.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView_featured)

        val url = "http://35.204.216.133/api/featured"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val featuredFeed = gson.fromJson(body, Array<FeaturedFeed>::class.java)

                if (activity != null) {
                    activity!!.runOnUiThread {
                        if (recyclerView_featured != null) {
                            recyclerView_featured.adapter = FeaturedAdapter(featuredFeed)
                        }
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }

        })
    }

    private fun setupCategory() {
        recyclerView_categories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView_categories)

        val url = "http://35.204.216.133/api/category"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val categoryFeed = gson.fromJson(body, Array<CategoryFeed>::class.java)

                if (activity != null) {
                    activity!!.runOnUiThread {
                        if (recyclerView_categories != null) {
                            recyclerView_categories.adapter = CategoryAdapter(categoryFeed)
                        }
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }

        })
    }

    private fun setupNavigation() {
        imageView_magnify.setOnClickListener {
            val manager = (context as AppCompatActivity).supportFragmentManager
            val transaction = manager.beginTransaction()
            var fragment: Fragment = Category()
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
            transaction.replace(R.id.content_main, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }




}
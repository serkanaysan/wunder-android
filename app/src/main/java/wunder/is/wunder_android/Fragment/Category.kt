package wunder.`is`.wunder_android

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_category.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class Category: Fragment() {

    var categoryList = ArrayList<CategoryFeed>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

        setupFont()

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
        return inflater!!.inflate(R.layout.fragment_category, container, false)
    }

    private fun setupCategory() {
        recyclerView_featured.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = object : LinearSnapHelper() {
            override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
                val centerView = findSnapView(layoutManager!!)
                        ?: return RecyclerView.NO_POSITION

                val position = layoutManager.getPosition(centerView)



                var targetPosition = -1
                if (layoutManager.canScrollHorizontally()) {
                    if (velocityX < 0) {
                        targetPosition = position - 1
                    } else {
                        targetPosition = position + 1
                    }
                }


                val firstItem = 0
                val lastItem = layoutManager.itemCount - 1
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem))

                setupProduct(categoryList[targetPosition].alias)

                return targetPosition
            }
        }
        snapHelper.attachToRecyclerView(recyclerView_featured)


        recyclerView_categories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper2 = PagerSnapHelper()
        snapHelper2.attachToRecyclerView(recyclerView_categories)




        val url = "http://35.204.216.133/api/category"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val categoryFeed = gson.fromJson(body, Array<CategoryFeed>::class.java)
                categoryFeed.forEach {
                    categoryList.add(CategoryFeed(it.title, it.alias))
                }

                if (activity != null) {
                    activity!!.runOnUiThread {
                        if (recyclerView_featured != null) {
                            recyclerView_featured.adapter = CategoryCardAdapter(categoryFeed)
                            setupProduct(categoryList[0].alias)
                        }
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }

        })
    }

    private fun setupProduct(alias: String) {

        val url = "http://35.204.216.133/api/category/" + alias
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val productFeed = gson.fromJson(body, Array<ProductFeed>::class.java)

                if (activity != null) {
                    activity!!.runOnUiThread {
                        if (recyclerView_categories != null) {
                            recyclerView_categories.adapter = ProductAdapter(productFeed)
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
        textView_navigationtitle.setOnClickListener {
            val manager = fragmentManager
            manager!!.popBackStack()
        }
    }



}
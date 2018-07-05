package wunder.`is`.wunder_android

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_productlist.*
import okhttp3.*
import org.apache.commons.lang3.text.WordUtils
import java.io.IOException

@SuppressLint("ValidFragment")
class ProductList(val alias: String, val title: String): Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFont()

        setupProduct()

        setupNavigation()
    }

    private fun setupFont() {

        val bold = Typeface.createFromAsset(context!!.assets, "fonts/NunitoSans-Bold.ttf")
        val semibold = Typeface.createFromAsset(context!!.assets, "fonts/NunitoSans-SemiBold.ttf")


        textView_navigationtitle.setTypeface(bold, Typeface.NORMAL)
        textView_navigationtitle2.setTypeface(bold, Typeface.NORMAL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_productlist, container, false)
    }

    private fun setupProduct() {
        textView_navigationtitle.text = WordUtils.capitalize(title)
        recyclerView_product.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val url = "http://35.204.216.133/api/category/" + alias + "?v2"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val productFeed = gson.fromJson(body, Array<ProductFeed>::class.java)
println(body)
                if (activity != null) {
                    activity!!.runOnUiThread {
                        if (recyclerView_product != null) {
                            recyclerView_product.adapter = ProductListAdapter(productFeed)
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
        textView_navigationtitle2.setOnClickListener {
            val manager = fragmentManager
            manager!!.popBackStack()
        }
    }




}
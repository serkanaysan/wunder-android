package wunder.`is`.wunder_android

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.*
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_product.*
import okhttp3.*
import java.io.IOException

@SuppressLint("ValidFragment")
class Product(val category: String, val alias: String): Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

        setupProduct()

    }

    private fun setupProduct() {

        val font = Typeface.createFromAsset(context!!.assets, "fonts/Montserrat-Medium.ttf")



        textView_title.setTypeface(font, Typeface.BOLD)
        textView_rep.setTypeface(font, Typeface.NORMAL)
        textView_desc.setTypeface(font, Typeface.NORMAL)
        textView_body.setTypeface(font, Typeface.NORMAL)



        val url = "http://35.204.216.133/api/" + category + "/" + alias + "?v2"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()


                val productDetailFeed = gson.fromJson(body, ProductDetailFeed::class.java)

                if (activity != null) {
                    activity!!.runOnUiThread {
                        Picasso.with(context).load(productDetailFeed.posterUrl).into(imageView_poster)
                        Picasso.with(context).load(productDetailFeed.logoUrl).into(imageView_logo)
                        textView_title.text = productDetailFeed.title
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            textView_rep.text = Html.fromHtml(productDetailFeed.representation,Html.FROM_HTML_MODE_LEGACY)
                            textView_desc.text = Html.fromHtml(productDetailFeed.description,Html.FROM_HTML_MODE_LEGACY)
                            textView_body.text = Html.fromHtml(productDetailFeed.body,Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            textView_rep.text = Html.fromHtml(productDetailFeed.representation)
                            textView_desc.text = Html.fromHtml(productDetailFeed.description)
                            textView_body.text = Html.fromHtml(productDetailFeed.body)
                        }
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }

        })
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_product, container, false)
    }






}
package wunder.`is`.wunder_android

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*

class Splash: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        var background = object : Thread(){
            override fun run() {
                try {
                    Thread.sleep(3000)

                    val manager = (context as AppCompatActivity).supportFragmentManager
                    val transaction = manager.beginTransaction()
                    var fragment:Fragment = Home()

                    transaction.replace(R.id.content_main, fragment)
                    transaction.commit()

                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

        background.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_splash, container, false)
    }



}
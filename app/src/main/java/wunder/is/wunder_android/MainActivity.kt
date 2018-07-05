package wunder.`is`.wunder_android

import android.content.pm.ActivityInfo
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        startSplash()

        setNoStatusBar()


    }

    private fun startSplash() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        var fragment: Fragment = Splash()

        transaction.replace(R.id.content_main, fragment)
        transaction.commit()
    }

    private fun setNoStatusBar() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.window.setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT)
            this.window.decorView.systemUiVisibility = 3328
        }else{
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

}

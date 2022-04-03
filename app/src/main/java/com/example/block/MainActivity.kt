package com.example.block

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import com.example.block.configs.BlockConfig

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_register).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                BlockKit.inst.register(this@MainActivity, BlockConfig())
            }
        })

        findViewById<Button>(R.id.btn_unregister).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                BlockKit.inst.unRegister()
            }
        })

        findViewById<Button>(R.id.btn_block).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                SystemClock.sleep(2500)
            }
        })
    }
}
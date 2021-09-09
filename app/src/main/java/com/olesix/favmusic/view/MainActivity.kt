package com.olesix.favmusic.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.olesix.favmusic.R


import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FragmentAlbums>(R.id.fragment_container_view)
            }
        }
    }
}
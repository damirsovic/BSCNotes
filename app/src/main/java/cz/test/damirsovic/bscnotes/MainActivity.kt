package cz.test.damirsovic.bscnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import cz.test.damirsovic.bscnotes.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProviders.of(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getNotes()
    }
}

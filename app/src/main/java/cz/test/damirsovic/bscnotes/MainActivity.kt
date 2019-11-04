package cz.test.damirsovic.bscnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cz.test.damirsovic.bscnotes.view.NotesListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(NotesListFragment.newInstance())
    }

    fun setFragment(fragment: Fragment) {
        if (layoutMain.childCount == 0)
            addFragment(fragment)
        else
            replaceFragment(fragment)
    }

    fun addFragment(fragment: Fragment) {
        System.out.println(fragment::class.java.name)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.layoutMain, fragment, fragment::class.java.name)
            .commit()
    }

    fun replaceFragment(fragment: Fragment) {
        System.out.println(fragment::class.java.name)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layoutMain, fragment, fragment::class.java.name)
            .commit()
    }
}

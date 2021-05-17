package ale.ziolo.uhf_rfid.view

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.view.ui.home.RulesFragment
import ale.ziolo.uhf_rfid.view.ui.profile.ProfileFragment
import ale.ziolo.uhf_rfid.view.ui.items.ItemsFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val profileFragment = ProfileFragment()
    private val rulesFragment= RulesFragment()
    private val itemsFragment= ItemsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(rulesFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_rules ->setCurrentFragment(rulesFragment)
                R.id.navigation_items->setCurrentFragment(itemsFragment)
                R.id.navigation_profile->setCurrentFragment(profileFragment)

            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}
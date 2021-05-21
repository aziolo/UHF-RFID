package ale.ziolo.uhf_rfid.view.ui.ruleFragment

import ale.ziolo.uhf_rfid.databinding.FragmentRulesBinding
import ale.ziolo.uhf_rfid.view.ui.addItem.AddItemActivity
import ale.ziolo.uhf_rfid.view.ui.addRule.AddRuleActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class RulesFragment : Fragment() {

  private lateinit var rulesViewModel: RulesViewModel
private var _binding: FragmentRulesBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    rulesViewModel =
            ViewModelProvider(this).get(RulesViewModel::class.java)

    _binding = FragmentRulesBinding.inflate(inflater, container, false)
    val root: View = binding.root

//    val textView: TextView = binding.textHome
//    rulesViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })

      binding.buttonAddRule.setOnClickListener {
          val intent = Intent(context, AddRuleActivity::class.java)
          startActivity(intent)
      }

    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package ale.ziolo.uhf_rfid.view.ui.ruleFragment

import ale.ziolo.uhf_rfid.databinding.FragmentRulesBinding
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.view.ui.addRule.AddRuleActivity
import ale.ziolo.uhf_rfid.view.ui.itemsFragment.ItemsViewModel
import ale.ziolo.uhf_rfid.view.ui.itemsFragment.RuleListAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import javax.inject.Inject

class RulesFragment : Fragment() {

    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RuleListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val ruleViewModel: RulesViewModel by lazy {
        ViewModelProviders.of(this).get(
            RulesViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapter = RuleListAdapter(this)
        binding.ruleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.ruleRecyclerView.adapter = adapter
        ruleViewModel.getAllData()
            .observe(viewLifecycleOwner, Observer<List<RuleEntity>> { updatedList ->
                adapter.setItem(updatedList!!)
            })

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
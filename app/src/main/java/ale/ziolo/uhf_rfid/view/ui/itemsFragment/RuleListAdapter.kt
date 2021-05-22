package ale.ziolo.uhf_rfid.view.ui.itemsFragment

import ale.ziolo.uhf_rfid.databinding.RecyclerViewElementRulesBinding
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.view.ui.ruleFragment.RulesFragment
import ale.ziolo.uhf_rfid.view.ui.ruleFragment.RulesViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

class RuleListAdapter(private val fragment: RulesFragment): RecyclerView.Adapter<RuleListAdapter.ViewHolder>() {


    private var list: List<RuleEntity> = ArrayList()
    private lateinit var chosen: RuleEntity
    private val viewModel: RulesViewModel by lazy {
        ViewModelProviders.of(fragment).get(
            RulesViewModel::class.java
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)
        val binding = RecyclerViewElementRulesBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRule = list[position]
        val slot = currentRule.start + " - " + currentRule.stop
        var elements = currentRule.name1
        if (currentRule.priority == currentRule.tag1) elements = elements + " (*)"
        if (currentRule.priority == currentRule.tag2) elements = elements + ",   " + currentRule.name2 + " (*)"
        holder.name.text = currentRule.title
        holder.slot.text = slot
        holder.elements.text = elements
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItem(list: List<RuleEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RecyclerViewElementRulesBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.rveTextRuleName
        var slot = binding.rveTimeSlot
        var elements = binding.rveElements

    }

    private fun chooseValue(holder: ViewHolder) {
        chosen = list[holder.adapterPosition]
    }

}

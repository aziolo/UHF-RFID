package ale.ziolo.uhf_rfid.view.ui.ruleFragment

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.databinding.RecyclerViewElementRulesBinding
import ale.ziolo.uhf_rfid.firestore.FirestoreViewModel
import ale.ziolo.uhf_rfid.model.entities.RuleEntity
import ale.ziolo.uhf_rfid.view.ui.itemsFragment.ItemListAdapter
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private val firestoreViewModel: FirestoreViewModel by lazy {
        ViewModelProviders.of(fragment).get(
            FirestoreViewModel::class.java
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
        if (currentRule.name2.isNotEmpty()) {
            elements = elements + ",   " + currentRule.name2
        }
        if (currentRule.priority == currentRule.tag2) elements = elements + " (*)"
        if (currentRule.name3.isNotEmpty()) {
            elements = elements + ",   " + currentRule.name3
        }
        if (currentRule.priority == currentRule.tag3) elements = elements + " (*)"
        if (currentRule.name4.isNotEmpty()) {
            elements = elements + ",   " + currentRule.name4
        }
        if (currentRule.priority == currentRule.tag4) elements = elements + " (*)"
        if (currentRule.name5.isNotEmpty()) {
            elements = elements + ",   " + currentRule.name5
        }
        if (currentRule.priority == currentRule.tag5) elements = elements + " (*)"

        holder.name.text = currentRule.title
        holder.slot.text = slot
        holder.elements.text = elements
        holder.delete.setOnClickListener{
            chooseValue(holder)
            showDeleteDialog()
        }
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
        var delete = binding.rveDeleteButton

    }

    private fun chooseValue(holder: RuleListAdapter.ViewHolder) {
        chosen = list[holder.adapterPosition]
    }

    private fun showDeleteDialog() {
        val dialogButtonClick = { _: DialogInterface, which: Int ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> delete()
            }
        }
        val dialogBuilder = AlertDialog.Builder(fragment.requireContext()).create()
        dialogBuilder.setTitle(fragment.requireContext().resources.getString(R.string.delete))
        dialogBuilder.setMessage(fragment.requireContext().resources.getString(R.string.delete_accept_rule))
        dialogBuilder.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            "Nie",
            DialogInterface.OnClickListener(dialogButtonClick)
        )
        dialogBuilder.setButton(
            DialogInterface.BUTTON_POSITIVE,
            "Tak",
            DialogInterface.OnClickListener(dialogButtonClick)
        )
        dialogBuilder.show()
    }
    private fun delete(){
        try {
            viewModel.delete(chosen)
            firestoreViewModel.deleteRule(chosen)
            notifyDataSetChanged()
            Toast.makeText(
                fragment.context,
                fragment.requireContext().resources.getString(R.string.deleted),
                Toast.LENGTH_SHORT
            ).show()

        }catch (e: Exception){
            Toast.makeText(
                fragment.context,
                fragment.requireContext().resources.getString(R.string.deleted),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

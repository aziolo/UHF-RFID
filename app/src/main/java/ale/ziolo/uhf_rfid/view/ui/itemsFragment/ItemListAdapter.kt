package ale.ziolo.uhf_rfid.view.ui.itemsFragment

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.databinding.RecyclerViewElementItemsBinding
import ale.ziolo.uhf_rfid.firestore.FirestoreViewModel
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

class ItemListAdapter(private val fragment: ItemsFragment): RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {


    private var list: List<ItemEntity> = ArrayList()
    private lateinit var chosen: ItemEntity
    private val viewModel: ItemsViewModel by lazy {
        ViewModelProviders.of(fragment).get(
            ItemsViewModel::class.java
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
        val binding = RecyclerViewElementItemsBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]

        holder.name.text = currentItem.name
        holder.tag.text = currentItem.tag
        holder.delete.setOnClickListener{
            chooseValue(holder)
            showDeleteDialog()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItem(list: List<ItemEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RecyclerViewElementItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.rviTextItemName
        var tag = binding.rviTextItemTag
        var delete = binding.rviDeleteButton

    }

    private fun chooseValue(holder: ViewHolder) {
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
        dialogBuilder.setMessage(fragment.requireContext().resources.getString(R.string.delete_accept_item))
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
            firestoreViewModel.deleteItem(chosen)
            notifyDataSetChanged()
            Toast.makeText(
                fragment.context,
                fragment.requireContext().resources.getString(R.string.deleted),
                Toast.LENGTH_SHORT
            ).show()

        }catch (e: Exception){
            Toast.makeText(
                fragment.context,
                fragment.requireContext().resources.getString(R.string.not_deleted),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
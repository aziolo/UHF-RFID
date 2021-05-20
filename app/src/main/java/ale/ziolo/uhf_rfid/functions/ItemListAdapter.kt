package ale.ziolo.uhf_rfid.functions

import ale.ziolo.uhf_rfid.R
import ale.ziolo.uhf_rfid.databinding.RecyclerViewElementItemsBinding
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.view.ui.items.ItemsFragment
import ale.ziolo.uhf_rfid.view.ui.items.ItemsViewModel
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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
        holder.status.text = currentItem.status
        if (currentItem.status == "IN") holder.status.setBackgroundResource(R.drawable.round_shape_green)
        if (currentItem.status == "OUT") holder.status.setBackgroundResource(R.drawable.round_shape_red)
        holder.button.setOnClickListener{
            chooseValue(holder)
            changeStatus()
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
        var status = binding.rviTextStatus
        var name = binding.rviTextItemName
        var tag = binding.rviTextItemTag
        var button = binding.rviButtonChangeStatus

    }

    private fun chooseValue(holder: ViewHolder) {
        chosen = list[holder.adapterPosition]
    }

    private fun changeStatus() {
        try {
            val old = chosen
            var newStatus: String = ""
            if (old.status == "IN") newStatus = "OUT"
            if (old.status == "OUT") newStatus = "IN"
            val updated = ItemEntity(old.tag, old.name, newStatus)
            viewModel.updateStatus(updated)
            Log.i("UPDATE", "operation update status successful")

        } catch (e: Exception) {
            Log.e("UPDATE", "operation update status failed")
        }
    }

}
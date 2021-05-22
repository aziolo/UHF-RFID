package ale.ziolo.uhf_rfid.view.ui.itemsFragment

import ale.ziolo.uhf_rfid.databinding.RecyclerViewElementItemsBinding
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
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

    }

    private fun chooseValue(holder: ViewHolder) {
        chosen = list[holder.adapterPosition]
    }

}
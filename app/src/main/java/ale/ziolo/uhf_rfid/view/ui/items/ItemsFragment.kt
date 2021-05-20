package ale.ziolo.uhf_rfid.view.ui.items

import ale.ziolo.uhf_rfid.databinding.FragmentItemsBinding
import ale.ziolo.uhf_rfid.functions.ItemListAdapter
import ale.ziolo.uhf_rfid.model.entities.ItemEntity
import ale.ziolo.uhf_rfid.view.ui.addItem.AddItemActivity
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

@Suppress("DEPRECATION")
class ItemsFragment : Fragment() {

    private lateinit var adapter: ItemListAdapter
    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private val itemViewModel: ItemsViewModel by lazy {
        ViewModelProviders.of(this).get(
            ItemsViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

       // viewModel = ViewModelProvider(this).get(ItemsViewModel::class.java)
        adapter = ItemListAdapter(this)
        binding.itemRecycleView.layoutManager = LinearLayoutManager(context)
        binding.itemRecycleView.adapter = adapter
        itemViewModel.getAllData().observe(this, Observer<List<ItemEntity>> { updatedList ->
            adapter.setItem(updatedList!!)
        })


        binding.buttonAddItem.setOnClickListener {
            val intent = Intent(context, AddItemActivity::class.java)
            startActivity(intent)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
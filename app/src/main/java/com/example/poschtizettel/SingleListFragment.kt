package com.example.poschtizettel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poschtizettel.database.PoschtiDatabase
import com.example.poschtizettel.database.ShoppingItems

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [SingleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingleListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: ListsViewModel
    private var listKey = "0"
    val args: SingleListFragmentArgs by navArgs()
    private lateinit var adapter: ShoppingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listKey = args.listkey


        val application = requireNotNull(this.activity).application

        val dataSource = PoschtiDatabase.getInstance(application).poschtiDatabaseDao

        val viewModelFactory = ListsViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ListsViewModel::class.java)
        val items = viewModel.getItemsOfList(listKey).toMutableList()


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_back_single_list).setOnClickListener {
            findNavController().navigate(R.id.action_singleListFragment_to_FirstFragment)

        }
        view.findViewById<Button>(R.id.button_add_item).setOnClickListener {
            val action = SingleListFragmentDirections.actionSingleListFragmentToAddItemFragment(listkey = listKey)
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.button_clear_list).setOnClickListener {
            if (adapter != null){
                adapter.clearDone()

            }
        }




        val items = viewModel.getItemsOfList(listKey).toMutableList()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_singleList)
        adapter = ShoppingItemAdapter(viewModel, items, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)


    }

    fun handleItemDone(key: String, status: Boolean){
        viewModel.handleItemDone(key, status)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingleListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingleListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}

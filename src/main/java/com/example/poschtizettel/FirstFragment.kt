package com.example.poschtizettel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poschtizettel.database.PoschtiDatabase
import com.example.poschtizettel.database.ShoppingList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var viewModel: ListsViewModel
    lateinit var lists: List<ShoppingList>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireNotNull(this.activity).application

        val dataSource = PoschtiDatabase.getInstance(application).poschtiDatabaseDao

        val viewModelFactory = ListsViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ListsViewModel::class.java)

        val listsObserver = Observer<List<ShoppingList>> { newName ->
            // Update the UI, in this case, a TextView.
            lists = newName


        }

        viewModel.lists.observe(viewLifecycleOwner, Observer { lists ->
            Log.i("FirstFragment", "list length ${lists.size}")
        })

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        val listsObserver = Observer<List<ShoppingList>> { newName ->
            // Update the UI, in this case, a TextView.
            lists = newName


        }

        viewModel.lists.observe(viewLifecycleOwner, Observer { lists ->
            Log.i("FirstFragment", "list length ${lists.size}")
        })
        GlobalScope.launch {
            val versace = viewModel.getDasListsForRealsies()
            Log.i("First", "asdf")

        }


        view.findViewById<TextView>(R.id.textview_first).setText(viewModel.listsString.value.toString())
        var textView = view.findViewById<TextView>(R.id.textview_first)
        val dummyLists = listOf<ShoppingList>(ShoppingList(name = "asdf", listKey = 1), ShoppingList(name = "sdfa", listKey = 2))
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = ShoppingListAdapter(dummyLists)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)



    }





}

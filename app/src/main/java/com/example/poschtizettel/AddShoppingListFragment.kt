package com.example.poschtizettel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.poschtizettel.database.PoschtiDatabase
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddShoppingListFragment : Fragment() {
    private lateinit var viewModel: ListsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        val application = requireNotNull(this.activity).application

        val dataSource = PoschtiDatabase.getInstance(application).poschtiDatabaseDao

        val viewModelFactory = ListsViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ListsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        view.findViewById<Button>(R.id.button_add_list).setOnClickListener {
            val application = requireNotNull(this.activity).application
            val dataSource = PoschtiDatabase.getInstance(application)
            val textField = view.findViewById<TextInputEditText>(R.id.textInputEdit_addList)
            val asdf = textField.text.toString()
            if (asdf != ""){
                    viewModel.onAddList(asdf)
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
            else{
                val snackbar = Snackbar.make(it, "Please enter list name", 600)
                snackbar.show()
            }

        }

    }



}

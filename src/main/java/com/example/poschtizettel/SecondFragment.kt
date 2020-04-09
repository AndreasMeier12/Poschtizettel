package com.example.poschtizettel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.poschtizettel.database.PoschtiDatabase
import com.example.poschtizettel.database.ShoppingList
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
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
            viewModel.onAddList(asdf)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

    }



}

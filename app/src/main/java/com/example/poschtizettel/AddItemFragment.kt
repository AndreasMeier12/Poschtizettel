package com.example.poschtizettel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.poschtizettel.database.PoschtiDatabase
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var listKey = "0"

/**
 * A simple [Fragment] subclass.
 * Use the [AddItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listkey = "-1"
    private lateinit var viewModel: ListsViewModel
    val args: AddItemFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        listkey = args.listkey
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application

        val dataSource = PoschtiDatabase.getInstance(application).poschtiDatabaseDao

        val viewModelFactory = ListsViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ListsViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_confirm_add_item).setOnClickListener {
            val name = view.findViewById<TextInputEditText>(R.id.text_input_item_name).text.toString()
            val quantity = view.findViewById<TextInputEditText>(R.id.text_input_item_quantity).text.toString()
            val shop = view.findViewById<TextInputEditText>(R.id.text_input_item_shop).text.toString()
            clearInputs()
            view.findViewById<TextInputEditText>(R.id.text_input_item_name).requestFocus()
            if(name != ""){
                viewModel.onAddItem(name = name, listNum = listkey, quantity = quantity, shop = shop)

                val snackbar = Snackbar.make(it, "Item " + name + " added", 300)
                snackbar.show()
            } else{
                val snackbar = Snackbar.make(it, "Can't enter empty name", 600)
                snackbar.show()


            viewModel.onAddItem(name = name, listNum = listkey, quantity = quantity, shop = shop)
            }


        }

        view.findViewById<Button>(R.id.button_back_add_item).setOnClickListener {
            navigateBack()
        }

    }

    fun clearInputs(){
        view?.findViewById<TextInputEditText>(R.id.text_input_item_name)?.setText("")
        view?.findViewById<TextInputEditText>(R.id.text_input_item_quantity)?.setText("")
    }

    fun navigateBack(){
        val action = AddItemFragmentDirections.actionAddItemFragmentToSingleListFragment(listkey)
        findNavController().navigate(action)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

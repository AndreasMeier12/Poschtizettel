package com.example.poschtizettel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        view.findViewById<Button>(R.id.button_add_list).setOnClickListener {
            val asdf = view.findViewById<TextInputEditText>(R.id.textInputEdit_addList)
            val listName = asdf.text.toString()
            val newList = ShoppingList(listName)


            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }




    }

    interface addShoppingListInterface{
        fun addShoppingList(newList: ShoppingList)

    }
}

package com.example.poschtizettel

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.poschtizettel.database.*
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TOKEN = "token"
private const val URL = "url"
private const val USERNAME = "username"
private const val GET_URL = "/api/list"
private const val NUKE_URL = "/api/list"
private const val REMOTE_URL = ""



/**
 * A simple [Fragment] subclass.
 * Use the [SyncFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SyncFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: ListsViewModel

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
        val application = requireNotNull(this.activity).application

        val dataSource = PoschtiDatabase.getInstance(application).poschtiDatabaseDao

        val viewModelFactory = ListsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListsViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sync, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val prefs = activity?.getPreferences(Context.MODE_PRIVATE)
        if (prefs != null) {
            prefill(view, prefs)
        }



        view.findViewById<Button>(R.id.button_apply).setOnClickListener {
            val name =
                view.findViewById<AppCompatEditText>(R.id.editTextTextUsername).text.toString()
            val url = view.findViewById<AppCompatEditText>(R.id.editTextTextURL).text.toString()
            val token = view.findViewById<AppCompatEditText>(R.id.editTextTextToken).text.toString()

            with(prefs!!.edit()) {
                putString(USERNAME, name)
                putString(TOKEN, token)
                putString(URL, url)

                apply()

            }
            Log.i("ApplySettings", "applySettings: ${name}. ${url}, ${token}")
            Toast.makeText(context, "Done!", 3).show()

        }


        view.findViewById<Button>(R.id.button_merge).setOnClickListener {
            sync()
        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SyncFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SyncFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun prefill(view: View, prefs: SharedPreferences) {


        val nameField = view.findViewById<AppCompatEditText>(R.id.editTextTextUsername)
        nameField.setText(prefs.getString(USERNAME, "Username"))
        val tokenField = view.findViewById<AppCompatEditText>(R.id.editTextTextToken)
        tokenField.setText(prefs.getString(TOKEN, "Token"))
        val urlField = view.findViewById<AppCompatEditText>(R.id.editTextTextURL)
        urlField.setText(prefs.getString(URL, "URL"))


    }

    fun setToServer() {
        val prefs = activity?.getPreferences(Context.MODE_PRIVATE)
        if (prefs != null) {
            val textView = view!!.findViewById<AppCompatEditText>(R.id.editTextTextURL)
// ...

// Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(context)
            val url = prefs.getString(URL, "URL") + GET_URL
            val token = prefs.getString(TOKEN, "null")
            val tokenVal = getToken()
            val a = GsonBuilder()
            val asdf = a.create()
            val map = mapOf<String, String>(Pair("token", tokenVal))
            val requestBody = asdf.toJson(map)

// Request a string response from the provided URL.
            val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    handleResponse(response)
                    Toast.makeText(context, "Set to server", 3).show()


                },
                Response.ErrorListener {

                        error ->
                    Toast.makeText(context, error.toString(), 3).show()
                    textView.setText("${error.toString()}")
                }){
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }

// Add the request to the RequestQueue.
            queue.add(stringRequest)


        }

    }

    fun sync(){
        val queue = Volley.newRequestQueue(context)
        val prefs = activity?.getPreferences(Context.MODE_PRIVATE)
        if (prefs != null) {

            val url = prefs.getString(URL, "URL") + NUKE_URL
            val listCommands = viewModel.getListCommands()
            val itemCommands = viewModel.getItemCommands()

            val a = GsonBuilder()
            val asdf = a.create()
            val serializedItems = itemCommands.map { asdf.toJson(it) }
            val serializedLists = listCommands.map { asdf.toJson(it) }
            val tokenVal = getToken();




            val map = fillMap(serializedLists, serializedItems, tokenVal)



            val requestBody = asdf.toJson(map)
            val stringReq: StringRequest =
                object : StringRequest(Method.PATCH, url,
                    Response.Listener { response ->
                        // response
                            handleResponse(response)
                            Toast.makeText(context, "Synced successfully", 3).show()

                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(context, error.toString(), 3).show()
                        Log.d("API", "error => $error")
                    }
                ) {
                    override fun getBody(): ByteArray {
                        return requestBody.toByteArray(Charset.defaultCharset())
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
            queue.add(stringReq)

        } else{
            Log.e("SyncFragment", "Could not post: ", )
        }



    }

    private fun fillMap(serializedLists: List<String>, serializedItems : List<String>, token: String?): Map<String, Any> {
        if (token != null){
            return mapOf<String, Any>(Pair("lists", serializedLists), Pair("items", serializedItems), Pair("token", token))
        } else {
            return mapOf<String, Any>(
                Pair("lists", serializedLists),
                Pair("items", serializedItems),
                Pair("token", "null")
            )
        }


    }


    fun parseJson(response: String) : Pair<List<ShoppingList>, List<ShoppingItems>>{
        val jsonObject = JSONTokener(response).nextValue() as JSONObject
        val items = jsonObject.get("items") as JSONArray
        val lists = jsonObject.get("lists") as JSONArray
        val outItems: ArrayList<ShoppingItems> = ArrayList()
        val outLists: ArrayList<ShoppingList> = ArrayList()

        for (i in 0 until items.length()) {
            val cur = items.getJSONObject(i)

            val newItem = ShoppingItems(
                item_key = cur.getString("id"),
                name = cur.getString("name"),
                quantity = cur.getString("quantity"),
                shoppingList = cur.getString("list_id"),
                done = cur.getBoolean("done"),
                shop = cur.getString("shop")
            )
            outItems.add(newItem)
        }
        for (i in 0 until lists.length()) {
            val cur = lists.getJSONObject(i)

            val newList = ShoppingList(
                name = cur.getString("name"),
                listKey = cur.getString("id")
            )
            outLists.add(newList)
        }
        return Pair(outLists, outItems)


    }

    fun handleResponse(response: String){
        try {
            val res = parseJson(response)
            viewModel.setContent(res.first, res.second)
        } catch (error: Exception) {
            Toast.makeText(context, error.toString(), 3).show()
            Log.e("API", "error => $error")

        }


    }

    fun handleFailure(){}


    private fun getToken() : String {
        val prefs = activity?.getPreferences(Context.MODE_PRIVATE)

        var tokenVal = prefs!!.getString("token", "null")
        if (tokenVal == null){
            tokenVal = "null"
        }
        return tokenVal

    }


}
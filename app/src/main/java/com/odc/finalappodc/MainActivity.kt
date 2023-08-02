package com.odc.finalappodc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.odc.finalappodc.adapter.UserAdapter
import com.odc.finalappodc.databinding.ActivityMainBinding
import com.odc.finalappodc.model.MyApplication
import com.odc.finalappodc.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.odc.finalappodc.http.API
import com.odc.finalappodc.http.Requettes
import com.odc.finalappodc.model.UserDAO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    var user_adapter: UserAdapter? = null
    var all_users = ArrayList<User>()

    var request: UserDAO? = null

    var dbLocalIsEmpty = true

    var jaiCliqueDeuxFois = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding?.root)
        request = (application as MyApplication).db.userDao()

        lifecycleScope.launch {
            LoadLocalData()
        }


        //addUser()


    }

    suspend fun insertInLocal(data: List<User>) {

        withContext(Dispatchers.IO) {
            if (dbLocalIsEmpty) {
                for (user in data) {
                    request?.insert(user)
                }
            }
        }

    }

    /*fun addUser() {
        binding?.btnAdd?.setOnClickListener {
            var intent = Intent(applicationContext, AddUser::class.java)
            startActivity(intent)
        }*/

    suspend fun LoadLocalData() {
        request?.select()?.collect {
            if (it.size!=0) {
                initialiserListe(it)
                dbLocalIsEmpty = false
            } else {
                lifecycleScope.launch {
                    getUsers()
                }
            }
        }
    }

    fun initialiserListe(data: List<User> = listOf()) {
        binding?.recycleListeV?.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL, false
        )
        user_adapter = UserAdapter(this, data.sortedByDescending { it.id })
        binding?.recycleListeV?.adapter = user_adapter
    }

    suspend fun getUsers() {

        withContext(Dispatchers.IO) {

            val request = Requettes.getInstance().create(API::class.java)
            val req_result = request.getUsers()

            if (req_result != null) {
                var users = req_result.body()

                runOnUiThread {
                    if (users != null) {
                        all_users.addAll(users?.data)
                        initialiserListe(users.data)

                        lifecycleScope.launch {
                            insertInLocal(users.data)
                        }

                    }
                }
                Log.i("Data", users?.data?.get(0)?.first_name.toString())
            }


        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (jaiCliqueDeuxFois) {
            super.onBackPressed()
            return
        }

        jaiCliqueDeuxFois = true
        Toast.makeText(
            applicationContext, "Veuillez cliqué deux fois pour quitter",
            Toast.LENGTH_LONG
        ).show()

        Handler(Looper.getMainLooper()).postDelayed({
            jaiCliqueDeuxFois = false
        }, 2000)
    }
}



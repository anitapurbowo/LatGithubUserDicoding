package com.example.submission1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val list = ArrayList<GitHubUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGitUser.setHasFixedSize(true)
        binding.rvGitUser.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        getListUser()
        //showRecyclerList()
        //list.addAll(getListUser())
    }

    fun getListUser() { //}: ArrayList<GitHubUser> {
        binding.pBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
//        val listUser = ArrayList<GitHubUser>()

        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 7bb43330cae4ab75e1fecfc0fade6448ee3665b6")
        val url = "https://api.github.com/users/patcon/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                binding.pBar.visibility = View.INVISIBLE
                val result = String(responseBody)

                val jsonArray = JSONArray(result)
                for (i in 0 until jsonArray.length()) {
                    val JSONObject = jsonArray.getJSONObject(i)
                    val dataNama = JSONObject.getString("login").toString()
                    val urldet = "https://api.github.com/users/"+dataNama
                    val dataAvatar = JSONObject.getString("avatar_url").toString()

                    getDetailUser(dataNama, dataAvatar, urldet);
                }
                Log.d("ABC", result)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                binding.pBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDetailUser(dataNama : String, dataAvatar : String, urldet : String) {
        val clientdet = AsyncHttpClient()
        clientdet.addHeader("User-Agent", "request")
        clientdet.addHeader("Authorization", "token 7bb43330cae4ab75e1fecfc0fade6448ee3665b6")

        clientdet.get(urldet, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                Log.d("ABC", urldet)
                val resultdet = String(responseBody)

                val JSONObjectDet = JSONObject(resultdet)
                val dataCompany = JSONObjectDet.getString("company").toString()
                val dataFollowers = JSONObjectDet.getString("followers").toString()
                val githubUsr = GitHubUser(
                    dataNama,
                    dataCompany,dataFollowers,
                    dataAvatar
                )
                list.add(githubUsr)
                Log.d("ABC2", resultdet)
                showRecyclerList()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("ABC2", "GAGAL")
            }

        })
    }
    private fun showRecyclerList() {
        binding.rvGitUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = listUserAdapter(list)
        binding.rvGitUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : listUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GitHubUser) {
                val intentBaru = Intent(this@MainActivity, detUser::class.java)
                intentBaru.putExtra("kirimData", data)
                startActivity(intentBaru)
            }
        })
    }
}
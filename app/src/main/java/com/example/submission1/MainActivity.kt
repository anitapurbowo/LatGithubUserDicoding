package com.example.submission1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
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
    var i=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGitUser.setHasFixedSize(true)
        binding.rvGitUser.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        getListUser("q")

        svUser.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0?.isEmpty()!!) {
                    list.clear()
                    getListUser("q")
                }
                else {
                    list.clear()
                    getListUser(p0)
                    Log.d("ABC",i.toString())
                    i+=1
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    fun getListUser(user : String) { //}: ArrayList<GitHubUser> {
        binding.pBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
//        val listUser = ArrayList<GitHubUser>()

        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 78cf249fba5c703f1cf6fccb05df136fac606ebf")
        val url = "https://api.github.com/search/users?q="+user
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                binding.pBar.visibility = View.INVISIBLE
                var result = String(responseBody)

                val JSONObjectTemp = JSONObject(result)
                val jsonArray = JSONObjectTemp.getJSONArray("items")
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
        clientdet.addHeader("Authorization", "token 78cf249fba5c703f1cf6fccb05df136fac606ebf")

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
                val dataLocation = JSONObjectDet.getString("location").toString()
                val dataRepos = JSONObjectDet.getString("public_repos").toString()
                val githubUsr = GitHubUser(
                    dataNama,
                    dataCompany,
                    dataFollowers,
                    dataRepos,
                    dataAvatar,
                    dataLocation
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


package com.example.submission1.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.dataclass.GitHubUser
import com.example.submission1.R
import com.example.submission1.adapter.folUserAdapter
import com.example.submission1.adapter.listUserAdapter
import com.example.submission1.dataBase.dataBaseHelper
import com.example.submission1.detUser
import com.example.submission1.dataclass.userFav
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class FollowingFragment : Fragment() {
    private var list: ArrayList<GitHubUser> = ArrayList()
    private lateinit var adapter : folUserAdapter

    val DB by lazy { dataBaseHelper }
    private lateinit var context : Any
    private lateinit var userFav : List<userFav>

    var activity: Activity? = getActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = folUserAdapter(list)
        list.clear()
        val dataUser = activity!!.intent.getParcelableExtra<GitHubUser>("kirimData") as GitHubUser
        isiList(dataUser?.nama)
    }

    private fun isiList(user : String) {
        pBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()

        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token "+getString(R.string.key))
        val url = "https://api.github.com/users/$user/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                pBar.visibility = View.INVISIBLE
                var result = String(responseBody)

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
                pBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDetailUser(dataNama : String, dataAvatar : String, urldet : String) {
        val clientdet = AsyncHttpClient()
        clientdet.addHeader("User-Agent", "request")
        clientdet.addHeader("Authorization", "token "+getString(R.string.key))

        clientdet.get(urldet, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                Log.d("ABC", urldet)
                val resultdet = String(responseBody)

                val JSONObjectDet = JSONObject(resultdet)
                val dataCompany = getString(R.string.company) + JSONObjectDet.getString("company").toString()
                val dataFollowers = JSONObjectDet.getString("followers").toString()
                val dataLocation = JSONObjectDet.getString("location").toString()
                val dataRepos = getString(R.string.repository) + JSONObjectDet.getString("public_repos").toString()
                val githubUsr = GitHubUser(
                    dataNama,
                    dataCompany,
                    dataFollowers,
                    dataRepos,
                    dataAvatar,
                    dataLocation, 0
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
        rvUser.layoutManager = LinearLayoutManager(activity)
        val listUserAdapter =
            listUserAdapter(list)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : listUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GitHubUser) {
                val intentBaru = Intent(activity, detUser::class.java)
                intentBaru.putExtra("kirimData", data)
                startActivity(intentBaru)
            }

            override fun onFavUserClicked(data : GitHubUser, position : Int) {
                var user = userFav(
                    data.nama,
                    data.company,
                    data.followers,
                    data.repository,
                    data.image,
                    data.location
                )
                if (data.fav==1) {
                    CoroutineScope(Dispatchers.IO).launch {
                        getContext()?.let { DB.getDatabase(it).UserFavDAO().deleteUser(user) }

                        userFav = DB.getDatabase(context as Context).UserFavDAO().getAllUser()
                        Log.d("abcdataroom", userFav.toString())

                        list[position].fav=0
                        (rvUser.adapter as listUserAdapter).notifyItemChanged(position)
                    }
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        getContext()?.let { DB.getDatabase(it).UserFavDAO().tambahUser(user) }

                        userFav = DB.getDatabase(context as Context).UserFavDAO().getAllUser()
                        Log.d("abcdataroom", userFav.toString())

                        list[position].fav=1
                        (rvUser.adapter as listUserAdapter).notifyItemChanged(position)
                    }
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowersFragment().apply {

            }
    }
}
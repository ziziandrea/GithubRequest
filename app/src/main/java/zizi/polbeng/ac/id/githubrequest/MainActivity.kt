package zizi.polbeng.ac.id.githubrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread
import org.json.JSONObject as JSONObject1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            thread {
                val mgithubinfo = fetchGitHubInfo(txtsearchuser.text.toString())
                val jsonreader = JSONObject1(mgithubinfo)
                runOnUiThread {
                    val id = jsonreader.getString("id")
                    val name = jsonreader.getString("name")
                    val url = jsonreader.getString("url")
                    val blog = jsonreader.getString("blog")
                    val bio = jsonreader.getString("bio")
                    tvInfo.text = "${id}\n${name}\n${url}\n${blog}\n${bio}"
                }
            }
        }
    }

    private fun fetchGitHubInfo(login_id: String): String {
        val url = "https://api.github.com/users/$login_id"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val bodystr = response.body().string() // this can be consumed val onlyonly once
        return bodystr
    }
    override fun onResume() {
        super.onResume()
        txtsearchuser.setText("")
        txtsearchuser.setHint("Enter GitHub username")
    }
}

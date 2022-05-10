package com.jihoonyoon.motionlayoutexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.jihoonyoon.motionlayoutexample.adapter.VideoAdapter
import com.jihoonyoon.motionlayoutexample.databinding.ActivityMainBinding
import com.jihoonyoon.motionlayoutexample.dto.VideoDto
import com.jihoonyoon.motionlayoutexample.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PlayerFragment())
            .commit()

        adapter = VideoAdapter(callback = { url, title ->
            // fragment 에 있는 함수 가져오기
            supportFragmentManager.fragments.find { it is PlayerFragment }?.let {
                (it as PlayerFragment).play(url, title)
            }
        })

        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.adapter = adapter
        getVideoList()
    }

    private fun getVideoList(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also {

            it.getVideoList()
                .enqueue(object : Callback<VideoDto> {
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if (response.isSuccessful.not()) {
                            Log.d(TAG, "onResponse: fail")
                            return
                        }

                        response.body()?.let { dto ->
                            Log.d(TAG, "onResponse: $dto")
                            adapter.submitList(dto.videos)
                        }
                    }

                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }

    }



    companion object {
        private const val TAG = "MainActivity"
    }
}
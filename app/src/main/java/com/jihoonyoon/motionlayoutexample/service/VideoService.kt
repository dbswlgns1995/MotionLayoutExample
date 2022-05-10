package com.jihoonyoon.motionlayoutexample.service

import com.jihoonyoon.motionlayoutexample.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {
    @GET("/v3/2360400b-aad5-43a1-8707-9ba38d8b3012")
    fun getVideoList(): Call<VideoDto>
}
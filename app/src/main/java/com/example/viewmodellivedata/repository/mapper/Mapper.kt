package com.example.viewmodellivedata.repository.mapper

import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.model.News
import com.example.viewmodellivedata.utils.Constants.Companion.ERROR_SERVER
import com.example.viewmodellivedata.utils.CustomResponse
import com.example.viewmodellivedata.utils.LocalException
import retrofit2.Response

object Mapper {
    fun map(newsDataResponse: Response<News>): CustomResponse<List<Article?>, LocalException> {
        return if (newsDataResponse.isSuccessful && newsDataResponse.code() == 200){
            CustomResponse.Success(newsDataResponse.body()?.articles ?: arrayListOf())
        }
        else CustomResponse.Failure(LocalException(ERROR_SERVER))
    }
}
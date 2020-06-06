package com.bnvs.metaler.data.categories.source.remote

import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.network.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class CategoriesRemoteDataSourceImpl(
    private val retrofitClient: RetrofitInterface
) : CategoriesRemoteDataSource {

    override fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}
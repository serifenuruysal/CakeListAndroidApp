package com.androidapp.cakelistapp.data.repository

import com.androidapp.cakelistapp.data.model.CakeModel
import com.androidapp.cakelistapp.domain.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Nur Uysal on 06/12/2021.
 */

interface CakeListRepository {
    suspend fun getCakeList(): ApiResponse<List<CakeModel>>
}

@Singleton
class CakeListRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CakeListRepository {

    /**
     * This is a "regular" suspending function, which means the caller must
     * be in a coroutine. The repository is not responsible for starting or
     * stopping coroutines since it doesn't have a natural lifecycle to cancel
     * unnecessary work.
     *
     * This *may* be called from Dispatchers.Main and is main-safe because
     * Retrofit will take care of main-safety for us.
     */

    //TODO custom error handling implemetation
    override suspend fun getCakeList(): ApiResponse<List<CakeModel>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCakeList()
            if (response.isSuccessful)
                response.body()?.let {
                    ApiResponse.Success(it)
                } ?: ApiResponse.Error("Response is null!")
            else
                ApiResponse.Error("Error occurred during fetching cakes!")
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "Exception!")
        }

    }
}
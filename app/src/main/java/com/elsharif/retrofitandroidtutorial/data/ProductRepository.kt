package com.elsharif.retrofitandroidtutorial.data

import com.elsharif.retrofitandroidtutorial.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProductsList():Flow<Result<List<Product>>>
}
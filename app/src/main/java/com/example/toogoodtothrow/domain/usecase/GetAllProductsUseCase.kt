package com.example.toogoodtothrow.domain.usecase

import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(): Flow<List<Product>> = repository.getAllProducts()
}
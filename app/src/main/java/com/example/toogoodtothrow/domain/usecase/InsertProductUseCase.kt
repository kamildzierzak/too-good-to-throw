package com.example.toogoodtothrow.domain.usecase

import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.domain.repository.ProductRepository
import javax.inject.Inject

class InsertProductUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        repository.insertProduct(product)
    }
}
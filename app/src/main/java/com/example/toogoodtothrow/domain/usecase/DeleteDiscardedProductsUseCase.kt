package com.example.toogoodtothrow.domain.usecase

import com.example.toogoodtothrow.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteDiscardedProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke() {
        repository.deleteDiscardedProducts()
    }
}
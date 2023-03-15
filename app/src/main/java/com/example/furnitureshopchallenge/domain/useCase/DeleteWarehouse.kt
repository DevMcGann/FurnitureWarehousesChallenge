package com.example.furnitureshopchallenge.domain.useCase

import com.example.furnitureshopchallenge.domain.repository.WarehouseRepository

class DeleteWarehouse(
    private val repository: WarehouseRepository
) {
    suspend operator fun invoke(code: String) = repository.deleteWarehoouseFromFirestore(code)
}
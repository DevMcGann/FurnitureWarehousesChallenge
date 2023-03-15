package com.example.furnitureshopchallenge.domain.useCase

import com.example.furnitureshopchallenge.domain.model.Warehouse
import com.example.furnitureshopchallenge.domain.repository.WarehouseRepository

class AddWarehouse(
    private val repository: WarehouseRepository
) {
    suspend operator fun invoke(
        warehouse: Warehouse
    ) = repository.addWarehouseToFirestore(warehouse =warehouse)
}
package com.example.furnitureshopchallenge.domain.useCase

import com.example.furnitureshopchallenge.domain.repository.WarehouseRepository

class GetWarehouses(
    private val repo: WarehouseRepository
) {
    operator fun invoke() = repo.getWarehousesFromFirestore()
}
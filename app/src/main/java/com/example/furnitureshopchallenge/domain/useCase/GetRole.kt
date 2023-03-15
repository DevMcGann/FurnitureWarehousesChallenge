package com.example.furnitureshopchallenge.domain.useCase

import com.example.furnitureshopchallenge.domain.repository.WarehouseRepository

class GetRole(
    private val repo:WarehouseRepository
) {
     operator fun invoke(user:String) = repo.getUserRoleFromFirebase(user)
}
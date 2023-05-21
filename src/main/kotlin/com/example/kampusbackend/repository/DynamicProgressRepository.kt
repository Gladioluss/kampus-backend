package com.example.kampusbackend.repository

import com.example.kampusbackend.entity.DynamicProgressEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DynamicProgressRepository : JpaRepository<DynamicProgressEntity, Long> {
}
package com.example.rsqtech.domain.doctor.repository

import com.example.rsqtech.domain.doctor.model.Doctor
import com.example.rsqtech.domain.organisation.model.Organisation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DoctorRepository : JpaRepository<Doctor, Long> {
    fun findByName(doctorName: String): Doctor
    fun findByOrganisation(organisation: Organisation): List<Doctor>
}
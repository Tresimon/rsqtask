package com.example.rsqtech.domain.patient.dto.form

class PatientFormDto(
    val name: String,
    val surname: String,
    val address: String,
    val organisationId: Long? = null
)

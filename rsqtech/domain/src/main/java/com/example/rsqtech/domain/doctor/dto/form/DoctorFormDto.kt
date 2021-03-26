package com.example.rsqtech.domain.doctor.dto.form

class DoctorFormDto(
    val name: String,
    val surname: String,
    val major: String,
    val organisationId: Long? = null
)

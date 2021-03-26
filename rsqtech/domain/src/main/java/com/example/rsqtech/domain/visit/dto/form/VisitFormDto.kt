package com.example.rsqtech.domain.visit.dto.form

import com.example.rsqtech.domain.visit.dto.validator.IsDoctorAndPatientFromSameOrganisation
import com.example.rsqtech.domain.visit.dto.validator.IsVisitDataWithDoctorAlreadyBooked
import java.time.LocalDateTime

@IsVisitDataWithDoctorAlreadyBooked
@IsDoctorAndPatientFromSameOrganisation
class VisitFormDto (
    val date: LocalDateTime,
    val patientId: Long,
    val doctorId: Long
)
package com.example.rsqtech.domain.visit.dto.validator

import javax.validation.ConstraintValidator
import com.example.rsqtech.domain.visit.dto.form.VisitFormDto
import org.springframework.beans.factory.annotation.Autowired
import com.example.rsqtech.domain.visit.repository.VisitRepository
import javax.validation.ConstraintValidatorContext

class IsVisitDataWithDoctorAlreadyBookedValidator :
    ConstraintValidator<IsVisitDataWithDoctorAlreadyBooked?, VisitFormDto> {
    @Autowired
    private val visitRepository: VisitRepository? = null
    override fun isValid(visitFormDto: VisitFormDto, context: ConstraintValidatorContext): Boolean {
        return !visitRepository!!.findByDateAndDoctorId(visitFormDto.date, visitFormDto.doctorId).isPresent
    }
}
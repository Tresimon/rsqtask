package com.example.rsqtech.domain.visit.dto.validator

import javax.validation.Constraint
import kotlin.reflect.KClass
import javax.validation.Payload

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention
@Constraint(validatedBy = [IsVisitDataWithDoctorAlreadyBookedValidator::class])
@MustBeDocumented
annotation class IsVisitDataWithDoctorAlreadyBooked(
    val message: String = "{IsVisitDataWithDoctorAlreadyBooked.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
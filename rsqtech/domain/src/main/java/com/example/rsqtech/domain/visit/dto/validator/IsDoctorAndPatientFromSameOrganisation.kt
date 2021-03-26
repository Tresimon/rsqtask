package com.example.rsqtech.domain.visit.dto.validator

import javax.validation.Constraint
import kotlin.reflect.KClass
import javax.validation.Payload

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention
@Constraint(validatedBy = [IsDoctorAndPatientFromSameOrganisationValidator::class])
@MustBeDocumented
annotation class IsDoctorAndPatientFromSameOrganisation(
    val message: String = "{IsDoctorAndPatientFromSameOrganisation.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
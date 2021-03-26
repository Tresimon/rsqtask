package com.example.rsqtech.domain.doctor.controller

import com.example.rsqtech.domain.doctor.dto.form.DoctorFormDto
import com.example.rsqtech.domain.doctor.model.Doctor
import com.example.rsqtech.domain.doctor.service.DoctorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/doctors")
class DoctorController(private val doctorService: DoctorService) {

    @GetMapping
    fun getDoctors(): List<Doctor> =
        doctorService.getDoctors()

    @GetMapping("/{id}")
    fun getDoctorById(@PathVariable("id") doctorId: Long): ResponseEntity<Doctor> =
        doctorService.getDoctorById(doctorId)

    @RequestMapping(method = [RequestMethod.GET], value = ["/organisation/{organisationId}"])
    fun getDoctorsByOrganisationId(@PathVariable("organisationId") organisationId: Long): List<Doctor> =
        doctorService.getDoctorsByOrganisationId(organisationId)

    @PostMapping
    fun createDoctor(@Valid @RequestBody doctorFormDto: DoctorFormDto): ResponseEntity<Doctor> =
        doctorService.createDoctor(doctorFormDto)

    @PutMapping("/{id}")
    fun updateDoctorById(
        @PathVariable("id") doctorId: Long,
        @Valid @RequestBody newDoctor: DoctorFormDto
    ): ResponseEntity<Doctor> =
        doctorService.updateDoctor(doctorId, newDoctor)

    @DeleteMapping("/{id}")
    fun deleteDoctor(@PathVariable("id") doctorId: Long): ResponseEntity<Void> =
        doctorService.deleteDoctor(doctorId)
}
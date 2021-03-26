package com.example.rsqtech.domain.visit.controller

import com.example.rsqtech.domain.visit.dto.form.VisitFormDto
import com.example.rsqtech.domain.visit.service.VisitService
import com.example.rsqtech.domain.visit.model.Visit
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/visits")
class VisitController (private val visitService: VisitService) {

    @GetMapping
    fun getVisits(): List<Visit> =
        visitService.getVisits()

    @GetMapping("/{id}")
    fun getVisitById(@PathVariable("id") visitId: Long): ResponseEntity<Visit> =
        visitService.getVisitById(visitId)

    @RequestMapping(method = [RequestMethod.GET], value = ["/organisation/{organisationId}"])
    fun getVisitsByOrganisationId(@PathVariable("organisationId") organisationId: Long): List<Visit> =
        visitService.getVisitsByOrganisationId(organisationId)

    @GetMapping("/patient/{id}")
    fun getVisitsByPatientId(@PathVariable("id") patientId: Long): List<Visit> =
        visitService.getVisitsByPatientId(patientId)

    @PostMapping
    fun createVisit(@Valid @RequestBody visitForm: VisitFormDto): ResponseEntity<Visit> =
        visitService.createVisit(visitForm)

    @PutMapping("/{id}")
    fun updateVisitById(
        @PathVariable("id") visitId: Long,
        @Valid @RequestBody newVisit: VisitFormDto
    ): ResponseEntity<Visit> =
        visitService.updateVisit(visitId, newVisit)

    @DeleteMapping("/{id}")
    fun deleteVisit(@PathVariable("id") visitId: Long): ResponseEntity<Void> =
        visitService.deleteVisit(visitId)
}
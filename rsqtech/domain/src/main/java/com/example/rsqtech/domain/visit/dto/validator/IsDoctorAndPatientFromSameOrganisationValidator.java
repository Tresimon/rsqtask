package com.example.rsqtech.domain.visit.dto.validator;

import com.example.rsqtech.domain.doctor.model.Doctor;
import com.example.rsqtech.domain.doctor.repository.DoctorRepository;
import com.example.rsqtech.domain.organisation.model.Organisation;
import com.example.rsqtech.domain.patient.model.Patient;
import com.example.rsqtech.domain.patient.repository.PatientRepository;
import com.example.rsqtech.domain.visit.dto.form.VisitFormDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsDoctorAndPatientFromSameOrganisationValidator implements ConstraintValidator<IsDoctorAndPatientFromSameOrganisation, VisitFormDto> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public boolean isValid(VisitFormDto visitFormDto, ConstraintValidatorContext context) {
        Organisation patientOrganisation = getOrganisationOfPatient(visitFormDto.getPatientId());
        Organisation doctorOrganisation = getOrganisationOfDoctor(visitFormDto.getDoctorId());
        return patientOrganisation == doctorOrganisation;
    }

    private Organisation getOrganisationOfPatient(Long patientId){
        return patientRepository.findById(patientId).map(Patient::getOrganisation)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't found patient with id: " + patientId));
    }

    private Organisation getOrganisationOfDoctor(Long doctorId){
        return doctorRepository.findById(doctorId).map(Doctor::getOrganisation)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't found doctor with id: " + doctorId));
    }
}

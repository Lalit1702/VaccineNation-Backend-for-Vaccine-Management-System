package com.example.VaccineNation.service;

import com.example.VaccineNation.Enum.VaccineBrand;
import com.example.VaccineNation.exception.PatientNotFoundException;
import com.example.VaccineNation.model.Dose;
import com.example.VaccineNation.model.Patient;
import com.example.VaccineNation.repository.DoseRepository;
import com.example.VaccineNation.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.UUID;

@Service
public class DoseService {

    @Autowired
    DoseRepository doseRepository;

    @Autowired
    PatientRepository patientRepository;

    @PostMapping("/vaccinate")
    public Dose addDose(int patientId, VaccineBrand vaccineBrand) {
        // check if the patientId is valid or not
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if(patientOptional.isEmpty()){
            throw new PatientNotFoundException("Invalid Patient Entry");
        }
        Patient patient = patientOptional.get();

        if(patient.isVaccinated()){
            throw new RuntimeException("Patient is Already Vaccinated");
        }

        patient.setVaccinated(true);

        Dose dose = new Dose();
        dose.setSerialNumber(String.valueOf(UUID.randomUUID()));
        dose.setVaccineBrand(vaccineBrand);
        dose.setPatient(patient); //setting the FK

        patientRepository.save(patient);

        return doseRepository.save(dose);
    }
}

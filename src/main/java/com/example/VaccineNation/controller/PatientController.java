package com.example.VaccineNation.controller;

import com.example.VaccineNation.Enum.Gender;
import com.example.VaccineNation.dto.request.PatientRequest;
import com.example.VaccineNation.dto.response.PatientResponse;
import com.example.VaccineNation.model.Patient;
import com.example.VaccineNation.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity addPatient(@RequestBody PatientRequest patientRequest){
        try {
            PatientResponse patientResponse = patientService.addPatient(patientRequest);
            return new ResponseEntity(patientResponse, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity("Invalid request",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public PatientResponse getPatient(@RequestParam("id") int id){
        return patientService.getPatient(id);

    }

    //get all the patients of a particular gender
    @GetMapping("/get/gender/{gender}")
    public List<PatientResponse> getAllSameGenderPatients(@PathVariable("gender") Gender gender){
        return patientService.getAllSameGenderPatients(gender);
    }
    
    //get all the vacconated patients above age 30

    //get all unvaccinated Males/FEMALES

    // change the vaccinated status for all the patients 1->0 0->1
}

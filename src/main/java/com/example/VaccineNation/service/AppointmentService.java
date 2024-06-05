package com.example.VaccineNation.service;

import com.example.VaccineNation.Enum.AppointmentStatus;
import com.example.VaccineNation.dto.response.AppointmentResponse;
import com.example.VaccineNation.dto.response.PatientResponse;
import com.example.VaccineNation.exception.DoctorNotFoundException;
import com.example.VaccineNation.exception.PatientNotFoundException;
import com.example.VaccineNation.model.Appointment;
import com.example.VaccineNation.model.Doctor;
import com.example.VaccineNation.model.Patient;
import com.example.VaccineNation.repository.AppointmentRepository;
import com.example.VaccineNation.repository.DoctorRepository;
import com.example.VaccineNation.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    public AppointmentResponse bookAppointment(int patientId, int doctorId) {
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if(patientOptional.isEmpty()){
            throw new PatientNotFoundException("Invalid patient Id");
        }

        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException("Invalid Doctor Id");
        }

        Patient patient = patientOptional.get();
        Doctor doctor = doctorOptional.get();

        //book the appointment
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(String.valueOf(UUID.randomUUID()));
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        Appointment savedAppointment =  appointmentRepository.save(appointment);

        // convert model to response dta
        AppointmentResponse appointmentResponse = new AppointmentResponse();
        appointmentResponse.setAppointmentId(savedAppointment.getAppointmentId());
        appointmentResponse.setStatus(savedAppointment.getStatus());
        appointmentResponse.setDateOfAppointment(savedAppointment.getDateOfAppointment());
        appointmentResponse.setDoctorName(savedAppointment.getDoctor().getName());

        Patient savedPatient = savedAppointment.getPatient();
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setName(savedPatient.getName());
        patientResponse.setEmailId(savedPatient.getEmailId());
        patientResponse.setVaccination(savedPatient.isVaccinated());
        appointmentResponse.setPatientResponse(patientResponse);

        return appointmentResponse;
    }
}

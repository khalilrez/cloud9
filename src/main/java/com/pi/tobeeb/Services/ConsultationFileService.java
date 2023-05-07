package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.*;
import com.pi.tobeeb.Enums.AppointmentStatus;
import com.pi.tobeeb.Exceptions.ResourceNotFoundException;
import com.pi.tobeeb.Repositorys.*;
import com.pi.tobeeb.Utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ConsultationFileService {

    @Autowired
    private ConsultationFileRepository consultationFileRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

     @Autowired
     private PrescriptionRepository prescriptionRepository;

     @Autowired
     private TestRepository testRepository;

     @Autowired
     private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ConsultationFileService.class);


    @Value("${app.upload.dir}")
    private String uploadDir;

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // run once per day
    //@Scheduled(fixedRate = 5000) // 30 seconds = 30,000 milliseconds
    public void checkExpiredAppointments() {
        List<Appointment> expiredAppointments = appointmentRepository.findByDateStartLessThanEqual(LocalDate.now());
        logger.info("IN SCHEDULED METHOD ( CONSULTATION FILE SERVICE");
        for (Appointment appointment : expiredAppointments) {
            if(appointment.getStatus().equals(AppointmentStatus.COMPLETE) && appointment.getConsultationFile() == null){
            ConsultationFile consultationFile = new ConsultationFile();
            //CREATE PRESCRIPTION & SAVE
            Prescription prescription = new Prescription();
            prescription.setCreationDate(LocalDate.now());
            prescriptionRepository.save(prescription);

            //SET CONSULTATION FILE ATTRIBUTES & SAVE
            consultationFile.setAppointment(appointment);
            consultationFile.setPrescription(prescription);
            consultationFileRepository.save(consultationFile);

            appointment.setConsultationFile(consultationFile);
            appointmentRepository.save(appointment);
            }


        }
    }


    public Test addNewTestToConsultationFile(String testName,Long fileId){
        ConsultationFile consultationFile = consultationFileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("Consultation File", "id", fileId));
        Test test = new Test();
        test.setFile(consultationFile);
        test.setTestName(testName);
        return  testRepository.save(test);
    }
    public Test getTestById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    public List<Test> getAllTestsByUserId(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        List<Test> tests = testRepository.findAllByFile_Appointment_Patient(user);
        return tests;

    }

    public void deleteTestById(Long id) {
        testRepository.deleteById(id);
    }

    public void deleteAllTests() {
        testRepository.deleteAll();
    }

    public void addImageToTest(Long id, MultipartFile image) throws IOException {
        Test test = testRepository.findById(id).orElse(null);
        if (test != null) {
            test.setImage(ImageUtils.compressImage(image.getBytes()));
            testRepository.save(test);
        }
    }

    public byte[] getTestImage(Long id) throws IOException {
        Test test = testRepository.findById(id).orElse(null);
        if (test != null) {
            return ImageUtils.decompressImage(test.getImage());
        }
        return null;
    }



    public ConsultationFile getConsultationFileByAppointment(Long id) {
        return consultationFileRepository.findByAppointment_IdAppointment(id)
                .orElseThrow(() -> new EntityNotFoundException("ConsultationFile not found with id: " + id));
    }

    public List<ConsultationFile> getAllConsultationFiles() {
        return consultationFileRepository.findAll();
    }

    public List<ConsultationFile> getConsultationFilesByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        List<ConsultationFile> consultationFiles = consultationFileRepository.findAllByAppointment_Patient(user);
        return consultationFiles;

    }


    public Prescription getPrescriptionById(Long id){
        return prescriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription", "id", id));
    }
}
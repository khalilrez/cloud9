package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.ConsultationFile;
import com.pi.tobeeb.Entities.Prescription;
import com.pi.tobeeb.Entities.Test;
import com.pi.tobeeb.Exceptions.ResourceNotFoundException;
import com.pi.tobeeb.Repositorys.AppointmentRepository;
import com.pi.tobeeb.Repositorys.ConsultationFileRepository;
import com.pi.tobeeb.Repositorys.PrescriptionRepository;
import com.pi.tobeeb.Repositorys.TestRepository;
import com.pi.tobeeb.Utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // run once per day
    public void checkExpiredAppointments() {
        List<Appointment> expiredAppointments = appointmentRepository.findByDateStartLessThanEqual(LocalDate.now());

        for (Appointment appointment : expiredAppointments) {
            ConsultationFile consultationFile = new ConsultationFile();
            //CREATE PRESCRIPTION & SAVE
            Prescription prescription = new Prescription();
            prescription.setConsultationFile(consultationFile);
            prescription.setCreationDate(LocalDate.now());
            prescriptionRepository.save(prescription);

            //SET CONSULTATION FILE ATTRIBUTES & SAVE
            consultationFile.setAppointment(appointment);
            consultationFile.setPrescription(prescription);
            consultationFileRepository.save(consultationFile);
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

}
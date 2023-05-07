package com.pi.tobeeb.Controllers;

import com.pi.tobeeb.Dto.ConsultationFileDTO;
import com.pi.tobeeb.Dto.PrescriptionDTO;
import com.pi.tobeeb.Dto.TestDTO;
import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.ConsultationFile;
import com.pi.tobeeb.Entities.Prescription;
import com.pi.tobeeb.Entities.Test;

import com.pi.tobeeb.Repositorys.ConsultationFileRepository;
import com.pi.tobeeb.Services.ConsultationFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/consultation-files")
public class ConsultationFileController {

    @Autowired
    private ConsultationFileService consultationFileService;

    @Autowired
    private ConsultationFileRepository consultationFileRepository;

    private static final Logger logger = LoggerFactory.getLogger(ConsultationFileController.class);




    //Get Image of Test , takes id of a "Test" as PathVariable  , Returns Byte[]
    @GetMapping("/test/image/{testId}")
    public ResponseEntity<byte[]> getTestImage(@PathVariable Long testId) throws IOException {
        byte[] imageBytes = consultationFileService.getTestImage(testId);
        if (imageBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable(value = "id") Long testId) {
        Test test = consultationFileService.getTestById(testId);
        if (test == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(test);
    }

    @PostMapping("/{id}/add-test/{testName}")
    public ResponseEntity<TestDTO> addNewTestToConsultationFile(@PathVariable Long id, @PathVariable("testName") String testName) {
        Test test = consultationFileService.addNewTestToConsultationFile(testName, id);
        TestDTO testDTO = new TestDTO();
        testDTO.setIdTest(test.getIdTest());
        testDTO.setTestName(test.getTestName());
        return new ResponseEntity<>(testDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/test/{id}")
    public ResponseEntity<?> deleteTestById(@PathVariable Long id) {
        consultationFileService.deleteTestById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tests")
    public ResponseEntity<?> deleteAllTests() {
        consultationFileService.deleteAllTests();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test/{id}/image")
    public ResponseEntity<?> addImageToTest(@PathVariable Long id, @RequestParam("file") MultipartFile image) throws IOException {
        consultationFileService.addImageToTest(id, image);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity<ConsultationFileDTO> getConsultationFileByAppointment(@PathVariable int id) {
        ConsultationFile consultationFile =  consultationFileService.getConsultationFileByAppointment(id);
        ConsultationFileDTO consultationFileDTO = consultationFileModelToDTO(consultationFile);
        return new ResponseEntity<>(consultationFileDTO,HttpStatus.OK);
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConsultationFileDTO>> getConsultationFilesByUserId(@PathVariable(value = "userId") Long id){
        List<ConsultationFile> consultationFiles = consultationFileService.getConsultationFilesByUserId(id);
        return new ResponseEntity<>(consultationFilesModelToDTO(consultationFiles), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<List<ConsultationFileDTO>> getAllConsultationFiles() {
        List<ConsultationFile> consultationFiles = consultationFileService.getAllConsultationFiles();
        return new ResponseEntity<>(consultationFilesModelToDTO(consultationFiles), HttpStatus.OK);
    }

    @GetMapping("/prescription/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionById(@PathVariable Long id){
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        Prescription prescription = consultationFileService.getPrescriptionById(id);
        prescriptionDTO.setPrescriptionId(prescription.getIdPrescription());

        prescriptionDTO.setContent(prescription.getContent());
        prescriptionDTO.setDate(prescription.getCreationDate());
        return  new ResponseEntity<>(prescriptionDTO,HttpStatus.OK);
    }


    @PutMapping("/prescription/{id}")
    public ResponseEntity<ConsultationFileDTO> updatePrescription(@PathVariable int id, @RequestBody String content){
        ConsultationFile consultationFile = consultationFileService.getConsultationFileByAppointment(id);
        consultationFile.getPrescription().setContent(content);
        consultationFileRepository.save(consultationFile);
        ConsultationFileDTO consultationFileDTO = consultationFileModelToDTO(consultationFile);
        return new ResponseEntity<>(consultationFileDTO,HttpStatus.OK);
    }
    @PutMapping("/note/{id}")
    public ResponseEntity<ConsultationFileDTO> updateNote(@PathVariable int id, @RequestBody String content){
        logger.info("THE VARIABLES CONTAINS : "+ content);
        ConsultationFile consultationFile = consultationFileService.getConsultationFileByAppointment(id);
        consultationFile.setDoctorNotes(content);
        consultationFileRepository.save(consultationFile);
        ConsultationFileDTO consultationFileDTO = consultationFileModelToDTO(consultationFile);
        return new ResponseEntity<>(consultationFileDTO,HttpStatus.OK);
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<List<TestDTO>> getAllTestsByUserId(@PathVariable Long id){
            List<Test> tests = consultationFileService.getAllTestsByUserId(id);
            List<TestDTO> testDTOS = new ArrayList<>();
        for (Test test : tests) {
            TestDTO testDTO = new TestDTO();
            testDTO.setIdTest(test.getIdTest());
            testDTO.setTestName(test.getTestName());
            testDTOS.add(testDTO);
        }
        return new ResponseEntity<>(testDTOS,HttpStatus.OK);
    }

    private List<ConsultationFileDTO> consultationFilesModelToDTO(List<ConsultationFile> consultationFiles) {
        List<ConsultationFileDTO> consultationFileDTOS = new ArrayList<>();
        for (ConsultationFile consultationFile : consultationFiles) {
            ConsultationFileDTO consultationFileDTO = consultationFileModelToDTO(consultationFile);
            consultationFileDTOS.add(consultationFileDTO);
        }
        return consultationFileDTOS;
    }

    private ConsultationFileDTO consultationFileModelToDTO(ConsultationFile consultationFile) {
        ConsultationFileDTO consultationFileDTO = new ConsultationFileDTO();
        consultationFileDTO.setIdFile(consultationFile.getIdFile());
        consultationFileDTO.setDoctorNotes(consultationFile.getDoctorNotes());
        consultationFileDTO.setAppointmentId((long) consultationFile.getAppointment().getIdAppointment());
        consultationFileDTO.setPrescriptionId(consultationFile.getPrescription().getIdPrescription());
        consultationFileDTO.setAppointmentDate(consultationFile.getAppointment().getDateStart());
        return  consultationFileDTO;
    }

}
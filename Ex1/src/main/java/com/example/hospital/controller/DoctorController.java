package com.example.hospital.controller;

import com.example.hospital.dto.DoctorDTO;
import com.example.hospital.model.Doctor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final Map<Long, Doctor> doctorStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public DoctorController() {
        resetStorage();
    }

    public synchronized void resetStorage() {
        doctorStorage.clear();
        idGenerator.set(1);
        Doctor d1 = Doctor.builder()
                .id(idGenerator.getAndIncrement())
                .name("BS. Nguyễn Văn A")
                .specialty("Tim mạch")
                .email("nguyenvana@hospital.com")
                .phone("0987654321")
                .build();
        Doctor d2 = Doctor.builder()
                .id(idGenerator.getAndIncrement())
                .name("BS. Trần Thị B")
                .specialty("Nhi khoa")
                .email("tranthib@hospital.com")
                .phone("0912345678")
                .build();
        doctorStorage.put(d1.getId(), d1);
        doctorStorage.put(d2.getId(), d2);
    }

    /**
     * 1. Lấy danh sách toàn bộ bác sĩ
     * HTTP Method: GET
     * URL: /api/v1/doctors
     */
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(new ArrayList<>(doctorStorage.values()));
    }

    /**
     * 2. Lấy thông tin chi tiết một bác sĩ theo ID
     * HTTP Method: GET
     * URL: /api/v1/doctors/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") Long id) {
        Doctor doctor = doctorStorage.get(id);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(doctor);
    }

    /**
     * 3. Thêm mới một bác sĩ
     * HTTP Method: POST
     * URL: /api/v1/doctors
     */
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody DoctorDTO dto) {
        Long newId = idGenerator.getAndIncrement();
        Doctor doctor = Doctor.builder()
                .id(newId)
                .name(dto.getName())
                .specialty(dto.getSpecialty())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        doctorStorage.put(newId, doctor);
        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }

    /**
     * 4. Cập nhật thông tin bác sĩ
     * HTTP Method: PUT
     * URL: /api/v1/doctors/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable("id") Long id,
            @Valid @RequestBody DoctorDTO dto) {
        if (!doctorStorage.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Doctor updated = Doctor.builder()
                .id(id)
                .name(dto.getName())
                .specialty(dto.getSpecialty())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        doctorStorage.put(id, updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * 5. Xóa bác sĩ khỏi hệ thống
     * HTTP Method: DELETE
     * URL: /api/v1/doctors/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("id") Long id) {
        if (!doctorStorage.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        doctorStorage.remove(id);
        return ResponseEntity.noContent().build();
    }
}

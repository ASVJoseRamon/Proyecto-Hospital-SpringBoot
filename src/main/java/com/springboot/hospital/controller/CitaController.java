package com.springboot.hospital.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.MedicoMapper;
import com.springboot.hospital.mapper.PacienteMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.StatusCita;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.MedicoService;
import com.springboot.hospital.service.PacienteService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    private final MedicoService medicoService;

    private final PacienteService pacienteService;

    private final CitaMapper citaMapper;

    private final MedicoMapper medicoMapper;

    private final PacienteMapper pacienteMapper;

    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarCitas() {
        List<CitaDTO> citas = citaService.getAllCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> listarCitaPorId(@PathVariable Long id) {
        Optional<CitaDTO> citaDTOOptional = citaService.getCitaById(id);
        return citaDTOOptional.map(cita -> new ResponseEntity<>(cita,HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/{idPaciente}/{idMedico}")
    public ResponseEntity<CitaDTO> guardarCita(@RequestBody CitaDTO citaDTO, @PathVariable Long idPaciente, @PathVariable Long idMedico) throws ParseException {
        Cita newCita = citaService.createCita(citaDTO, idPaciente, idMedico);

        if(newCita == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CitaDTO newCitaDTO = citaMapper.toDTO(newCita);
        return new ResponseEntity<>(citaDTO,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizarCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO) throws ParseException {
        CitaDTO citaUpdate = citaService.updateCita(id, citaDTO);
        if(citaUpdate != null) {
            return ResponseEntity.ok(citaUpdate);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id){
        citaService.deleteCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<CitaDTO> listarCitasPorPacienteId(@PathVariable Long pacienteId) {
        return citaService.getCitasByPacienteId(pacienteId);
    }
    
    @GetMapping("/medico/{medicoId}")
    public List<CitaDTO> listarCitasPorMedicoId(@PathVariable Long medicoId) {
        return citaService.getCitasByMedicoId(medicoId);
    }

    @GetMapping("/status/{statusCita}")
    public List<CitaDTO> listarCitasPorStatus(@PathVariable StatusCita statusCita) {
        return citaService.getCitasByStatusCita(statusCita);
    }
}    

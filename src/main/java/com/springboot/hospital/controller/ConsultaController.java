package com.springboot.hospital.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.ConsultaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    private final CitaMapper citaMapper;

    private final CitaService citaService;

    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listarConsultas() {
        List<ConsultaDTO> consultas = consultaService.getAllConsultas();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> obtenerConsultaPorId(@PathVariable Long id) {
        Optional<ConsultaDTO> consulta = consultaService.getConsultaById(id);
        return consulta.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ConsultaDTO> guardarConsulta (@RequestParam Long citaId, @RequestBody ConsultaDTO consultaDTO) throws ParseException {
        ConsultaDTO createdConsulta = consultaService.createConsulta(citaId, consultaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConsulta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> actualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDTO consultaDTO) throws ParseException {
        ConsultaDTO updateConsulta = consultaService.updateConsulta(id, consultaDTO);
        return updateConsulta != null 
                ? new ResponseEntity<>(updateConsulta, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Long id) {
        consultaService.deleteConsulta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ConsultaDTO>> listarConsultaPorInform(@RequestParam String search) {
        List<ConsultaDTO> consultasDtos = consultaService.getConsultasByInformeContaining(search);
        return new ResponseEntity<>(consultasDtos, HttpStatus.OK);
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<ConsultaDTO>> listarConsultasPorCita(@PathVariable Long citaId) throws ParseException {
        List<ConsultaDTO> consultasDTO = consultaService.getConsultaByCitaId(citaId);
        return new ResponseEntity<>(consultasDTO, HttpStatus.OK);
    }
    

}

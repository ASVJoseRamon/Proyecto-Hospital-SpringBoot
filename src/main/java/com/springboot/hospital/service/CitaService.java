package com.springboot.hospital.service;

import java.io.ObjectInputFilter.Status;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.StatusCita;

public interface CitaService {
    List<CitaDTO> getAllCitas();

    Optional<CitaDTO> getCitaById(Long id);

    Cita createCita(CitaDTO citaDTO, Long idPaciente, Long idMedico) throws ParseException;

    CitaDTO updateCita(Long id, CitaDTO citaDTO) throws ParseException;

    void deleteCita(Long id);

    List<CitaDTO> getCitasByPacienteId(Long pacienteId);

    List<CitaDTO> getCitasByMedicoId(Long medicoId);

    List<CitaDTO> getCitasByStatusCita(StatusCita statusCita);
}

package com.springboot.hospital.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.PacienteDTO;

public interface PacienteService {

    List<PacienteDTO> getAllPacientes();

    Optional<PacienteDTO> getPacienteById(Long idPaciente);

    PacienteDTO createPaciente(PacienteDTO pacienteDTO);

    PacienteDTO updatePacienteDTO(Long id, PacienteDTO pacienteDTO);

    void deletePaciente(Long id);

    Collection<CitaDTO> getCitasByPacienteId(Long pacienteId);

}

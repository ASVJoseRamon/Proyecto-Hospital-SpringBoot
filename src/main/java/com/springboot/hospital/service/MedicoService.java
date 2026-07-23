package com.springboot.hospital.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.MedicoDTO;

public interface MedicoService {
    List<MedicoDTO> getAllMedicos();
    
    Optional<MedicoDTO> getMedicoById(Long idMedico);

    MedicoDTO createMedicoDTO(MedicoDTO medicoDTO);

    MedicoDTO updateMedicoDTO(Long id, MedicoDTO medicoDTO);

    void deleteMedico(Long id);

    Collection<CitaDTO> getCitasBymedico(Long medicoId);

    List<MedicoDTO> getMedicosByEspecialidad(String especialidad);
}

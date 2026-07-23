package com.springboot.hospital.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.model.Cita;

public interface ConsultaService {

    List<ConsultaDTO> getAllConsultas();

    Optional<ConsultaDTO> getConsultaById(Long consultaId);

    ConsultaDTO createConsulta(Long citaId, ConsultaDTO consultaDTO) throws ParseException;

    ConsultaDTO updateConsulta(Long id, ConsultaDTO consultaDTO) throws ParseException;

    void deleteConsulta(Long id);

    List<ConsultaDTO> getConsultasByInformeContaining(String searchTerm);

    List<ConsultaDTO> getConsultasByCita(Cita cita);
    
    List<ConsultaDTO> getConsultaByCitaId(Long citaId) throws ParseException;

}

package com.springboot.hospital.service.impl;

import com.springboot.hospital.repository.CitaRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.ConsultaMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;
import com.springboot.hospital.model.StatusCita;
import com.springboot.hospital.repository.ConsultaRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.ConsultaService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaServiceImpl implements ConsultaService{

    private final CitaRepository citaRepository;

    private final ConsultaRepository consultaRepository;

    private final CitaMapper citaMapper;

    private final ConsultaMapper consultaMapper;

    private final CitaService citaService;
 
    @Override
    public List<ConsultaDTO> getAllConsultas() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream().map(consultaMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ConsultaDTO> getConsultaById(Long consultaId) {
        Optional<Consulta> consulta = consultaRepository.findById(consultaId);
        return consulta.map(consultaMapper::toDTO);   
    }

    @Override
    public ConsultaDTO createConsulta(Long citaId, ConsultaDTO consultaDTO) throws ParseException {
        CitaDTO citaDTO = citaService.getCitaById(citaId).orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));

        Consulta consulta = new Consulta();
        consulta.setCita(citaMapper.toEntity(citaDTO));
        consulta.setFechaConsulta(new Date());
        consulta.setInforme(consultaDTO.getInforme());

        Consulta createdConsulta = consultaRepository.save(consulta);
        return consultaMapper.toDTO(createdConsulta);
        
    }

    @Override
    public ConsultaDTO updateConsulta(Long id, ConsultaDTO consultaDTO) throws ParseException {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);
        if(consultaOptional.isPresent()){
            Consulta consulta = consultaOptional.get();

            consulta.setFechaConsulta(consultaDTO.getFechaConsultaasDate());
            consulta.setInforme(consultaDTO.getInforme());
            
            Consulta updateConsulta = consultaRepository.save(consulta);

            CitaDTO citaDTO = consultaDTO.getCitaDTO();

            if(citaDTO != null) {
                Cita cita = consulta.getCita();
                if(cita != null) {
                    cita.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(citaDTO.getFecha()));
                    cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

                    citaRepository.save(cita);
                }

                citaService.updateCita(cita.getId(),citaDTO);
            }
            return consultaMapper.toDTO(updateConsulta);
        }    
        return null;
    }

    @Override
    public void deleteConsulta(Long id) {
        consultaRepository.deleteById(id);
    }

    @Override
    public List<ConsultaDTO> getConsultasByInformeContaining(String searchTerm) {
        List<Consulta> consultas = consultaRepository.findByInformeContainingIgnoreCase(searchTerm);
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> getConsultasByCita(Cita cita) {
        List<Consulta> consultas = consultaRepository.findByCita(cita);
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> getConsultaByCitaId(Long citaId) throws ParseException {
        Optional<Cita> citaOptional = citaRepository.findById(citaId);
        if(citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            if(cita.getId() != null) {
                citaRepository.save(cita);
            }
            List<Consulta> consultas = consultaRepository.findByCita(cita);

            List<ConsultaDTO> consultasDTO = new ArrayList();
            for(Consulta consulta : consultas) {
                ConsultaDTO consultaDTO = new ConsultaDTO();
                consultaDTO.setId(consulta.getId());
                consultaDTO.setFechaConsulta(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(consulta.getFechaConsulta()));
                consultaDTO.setInforme(consulta.getInforme());

                consultasDTO.add(consultaDTO);
            }
            return consultasDTO;
        }
        else {
            throw new EntityNotFoundException("Cita no encontrada con ID: "+citaId);
        }
    }

}

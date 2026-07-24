package com.springboot.hospital.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.PacienteDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.PacienteMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Paciente;
import com.springboot.hospital.repository.PacienteRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.PacienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
@Lazy
public class PacienteServiceImpl implements PacienteService{

    private final PacienteRepository pacienteRepository;

   
    private final CitaService citaService;

    private final PacienteMapper pacienteMapper;

    private final CitaMapper citaMapper;

    @Override
    public List<PacienteDTO> getAllPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                        .map(pacienteMapper::toDTO)
                        .collect(Collectors.toList());
    }

    @Override
    public Optional<PacienteDTO> getPacienteById(Long idPaciente) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(idPaciente);
        return optionalPaciente.map(pacienteMapper::toDTO);
    }

    @Override
    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteDTO updatePacienteDTO(Long id, PacienteDTO pacienteDTO) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if(optionalPaciente.isPresent()){
            Paciente paciente = optionalPaciente.get();
            paciente.setNombre(pacienteDTO.getNombre());
            paciente.setFechaNacimiento(pacienteDTO.getFechaNaciemiento());
            paciente.setEnfermedad(pacienteDTO.isEnfermedad());
            paciente = pacienteRepository.save(paciente);

            return pacienteMapper.toDTO(paciente);
        }
        return null;
    }

    @Override
    public void deletePaciente(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if(optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();

            for(Cita cita : paciente.getCitas()){
                citaService.deleteCita(cita.getId());
            }

            pacienteRepository.deleteById(id);
        }
    }

    @Override
    public Collection<CitaDTO> getCitasByPacienteId(Long pacienteId) {
       Optional<Paciente> optionalPaciente = pacienteRepository.findById(pacienteId);
       return optionalPaciente.map(paciente -> paciente.getCitas().stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList()))
                .orElse(null);

    }

}

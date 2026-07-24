package com.springboot.hospital.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.MedicoDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.MedicoMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Medico;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.MedicoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;
    
    private final CitaService citaService;

    private final CitaMapper citaMapper;

    private final MedicoMapper medicoMapper;

    @Override
    public List<MedicoDTO> getAllMedicos() {
        List<Medico> medicos = medicoRepository.findAll();
        return medicos.stream()
        .map(medicoMapper::toDTO)
        .collect(Collectors.toList());
    }

    @Override
    public Optional<MedicoDTO> getMedicoById(Long idMedico) {
        Optional<Medico> optionalMedico = medicoRepository.findById(idMedico);
        return optionalMedico.map(medicoMapper::toDTO);
    }

    @Override
    public MedicoDTO createMedicoDTO(MedicoDTO medicoDTO) {
        Medico medico = medicoMapper.toEntity(medicoDTO);
        medico = medicoRepository.save(medico);
        return medicoMapper.toDTO(medico);
    }

    @Override
    public MedicoDTO updateMedicoDTO(Long id, MedicoDTO medicoDTO) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);
        if(optionalMedico.isPresent()) {
            Medico medico = optionalMedico.get();
            medico.setNombre(medicoDTO.getNombre());
            medico.setEmail(medicoDTO.getEmail());
            medico.setEspecialidad(medicoDTO.getEspecialidad());
            medico = medicoRepository.save(medico);
            return medicoMapper.toDTO(medico);
        }
        return null;
    }

    @Override
    public void deleteMedico(Long id) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);
        if(optionalMedico.isPresent()){
            Medico medico = optionalMedico.get();

            for(Cita cita : medico.getCitas()){
                citaService.deleteCita(cita.getId());
            }

            medicoRepository.deleteById(id);
        }
    }

    @Override
    public Collection<CitaDTO> getCitasBymedico(Long medicoId) {
        Optional<Medico> optionalMedico = medicoRepository.findById(medicoId);
        return optionalMedico.map(medico -> medico.getCitas().stream()
            .map(citaMapper::toDTO)
            .collect(Collectors.toList()))
            .orElse(null);    
    }

    @Override
    public List<MedicoDTO> getMedicosByEspecialidad(String especialidad) {
        List<Medico> medicos = medicoRepository.findByEspecialidad(especialidad);
        return medicos.stream()
            .map(medicoMapper::toDTO)
            .collect(Collectors.toList());
    }

}

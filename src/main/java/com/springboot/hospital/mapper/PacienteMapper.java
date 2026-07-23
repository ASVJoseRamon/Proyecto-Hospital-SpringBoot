package com.springboot.hospital.mapper;

import org.springframework.stereotype.Component;

import com.springboot.hospital.dto.PacienteDTO;
import com.springboot.hospital.model.Paciente;

@Component
public class PacienteMapper {
    public PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setNombre(paciente.getNombre());
        pacienteDTO.setFechaNaciemiento(paciente.getFechaNacimiento());
        pacienteDTO.setEnfermedad(paciente.isEnfermedad());
        return pacienteDTO;
    }
    public Paciente toEntity(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDTO.getId());
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNaciemiento());
        paciente.setEnfermedad(pacienteDTO.isEnfermedad());
        return paciente;
    }
}

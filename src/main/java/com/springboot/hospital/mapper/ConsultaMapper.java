package com.springboot.hospital.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;

@Component
public class ConsultaMapper {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ConsultaDTO toDTO(Consulta consulta){
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setId(consulta.getId());
        consultaDTO.setFechaConsulta(dateFormat.format(consulta.getFechaConsulta()));
        consultaDTO.setInforme(consulta.getInforme());
        if(consulta.getCita() != null) {
            Cita cita = consulta.getCita();
            CitaDTO citaDTO = new CitaDTO();

            citaDTO.setId(cita.getId());
            citaDTO.setFecha(dateFormat.format(cita.getFecha()));
            citaDTO.setCancelado(cita.isCancelado());
            citaDTO.setStatusCita(cita.getStatusCita().toString());
            citaDTO.setPacienteId(cita.getPaciente().getId());
            citaDTO.setMedicoId(cita.getMedico().getId());
            consultaDTO.setCitaDTO(citaDTO);
        }
        return consultaDTO;
    }

    public Consulta toEntity(ConsultaDTO consultaDTO) throws ParseException {
        Consulta consulta = new Consulta();

        consulta.setId(consultaDTO.getId());
        consulta.setFechaConsulta(dateFormat.parse(consultaDTO.getFechaConsulta()));
        consulta.setInforme(consultaDTO.getInforme());
        if(consultaDTO.getCitaDTO() != null) {
            Cita cita = new Cita();
            CitaDTO citaDTO =consultaDTO.getCitaDTO();
            cita.setId(citaDTO.getId());
            consulta.setCita(cita);
        }
        return consulta;
    }
}

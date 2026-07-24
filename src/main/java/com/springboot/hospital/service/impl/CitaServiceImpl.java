package com.springboot.hospital.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.MedicoDTO;
import com.springboot.hospital.dto.PacienteDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.MedicoMapper;
import com.springboot.hospital.mapper.PacienteMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;
import com.springboot.hospital.model.Medico;
import com.springboot.hospital.model.Paciente;
import com.springboot.hospital.model.StatusCita;
import com.springboot.hospital.repository.CitaRepository;
import com.springboot.hospital.repository.ConsultaRepository;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.MedicoService;
import com.springboot.hospital.service.PacienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService{
    
    private final CitaRepository citaRepository;

    private final PacienteRepository pacienteRepository;

    private final MedicoRepository medicoRepository;

    private final ConsultaRepository consultaRepository;

    private final CitaMapper citaMapper;

    @Override
    public List<CitaDTO> getAllCitas() {
        List<Cita> citas = citaRepository.findAll();
        return citas.stream()
        .map(citaMapper::toDTO)
        .collect(Collectors.toList());   
    }

    @Override
    public Optional<CitaDTO> getCitaById(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        return citaOptional.map(citaMapper::toDTO);
    
    }

    @Override
    public Cita createCita(CitaDTO citaDTO, Long idPaciente, Long idMedico) throws ParseException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(idPaciente);
        Optional<Medico> medicoOptional = medicoRepository.findById(idMedico);

        if(pacienteOptional == null || medicoOptional == null){
            return null;
        }

        Paciente paciente = pacienteOptional.get();
        Medico medico = medicoOptional.get();
        
        Cita cita = citaMapper.toEntity(citaDTO,paciente,medico);
        
        return citaRepository.save(cita);
    }

    @Override
    public CitaDTO updateCita(Long id, CitaDTO citaDTO) throws ParseException {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        
        if(citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            cita.setId(citaDTO.getId());

            if(citaDTO.getFecha() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha = dateFormat.parse(citaDTO.getFecha());
            }

            cita.setCancelado(citaDTO.isCancelado());
            cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

            Optional<Paciente> pacienteOptional = pacienteRepository.findById(citaDTO.getPacienteId());
            cita.setPaciente(pacienteOptional.get());

            Optional<Medico> medicoOptional = medicoRepository.findById(citaDTO.getMedicoId());
            cita.setMedico(medicoOptional.get());

            return citaMapper.toDTO(citaRepository.save(cita));
        }
        return null;
    }

    @Override
    public void deleteCita(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);

        if(citaOptional.isPresent()){
            Cita cita = citaOptional.get();

            if(cita.getConsulta() != null) {
                Consulta consulta = cita.getConsulta();
                consulta.setCita(null);
                consultaRepository.delete(consulta);
            }
            citaRepository.delete(cita);
        }
    }

    @Override
    public List<CitaDTO> getCitasByPacienteId(Long pacienteId) {
        List<Cita> citas = citaRepository.findByPacienteId(pacienteId);
        return citas.stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByMedicoId(Long medicoId) {
        List<Cita> citas = citaRepository.findByMedicoId(medicoId);
        return citas.stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByStatusCita(StatusCita statusCita) {
        List<Cita> citas = citaRepository.findByStatusCita(statusCita);
        return citas.stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
    }
    
}

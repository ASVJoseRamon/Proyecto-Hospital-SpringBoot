package com.springboot.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.StatusCita;

@Repository
public interface CitaRepository extends JpaRepository<Cita,Long>{

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByMedicoId(Long medicoId);

    List<Cita> findByStatusCita(StatusCita statusCita);

}

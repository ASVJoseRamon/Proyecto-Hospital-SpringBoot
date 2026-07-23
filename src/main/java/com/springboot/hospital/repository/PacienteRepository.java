package com.springboot.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.hospital.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long>{

    Paciente findByNombre(String nombre);

}

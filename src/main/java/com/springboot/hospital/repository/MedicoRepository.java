package com.springboot.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.hospital.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long> {

    Medico findByNombre(String nombre);

    List<Medico> findByEspecialidad(String especialidad);

}

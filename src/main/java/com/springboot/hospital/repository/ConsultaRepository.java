package com.springboot.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta,Long>{
    List<Consulta> findByCita(Cita cita);

    List<Consulta> findByInformeContainingIgnoreCase(String searchTerm);
}

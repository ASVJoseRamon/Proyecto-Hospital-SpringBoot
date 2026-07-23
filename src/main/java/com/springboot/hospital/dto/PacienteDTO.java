package com.springboot.hospital.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PacienteDTO {
    private Long id;

    private String nombre;

    private Date fechaNaciemiento;

    private boolean enfermedad;
}

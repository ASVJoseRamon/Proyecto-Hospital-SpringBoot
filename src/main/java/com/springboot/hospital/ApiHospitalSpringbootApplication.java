package com.springboot.hospital;

import com.springboot.hospital.repository.ConsultaRepository;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;
import com.springboot.hospital.model.Medico;
import com.springboot.hospital.model.Paciente;
import com.springboot.hospital.model.StatusCita;
import com.springboot.hospital.repository.CitaRepository;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;

@SpringBootApplication
public class ApiHospitalSpringbootApplication {

	private final ConsultaRepository consultaRepository;

    ApiHospitalSpringbootApplication(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(ApiHospitalSpringbootApplication.class, args);
	}

	//@Bean
	CommandLineRunner start(PacienteRepository pacienteRepository, MedicoRepository medicoRepository, CitaRepository citaRepository) {
		return args -> {
			Stream.of("Jose", "Roman", "Ximena")
				.forEach(nombre -> {
					Paciente paciente = new Paciente();
					paciente.setNombre(nombre);
					paciente.setFechaNacimiento(new Date());
					paciente.setEnfermedad(false);
					pacienteRepository.save(paciente);
				});
			Stream.of("House", "Chopper", "Law")
				.forEach(nombre -> {
					Medico medico = new Medico();
					medico.setNombre(nombre);
					medico.setEmail(nombre+((int)Math.random()*9)+"@gmail.com");
					medico.setEspecialidad(Math.random() > 0.5 ? "Doctor" : "Pedagologo");
					medicoRepository.save(medico);
				});

			Paciente paciente1 = pacienteRepository.findById(1l).orElse(null);

			Medico medico = medicoRepository.findByNombre("Chopper");

			Cita cita1 = new Cita();
			cita1.setFecha(new Date());
			cita1.setStatusCita(StatusCita.PENDIENTE);
			cita1.setMedico(medico);
			cita1.setPaciente(paciente1);
			citaRepository.save(cita1);

			Cita citaBD = citaRepository.findById(1l).orElse(null);

			Consulta consulta = new Consulta();
			consulta.setFechaConsulta(new Date());
			consulta.setCita(citaBD);
			consulta.setInforme("Informe de la consulta");
			consultaRepository.save(consulta);

		};
	}
}

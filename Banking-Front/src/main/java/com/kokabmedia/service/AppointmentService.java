package com.kokabmedia.service;

import java.util.List;
import java.util.Optional;

import com.kokabmedia.domain.Appointment;

/*
 * This interface is a for the AppointmentServiceImpl layer, this allows us to code against 
 * an interface and enforce loose coupling with the @Autowire annotation as per 
 * best practises.
 */
public interface AppointmentService {
	Appointment createAppointment(Appointment appointment);

    List<Appointment> findAll();

    Optional<Appointment> findAppointment(Long id);

    void confirmAppointment(Long id);
}

package com.ceiba.biblioteca.aplicacion.manejadores.prestamo;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import jdk.jshell.execution.LoaderDelegate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;

@Component
public class ManejadorGenerarPrestamo {

    private  RepositorioLibro repositorioLibro;
    private RepositorioPrestamo repositorioPrestamo;
    private Prestamo prestamo;
    private Libro libro;
    private ServicioBibliotecario servicioBibliotecario;

    @Transactional
    public void ejecutar(String isbn, String nombreCliente) {

        servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        libro = new Libro(repositorioLibro.obtenerPorIsbn(isbn).getIsbn(),
                repositorioLibro.obtenerPorIsbn(isbn).getTitulo(),
                repositorioLibro.obtenerPorIsbn(isbn).getAnio());

        prestamo = new Prestamo(libro);

        if(!servicioBibliotecario.validarSumDigitosMayor30(isbn)){
            prestamo.setFechaEntregaMaxima(null);
        }

        // prestamo.setFechaEntregaMaxima(servicioBibliotecario.getFechaEntrega(prestamo.getFechaSolicitud()));

        throw new UnsupportedOperationException("Método pendiente por implementar");
    }
}

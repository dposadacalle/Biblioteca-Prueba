package com.ceiba.biblioteca.dominio.servicio.bibliotecario;

import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.infraestructura.persistencia.repositorio.RepositorioPrestamoPersistente;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Component
public class ServicioBibliotecario {

    public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";

    // Esta constante, se definimos un valor es el tiempo en (Dias),
    // Defina como el numero maximo de la entrega.
    public static final int EL_NUMERO_MAXIMO_DE_DIAS_PARA_LA_ENTREGA = 15;

    private final RepositorioLibro repositorioLibro;
    private final RepositorioPrestamo repositorioPrestamo;

    public ServicioBibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioPrestamo = repositorioPrestamo;
    }

    public void prestar(String isbn, String nombreUsuario) {

        // Nos retorna a la variable tipo String, Digitos que componen el ISBN
        String digitosIsbn = getNumerosIsbn(isbn);

        if (esPalindromo(digitosIsbn)){
            throw new UnsupportedOperationException("los libros palíndromos solo se " +
                    "pueden utilizar en la biblioteca");
        }

        if(!validarSumDigitosMayor30(isbn)){
            LocalDate fchEntrega = null;
        }
    }

    public boolean esPrestado(String isbn) {

        return false;
    }

    // Este metodo nos devuelve, Extrae los digitos de una cadena de texto
    // método que retorna el valor; a este método envia la cadena en String
    public String getNumerosIsbn(String isbn){
        char[] isbn_div = isbn.toCharArray();

        String num = "";

        for (int i=0; i<isbn_div.length; i++){
            if(Character.isDigit(isbn_div[i])){
                num+=isbn_div[i];
            }
        }

        return num;
    }

    // Metodo nos retorna si los Digitos del Isbn es Palindromo
    // este metodo devuelve un valor logica true o false
    public boolean esPalindromo(String num){

        int aux, inverso=0, cifra;
        int n = Integer.parseInt(num);
        aux = n;

        while(aux != 0){
            cifra = aux%10;
            inverso = inverso*10 + cifra;
            aux = aux/10;
        }

        return n == inverso;
    }

    // Metodo que devuelve un Array,
    // Con los digitos Numericos del String Isbn,
    // Retorna el array tipo Entero, con los digitos,
    // Metodo se utiliza, para poder calcular suma de los digitos
    public int[] getArrayDigitosIsbn(String numIsbn){

        int num = Integer.parseInt(numIsbn);
        int[] digitos = new int[numIsbn.length()];
        int i = digitos.length-1;

        while (num > 0){
            digitos[i]=num%10;
            num=num/10;
            num--;
        }

        return digitos;
    }

    // Metodo que devuelve un Entero,
    // Hace la iteracíón, para el array De Digitos, y
    // Calcular su suma  y la retorna
    public int calcularSumDigitosIsbn(String numIsbn){

        int[] digitosIsbn = getArrayDigitosIsbn(numIsbn);
        int sum=0;

        for (int i=0; i<digitosIsbn.length; i++){
            sum+=digitosIsbn[i];
        }

        return sum;
    }

    // Este metodo ValidarSumDigitosMayor30
    // Recibi, lo que retorna desde el metodo calcularSumaDigitosIsbn(String numIsbn)
    // Valida que la suma de los digitos sea mayor a 30
    // Retorna un valor logico (booleano)
    public boolean validarSumDigitosMayor30(String numIsbn){
        return calcularSumDigitosIsbn(numIsbn) > 30;
    }

    // Metodo qe nos calcula la Fecha Maxima de Entrega
    // A partir de los parametros de la fecha de Prestamo,
    // validando que no incluya los Dias Domingos, tome el siguiente dia,
    // Enviamos como parametro de tipo localDate, y luego retornamos de tipo Date.
    public Date getFechaEntrega(LocalDate fechaPrestamo){

        LocalDate fechaEntrega = fechaPrestamo;

        int i = 1;

        while(i < EL_NUMERO_MAXIMO_DE_DIAS_PARA_LA_ENTREGA){
            fechaEntrega = fechaEntrega.plusDays(1);

            if(fechaEntrega.getDayOfWeek() == DayOfWeek.SUNDAY){
                fechaEntrega = fechaEntrega.plusDays(1);
            }
            i++; // Incrementamos la variable control del ciclo, Iteración
        }

        LocalDate dtFechaEntrega = LocalDate.of(fechaEntrega.getYear(),
                fechaEntrega.getMonthValue()-1, fechaEntrega.getDayOfMonth());

        return Date.from(Instant.from(dtFechaEntrega));
    }

}

package main;

import controlador.ControladorVentanaPersona;
import modelo.Conexion;
import modelo.PersonaDAO;
import vista.VistaVentanaPersona;

public class Main {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        PersonaDAO personaDAO = new PersonaDAO(conexion.getConexion());
        VistaVentanaPersona vista = new VistaVentanaPersona();
        new ControladorVentanaPersona(vista, personaDAO);
    }
}

package com.ejemplo.act1_introduccion_spring_boot.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
@RestController
public class InfoController {
    @GetMapping("/identificador")
    public String obtenerIdentificador() {
        return "Proyecto: Act1. Introducción a Spring Boot";
    }

    @GetMapping("/mis-datos")
    public Map<String, Object> obtenerDatosSimulados() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("nombre", "Sam");
        datos.put("actividad", "Act1. Introducción a Spring Boot");
        datos.put("materias", new String[]{"Programación Web"});
        return datos;
    }
}

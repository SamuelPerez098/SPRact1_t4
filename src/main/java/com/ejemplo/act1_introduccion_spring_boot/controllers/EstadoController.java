package com.ejemplo.act1_introduccion_spring_boot.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
@RestController
public class EstadoController {
    @GetMapping("/estado")
    public Map<String, Object> obtenerEstado() {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", 200);
        respuesta.put("mensaje", "Servidor Tomcat corriendo sin errores para Act1");
        return respuesta;
    }
}

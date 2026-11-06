# Actividad 1: Introducción a Spring Boot y Despliegue en AWS Lightsail

Desarrollo y despliegue de una aplicación **Spring Boot** en un servidor virtual VPS (**AWS Lightsail**)...

---

### Controladores y Endpoints (Fase 1)

Se implementaron 2 controladores desacoplados utilizando la anotación `@RestController` para la construcción de la API REST:

* **`IdentificadorController`**: Maneja peticiones `@GetMapping("/identificador")` retornando una respuesta simple en formato texto para identificar la instancia del estudiante.
* **`DatosController`**: Maneja respuestas estructuradas en formato `JSON` mediante colecciones `Map<String, Object>`:
  * `@GetMapping("/mis-datos")`: Devuelve la información del alumno, materia y datos del entorno.
  * `@GetMapping("/estado")`: Devuelve información sobre el estado operativo del servicio y la aplicación.

### 2. Configuración del Puerto Personalizado

Por defecto, Tomcat embebido en Spring Boot corre en el puerto `8080`. Para cumplir con las especificaciones del entorno y evitar conflictos con otros servicios, se definió el puerto `8081` en el archivo `src/main/resources/application.properties`:

```properties
server.port=8081
```
---

## Proceso de Despliegue en AWS Lightsail (Fase 2)

### Step 1: Compilación y Empaquetado Local
En la computadora local (Linux Mint), se generó el ejecutable `.jar` omitiendo las pruebas unitarias:
```bash
./mvnw clean package -DskipTests
```

### Step 2: Transferencia por SCP al VPS
El archivo compilado se envió mediante una conexión segura SSH/SCP al servidor en la nube:
```bash
scp -i ~/Descargas/LightsailDefaultKey-us-east-2.pem target/act1-introduccion-spring-boot-0.0.1-SNAPSHOT.jar ubuntu@3.148.185.31:~/
```

### Step 3: Configuración de Red y Firewall
En el panel de AWS Lightsail (pestaña *Networking*), se añadió una regla personalizada en el firewall **IPv4**:
* **Protocolo:** Custom TCP
* **Puerto:** `8081`
* **Origen:** Any IPv4 address (`0.0.0.0/0`)


### 2. Configuración como Servicio Persistente (`systemd`)
Para mantener la aplicación encendida 24/7 de forma automática (incluso tras reiniciar el VPS), se creó el servicio `mi-app.service` en `/etc/systemd/system/mi-app.service`:

```ini
[Unit]
Description=Aplicacion Spring Boot Act1
After=syslog.target network.target

[Service]
User=ubuntu
ExecStart=/usr/bin/java -Xmx128m -jar /home/ubuntu/act1-introduccion-spring-boot-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

*Nota:* El parámetro `-Xmx128m` limita el uso máximo del Heap de Java a **128 MB de RAM**, permitiendo que el sistema operativo y otros servicios convivan de manera estable.

Comandos para habilitar e iniciar el servicio:
```bash
sudo systemctl daemon-reload
sudo systemctl enable mi-app.service
sudo systemctl start mi-app.service
```

---

## Links (Endpoints Activos)

Los endpoints están disponibles en el servidor VPS mediante la IP pública en el puerto `8081`:
   http://3.148.185.31:8081/identificador
   http://3.148.185.31:8081/mis-datos

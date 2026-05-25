# NoraFit — Entrena. Registra. Progresa.

<img src="./docs/Nora.png" alt="Logo NoraFit" width="80"/>

## Descripción general

NoraFit es una aplicación orientada al registro, organización y seguimiento de rutinas de entrenamiento. El sistema permite gestionar usuarios, rutinas, ejercicios de fuerza, cardio simple y rutinas HIIT, facilitando el control del progreso físico mediante una solución gratuita y enfocada en funcionalidades esenciales.

El proyecto fue desarrollado en el marco de la asignatura Fundamentos de Ingeniería de Software y aplica prácticas de desarrollo colaborativo, control de versiones, pruebas automatizadas, integración continua, despliegue con Docker y documentación técnica del sistema.

## Propuesta de valor

NoraFit busca ofrecer una alternativa gratuita frente a aplicaciones fitness que limitan funciones básicas mediante suscripciones. El sistema permite registrar rutinas, ejercicios, series, repeticiones, peso, tiempos de descanso y entrenamientos cardiovasculares sin bloquear funcionalidades esenciales.

## Miembros del equipo

| Integrante | Rol principal | GitHub |
|---|---|---|
| Juan Pablo Peña | Scrum Master / Management | @jpenab02 |
| Juan José Mendoza Márquez | Configuration Manager / Frontend Developer | @Juanvil1840 |
| Santiago Martínez Cuellar | QA Lead / Databases | @Pochicard |
| Santiago Bautista Velásquez | DevOps Engineer / Backend Developer | @Santiago1213bv |
| Santiago Álvarez Serrano | Product Owner / Backend Developer | @AlvarezSS |

## Tecnologías utilizadas

| Capa | Tecnología |
|---|---|
| Frontend | Flutter |
| Backend | Java 17, Spring Boot |
| Persistencia | PostgreSQL / Supabase |
| Pruebas | JUnit 5, Mockito, JaCoCo |
| Calidad | SonarCloud / SonarQube |
| Contenedores | Docker, Docker Compose |
| CI/CD | GitHub Actions |
| Control de versiones | Git, GitHub |

## Estructura general del repositorio

```text
FIS_2610_3517_G3-main/
├── .github/
│   ├── ISSUE_TEMPLATE/
│   └── workflows/
├── db/
│   ├── schema.sql
│   ├── structure.sql
│   └── users.sql
├── docs/
│   ├── Diagramas/
│   ├── architecture/
│   ├── security/
│   ├── user_guide/
│   └── wiki/
├── scr/
│   ├── scrBackend/
│   │   └── norafit/
│   └── scrFrontend/
│       └── nora_fit/
├── docker-compose.yml
├── Dockerfile
├── CONTRIBUTING.md
└── README.md
```

## Prerrequisitos

Antes de ejecutar el proyecto se requiere tener instalado:

- Git
- Java JDK 17
- Maven o Maven Wrapper incluido en el proyecto
- Docker y Docker Compose
- Flutter SDK 3.x o superior
- Android Studio o Visual Studio Code con extensión de Flutter
- PostgreSQL local o proyecto Supabase activo

## Instalación del proyecto

Clonar el repositorio:

```bash
git clone https://github.com/puj-course/FIS_2610_3517_G3.git
cd FIS_2610_3517_G3
```

Entrar al backend:

```bash
cd scr/scrBackend/norafit
```

Dar permisos al Maven Wrapper en Linux/Mac o Git Bash:

```bash
chmod +x mvnw
```

Instalar dependencias y compilar:

```bash
./mvnw clean compile
```

En Windows PowerShell:

```powershell
./mvnw.cmd clean compile
```

## Configuración de variables de entorno

El backend requiere conexión a PostgreSQL y, para algunas funcionalidades, configuración de Twilio. Por seguridad, las credenciales no deben quedar escritas directamente en `application.properties`.

Ejemplo recomendado para `application.properties`:

```properties
spring.application.name=norafit

spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

twilio.account-sid=${TWILIO_ACCOUNT_SID}
twilio.auth-token=${TWILIO_AUTH_TOKEN}
twilio.phone-number=${TWILIO_PHONE_NUMBER}
```

Variables requeridas:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://host:puerto/base_datos
SPRING_DATASOURCE_USERNAME=usuario
SPRING_DATASOURCE_PASSWORD=contraseña
TWILIO_ACCOUNT_SID=valor
TWILIO_AUTH_TOKEN=valor
TWILIO_PHONE_NUMBER=valor
```

## Ejecución del backend

Desde la carpeta:

```bash
cd scr/scrBackend/norafit
```

Ejecutar:

```bash
./mvnw spring-boot:run
```

El backend quedará disponible por defecto en:

```text
http://localhost:8080
```

## Ejecución del frontend

Desde la raíz del repositorio:

```bash
cd scr/scrFrontend/nora_fit
```

Instalar dependencias:

```bash
flutter pub get
```

Ejecutar la aplicación:

```bash
flutter run
```

Nota: el emulador o dispositivo físico debe poder comunicarse con el backend. Si se usa un celular físico, debe estar en la misma red que el equipo donde corre el backend.

## Ejecución de pruebas

Desde el backend:

```bash
cd scr/scrBackend/norafit
```

Ejecutar pruebas unitarias:

```bash
./mvnw test
```

Ejecutar pruebas con reporte de cobertura JaCoCo:

```bash
./mvnw clean test jacoco:report
```

El reporte queda en:

```text
scr/scrBackend/norafit/target/site/jacoco/index.html
scr/scrBackend/norafit/target/site/jacoco/jacoco.xml
```

## Calidad de código con SonarCloud

El proyecto utiliza JaCoCo para generar cobertura y SonarCloud/SonarQube para analizar calidad, duplicación, mantenibilidad, confiabilidad y seguridad.

Comando base:

```bash
./mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121:sonar   -Dsonar.host.url=https://sonarcloud.io   -Dsonar.organization=puj-course   -Dsonar.projectKey=puj-course_FIS_2610_3517_G3   -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

En GitHub Actions se requiere configurar el secreto:

```text
SONAR_TOKEN
```

## Empaquetado del backend

Para generar el archivo `.jar` ejecutable:

```bash
cd scr/scrBackend/norafit
./mvnw clean package
```

Si se requiere omitir pruebas durante el empaquetado:

```bash
./mvnw clean package -DskipTests
```

El artefacto se genera en:

```text
target/
```

## Ejecución con Docker

Primero generar el `.jar`:

```bash
cd scr/scrBackend/norafit
./mvnw clean package -DskipTests
```

Construir la imagen:

```bash
docker build -t norafit-app .
```

Ejecutar el contenedor:

```bash
docker run -p 8080:8080 norafit-app
```

## Despliegue con Docker Compose

El proyecto incluye un `docker-compose.yml` que levanta dos servicios:

- `db`: base de datos PostgreSQL.
- `app`: backend NoraFit construido desde el Dockerfile.

Desde la carpeta del backend:

```bash
cd scr/scrBackend/norafit
docker compose up --build
```

Para detener los servicios:

```bash
docker compose down
```

Para eliminar también volúmenes:

```bash
docker compose down -v
```

## CI/CD con GitHub Actions

El repositorio incluye flujos automatizados en `.github/workflows/`:

| Workflow | Propósito |
|---|---|
| `compilacion.yml` | Compila el backend con Java 17 y Maven. |
| `pipelineEmpaquetado.yml` | Genera el `.jar` ejecutable y lo publica como artefacto. |
| `backend-tests.yml` / `build.yml` | Ejecuta pruebas, genera cobertura JaCoCo y envía análisis a SonarCloud. |
| `docker-deploy.yml` | Construye y publica imagen Docker en Docker Hub. |
| `daily.yml` | Crea issues automáticos para reuniones Daily. |
| `reporteSprint.yml` | Genera reporte semanal del sprint. |
| `validarInvest.yml` | Valida formato INVEST en Historias de Usuario. |

Para que los workflows funcionen correctamente se deben configurar los siguientes secretos en GitHub:

```text
SONAR_TOKEN
DOCKER_USERNAME
DOCKER_PASSWORD
```

## Funcionalidades principales

- Registro e inicio de sesión de usuarios.
- Gestión de rutinas.
- Creación y consulta de ejercicios.
- Registro de ejercicios de fuerza con series, repeticiones, peso y descanso.
- Registro de cardio simple.
- Ejecución de rutinas HIIT.
- Cálculo de duración estimada de ejercicios.
- Observadores para eventos de ejecución HIIT.
- Métricas internas de calidad y complejidad.
- Validación automatizada mediante pruebas unitarias.

## Arquitectura del backend

El backend está organizado por responsabilidades:

```text
controllers/     Entrada HTTP y exposición de endpoints
services/        Lógica de negocio
repositories/    Acceso a datos con Spring Data JPA
entities/        Modelo de dominio persistente
dto/             Objetos de transferencia de datos
factory/         Creación de tipos de ejercicios
strategy/        Cálculo de tiempos según tipo de ejercicio
facade/          Simplificación de operaciones de rutina
decorators/      Extensión de comportamiento HIIT
observer/        Notificación de eventos HIIT
metrics/         Métricas de calidad y reglas de negocio
config/          Configuración externa del sistema
```

## Patrones de diseño aplicados

| Patrón | Ubicación | Propósito |
|---|---|---|
| Factory Method | `factory/` | Crear distintos tipos de ejercicios sin acoplar el cliente a clases concretas. |
| Strategy | `strategy/` | Cambiar el cálculo de tiempo según el tipo de ejercicio. |
| Facade | `RoutineManagementFacade` | Simplificar la interacción entre cliente y servicios de rutina. |
| Decorator | `HIITExecutionServiceDecorator`, `HIITStatsDecorator` | Añadir estadísticas a la ejecución HIIT sin modificar el servicio base. |
| Observer | `observer/` | Notificar eventos ocurridos durante la ejecución de rutinas HIIT. |
| Builder | `StrengthSeriesBuilder` | Construir series de fuerza de forma clara y controlada. |

## Contribución

Para contribuir al proyecto:

1. Crear una rama desde `main`:
   ```bash
   git checkout -b feature/nombre-funcionalidad
   ```

2. Realizar cambios siguiendo la estructura del proyecto.

3. Ejecutar pruebas:
   ```bash
   cd scr/scrBackend/norafit
   ./mvnw test
   ```

4. Confirmar cambios:
   ```bash
   git add .
   git commit -m "feat: descripción clara del cambio"
   ```

5. Subir la rama:
   ```bash
   git push origin feature/nombre-funcionalidad
   ```

6. Crear Pull Request hacia `main`.

Antes de aprobar un Pull Request se debe verificar:

- El proyecto compila correctamente.
- Las pruebas pasan.
- No se introducen credenciales en el repositorio.
- Se actualiza la documentación si el cambio afecta arquitectura, uso o despliegue.
- Se respeta el formato de Historias de Usuario cuando aplique.

## Documentación adicional

La documentación del proyecto se encuentra en:

```text
docs/
docs/Diagramas/
docs/architecture/
docs/security/
docs/user_guide/
docs/wiki/
```

Se recomienda usar la Wiki del repositorio para explicar:

- Arquitectura.
- Modelo de datos.
- Patrones aplicados.
- Despliegue.
- Pruebas y cobertura.
- Flujos de CI/CD.
- Guía de contribución.

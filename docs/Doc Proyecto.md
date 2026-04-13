# Documentación del Proyecto NoraFit

## 1. Descripción general

NoraFit es una aplicación orientada a la gestión de rutinas de ejercicio y entrenamiento físico. El sistema permite a los usuarios crear, visualizar y administrar rutinas que incluyen distintos tipos de ejercicios, como entrenamiento de fuerza y ejercicios cardiovasculares.

La arquitectura del proyecto está dividida en tres componentes principales:

* **Backend**: Implementa la lógica del sistema y expone una API REST.
* **Frontend**: Proporciona la interfaz gráfica para los usuarios mediante una aplicación móvil.
* **Mockups**: Representan el diseño conceptual de las pantallas antes de su implementación.

Estos componentes siguen una arquitectura **cliente-servidor**, donde el frontend actúa como cliente y consume los servicios proporcionados por el backend.

---

# 2. Arquitectura del sistema

El funcionamiento general del sistema sigue el siguiente flujo:

Usuario → Frontend → Backend → Base de datos → Backend → Frontend → Usuario

1. El usuario interactúa con la aplicación móvil.
2. El frontend envía solicitudes HTTP a la API del backend.
3. El backend procesa la lógica de negocio.
4. Se consulta o actualiza la información en la base de datos.
5. El backend devuelve una respuesta al frontend en formato JSON.
6. El frontend muestra la información al usuario.

---

# 3. Backend

## 3.1 Descripción

El backend de NoraFit está desarrollado en **Java** utilizando el framework **Spring Boot**. Este componente se encarga de gestionar la lógica del sistema, administrar los datos y exponer los servicios necesarios para que el frontend pueda interactuar con la aplicación.

Ubicación dentro del repositorio:

```
scr/scrBackend/norafit
```

---

## 3.2 Arquitectura interna

El backend sigue una arquitectura por capas que separa responsabilidades dentro del sistema:

* **Controllers**
  Gestionan las peticiones HTTP que llegan desde el frontend y definen los endpoints de la API.

* **Services**
  Implementan la lógica de negocio del sistema, procesando la información antes de enviarla a la base de datos o devolverla al cliente.

* **Repositories**
  Permiten la interacción con la base de datos mediante operaciones de persistencia.

* **Entities**
  Representan los modelos de datos utilizados en el sistema.

---

## 3.3 Modelo de datos

El sistema utiliza varias entidades para representar los diferentes elementos relacionados con las rutinas de ejercicio.

Principales entidades identificadas en el proyecto:

* **User**
  Representa a los usuarios registrados en el sistema.

* **Routine**
  Representa una rutina de entrenamiento creada por un usuario.

* **Exercise**
  Clase base que define un ejercicio dentro de una rutina.

* **StrengthExercise**
  Representa ejercicios de fuerza que pueden incluir series y peso.

* **StrengthSeries**
  Define las series asociadas a ejercicios de fuerza (repeticiones, peso, etc.).

* **CardioExercise**
  Representa ejercicios cardiovasculares.

* **SimpleCardio**
  Tipo de ejercicio cardiovascular básico.

* **HIITCardio**
  Representa ejercicios cardiovasculares de alta intensidad (HIIT).

Esta estructura permite modelar diferentes tipos de entrenamiento de forma flexible.

---

## 3.4 Funcionalidades principales del backend

El backend permite:

* Gestión de usuarios.
* Creación y administración de rutinas.
* Gestión de ejercicios dentro de las rutinas.
* Diferenciación entre ejercicios de fuerza y ejercicios cardiovasculares.
* Comunicación con el frontend mediante una API REST.

---

# 4. Frontend

## 4.1 Descripción

El frontend de NoraFit corresponde a la aplicación móvil que permite a los usuarios interactuar con el sistema. Está desarrollado utilizando **Flutter**, lo que permite generar aplicaciones multiplataforma para Android, iOS y Web a partir de una única base de código.

Ubicación dentro del repositorio:

```
scr/srcFrontend/nora_fit
```

---

## 4.2 Tecnologías utilizadas

* **Flutter** – Framework para desarrollo de aplicaciones móviles multiplataforma.
* **Dart** – Lenguaje de programación utilizado por Flutter.
* **Material Design** – Sistema de diseño utilizado para los componentes visuales.

---

## 4.3 Estructura del proyecto

El frontend sigue la estructura estándar de un proyecto Flutter:

* **android/**
  Contiene la configuración necesaria para compilar la aplicación en dispositivos Android.

* **ios/**
  Incluye los archivos necesarios para ejecutar la aplicación en dispositivos iOS.

* **lib/**
  Carpeta principal donde se encuentra el código fuente de la aplicación, incluyendo pantallas, widgets y lógica de navegación.

* **assets/**
  Contiene recursos estáticos como imágenes, íconos y archivos utilizados por la interfaz.

* **web/**
  Permite ejecutar la aplicación en navegadores web.

* **test/**
  Incluye pruebas automatizadas del frontend.

---

## 4.4 Archivos de configuración importantes

* **pubspec.yaml**
  Archivo principal de configuración del proyecto Flutter donde se definen dependencias, recursos y versiones.

* **analysis_options.yaml**
  Define reglas de análisis estático del código para mantener buenas prácticas de desarrollo.

* **README.md**
  Contiene información básica sobre el proyecto.

---

## 4.5 Rol del frontend dentro del sistema

El frontend cumple las siguientes funciones dentro de NoraFit:

* Mostrar la interfaz gráfica al usuario.
* Permitir la navegación entre pantallas.
* Capturar las acciones del usuario.
* Enviar solicitudes al backend mediante la API REST.
* Mostrar los datos recibidos desde el servidor.

---

# 5. Mockups

Los mockups representan el diseño visual preliminar de la aplicación antes de su implementación en el frontend. Estos diseños permiten definir la estructura de las pantallas, la experiencia de usuario y la organización de los elementos visuales.

Los mockups sirven como guía para el desarrollo del frontend, asegurando que la aplicación final mantenga coherencia en su diseño y funcionalidad.

Las principales pantallas contempladas en los mockups incluyen:

* Pantalla de bienvenida.
* Pantalla de registro de usuario.
* Pantalla de inicio de sesión.
* Visualización de rutinas.
* Gestión de ejercicios dentro de una rutina.

---

# 6. Integración de componentes

La integración entre los componentes del sistema se basa en el uso de una API REST.

| Componente | Rol                                                   |
| ---------- | ----------------------------------------------------- |
| Backend    | Implementa la lógica del sistema y gestiona los datos |
| Frontend   | Proporciona la interfaz de usuario                    |
| Mockups    | Definen el diseño visual previo de la aplicación      |

El frontend realiza solicitudes HTTP al backend para obtener o modificar información, y el backend responde con datos estructurados en formato JSON.


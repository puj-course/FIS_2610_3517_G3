# 1. Visión General del Proyecto

## Objetivo del Sistema
NoraFit es una aplicación móvil diseñada para permitir a los usuarios registrar, organizar y analizar sus rutinas de gimnasio de manera simple, gratuita y eficiente. El sistema busca ofrecer una solución accesible para el seguimiento del progreso físico sin restricciones de pago.

## Alcance
El sistema permitirá:
- Registro de rutinas personalizadas.
- Seguimiento de peso, repeticiones y tiempos de descanso.
- Modo HIIT basado en intervalos de tiempo.
- Visualización de progreso histórico.
- Gestión estructurada de entrenamientos.

No incluye actualmente:
- Funciones sociales.
- Integración con wearables.
- Suscripciones o contenido premium.

## Problema que se Busca Resolver
Muchas aplicaciones fitness limitan funcionalidades esenciales bajo suscripciones. NoraFit elimina estas barreras ofreciendo herramientas básicas de seguimiento completamente gratuitas.

## Stakeholders
- Usuarios de gimnasio.
- Equipo de desarrollo.
- Docente del curso.
- Futuras versiones del producto.

# 2. Gestión de Requerimientos

## 2.1 Casos de Uso / Historias de Usuario

### HU-01: Registrar ejercicio
**Actor:** Usuario  
**Descripción:** El usuario puede registrar peso y repeticiones de un ejercicio.  
**Reglas de negocio:**  
- El peso debe ser mayor que 0.  
- Las repeticiones deben ser un número entero positivo.  
**Criterios de aceptación:**  
- El sistema guarda el registro correctamente.  
- Se puede visualizar el historial del ejercicio.  

### HU-02: Crear rutina
**Actor:** Usuario  
**Descripción:** El usuario puede crear una rutina personalizada.  
**Reglas de negocio:**  
- Debe contener al menos un ejercicio.  
**Criterios de aceptación:**  
- La rutina queda almacenada.  
- Puede editarse posteriormente.  

### HU-03: Modo HIIT
**Actor:** Usuario  
**Descripción:** Permite configurar intervalos de trabajo y descanso.  
**Criterios de aceptación:**  
- El temporizador funciona correctamente.  
- Se notifican cambios entre intervalos.


# 3. Arquitectura y Diseño

## Estilo Arquitectónico
El sistema sigue una arquitectura cliente-servidor, donde el frontend móvil se comunica con un backend REST.

## Justificación
Este modelo permite escalabilidad, separación de responsabilidades y mantenimiento independiente entre interfaz y lógica de negocio.

## Patrones de Diseño Utilizados
- MVC en backend.
- Separación por capas (Controller, Service, Repository).
- Principios de Programación Orientada a Objetos.

## Separación de Capas
- Presentación (Flutter)
- Lógica de negocio (Spring Boot)
- Persistencia (Base de datos relacional)

# 4. Componentes y Tecnologías

## 4.1 Frontend
**Tecnología:** Flutter  
**Responsabilidades:**
- Interfaz de usuario.
- Gestión de estado.
- Interacción con API REST.

## 4.2 Backend
**Tecnología:** Java + Spring Boot  

### Servicios
- Gestión de rutinas.
- Gestión de ejercicios.
- Registro de progreso.

### Controladores
- Endpoints REST.
- Validación de entradas.

### Lógica de negocio
- Procesamiento de registros.
- Reglas de negocio.

## 4.3 Base de Datos
**Motor:** (Especificar: MySQL / PostgreSQL)  

### Modelo de datos
- Usuario
- Rutina
- Ejercicio
- Registro

### Relaciones
- Un usuario puede tener múltiples rutinas.
- Una rutina puede tener múltiples ejercicios.

## 4.4 Automatización
- GitHub Actions para integración continua.
- Docker para despliegue.
- SonarQube para análisis de calidad.


# 5. Diagramas del Sistema

## Diagrama de Clases
(Insertar imagen del diagrama de clases)

## Diagrama de Base de Datos
(Insertar imagen del modelo entidad-relación)

## Diagrama de Componentes
(Insertar imagen de arquitectura general)

## Diagrama de Despliegue
(Insertar imagen de infraestructura)

## Diagramas de Comunicación (EBC)
(Insertar diagramas de interacción entre componentes)

# 6. Modelo de Inteligencia Artificial

Actualmente el sistema no implementa un modelo de Inteligencia Artificial. 

# 7. DevOps y Automatización

## Git Workflow
- Rama main: versión estable.
- Rama develop: integración.
- Ramas features: desarrollo individual.
- Pull Requests obligatorios.
- Revisión por pares.

## Integración Continua
- Uso de GitHub Actions para validación automática.

## Despliegue con Docker
- Contenerización del backend.
- Entornos consistentes.

## Gestión de Entornos
- Desarrollo
- Pruebas
- Producción

## Automatización de Pruebas
- Pruebas unitarias en backend.
- Validación de endpoints.

# 8. Métricas de Calidad

## Resultados de SonarQube
- Análisis estático de código.
- Identificación de code smells.

## Cobertura de Pruebas
- Medición de pruebas unitarias.
- Reporte de cobertura porcentual.

## Estado del Proyecto por Sprint
- Sprint 1: Diseño inicial.
- Sprint 2: Implementación backend.
- Sprint 3: Integración frontend-backend.
- Sprint 4: Optimización y pruebas.

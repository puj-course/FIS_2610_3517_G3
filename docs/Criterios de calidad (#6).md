# 📌 NoraFit – Criterios de Calidad (MVP) (Actualizado)

## 1. Propósito

Este documento define los estándares iniciales de calidad para el MVP de NoraFit.  
Establece las condiciones mínimas que debe cumplir una funcionalidad, historia de usuario o tarea para considerarse **“Done” (Terminada)**, garantizando coherencia con el Documento de Requisitos y la Wiki del proyecto.

Aplica a:

- Frontend móvil en Flutter  
- Backend en Java + Spring Boot  
- Base de datos PostgreSQL  
- Almacenamiento de GIFs  
- Autenticación con JWT  

---

# 2. Definición de “Done” (DoD)

Una tarea o historia de usuario se considera **Terminada** únicamente si cumple TODOS los siguientes criterios:

---

## 2.1 Funcionalidad

- Cumple completamente los **Acceptance Criteria** definidos en el Issue.
- El flujo principal funciona de inicio a fin sin errores.
- No existen bugs críticos o bloqueantes abiertos.
- Las validaciones están implementadas (ej: peso > 0, repeticiones > 0).
- Los datos se almacenan correctamente en base de datos.

---

## 2.2 Backend (Spring Boot)

- Endpoint implementado según el contrato definido.
- Uso correcto de códigos HTTP (200, 201, 400, 401, 403, 404).
- Validaciones del lado servidor implementadas.
- Autenticación mediante JWT aplicada cuando corresponda.
- Endpoints de administrador protegidos.
- Contraseñas almacenadas con hashing seguro (bcrypt o Argon2).
- Manejo adecuado de excepciones.
- Pruebas unitarias básicas implementadas en la lógica crítica.
- No exposición de datos sensibles en logs.

---

## 2.3 Frontend (Flutter)

- Interfaz clara, simple y alineada con el principio “Simplicity First”.
- No existen crashes ni bloqueos.
- Manejo adecuado de errores (mensajes visibles al usuario).
- GIFs cargan correctamente en la biblioteca de ejercicios.
- Visualización de músculos activos visible y comprensible.
- Temporizadores (descanso y HIIT) funcionan correctamente.
- Compatible con:
  - Android 8+
  - iOS 12+

---

## 2.4 Base de Datos

- Persistencia correcta de:
  - Usuarios
  - Rutinas
  - Ejercicios
  - Sesiones
  - Sets
- Relaciones entre entidades consistentes.
- No se rompen datos existentes.
- Migraciones documentadas si aplican.

---

## 2.5 Rendimiento

- Tiempo de respuesta API < 300 ms bajo carga normal.
- No existen consultas innecesarias a la base de datos.
- Carga de GIF optimizada para evitar consumo excesivo de datos.
- Temporizadores ejecutan cuenta regresiva sin desfase.

---

## 2.6 Seguridad

- Uso obligatorio de HTTPS.
- JWT implementado correctamente.
- Control de acceso por roles en endpoints administrativos.
- No se exponen datos sensibles en respuestas.
- Flujo funcional para eliminación de cuenta.
- Recolección mínima de datos personales (privacidad por diseño).

---

## 2.7 Cumplimiento de Requisitos No Funcionales

Cada funcionalidad debe respetar:

- Disponibilidad objetivo: 99.5% (servicios críticos).
- Código mantenible y estructurado.
- Documentación mínima necesaria.
- Pipeline CI/CD funcional.
- Arquitectura modular preparada para escalar.
- Soporte inicial en español.
- Preparado para futura internacionalización (i18n).

---

# 3. Criterios de Aceptación del MVP

El MVP se considera listo si cumple:

- Registro e inicio de sesión funcional.
- CRUD completo de rutinas.
- Registro de sets con peso y repeticiones.
- Temporizador de descanso operativo.
- Modo HIIT configurable y funcional.
- Biblioteca de ejercicios con GIF y músculos activos.
- Historial de sesiones visible.
- Tendencia básica por ejercicio.
- Opción para eliminar cuenta.
- Sin funciones sociales implementadas.

---

# 4. Clasificación de Severidad de Errores

## 🔴 Crítico
- Fallo en autenticación.
- Pérdida de datos.
- Crash de la aplicación.
- Sesiones no guardadas.
- Vulnerabilidad de seguridad.

Bloquea release.

## 🟠 Mayor
- Funcionalidad incompleta.
- Cálculos incorrectos en progreso.
- CRUD inestable.
- Temporizador HIIT inconsistente.

Debe resolverse antes de liberar.

## 🟡 Menor
- Problemas visuales.
- Validaciones no críticas.
- Detalles de UI.

No bloquea release, pero debe registrarse.

---

# 5. Proceso QA

Antes de cerrar un Issue:

1. Pruebas realizadas por el desarrollador.
2. Revisión de código completada.
3. Validación backend + frontend integrada.
4. Prueba manual del flujo principal.
5. No existen bugs abiertos asociados.

---

# 6. Fuera del Alcance (MVP)

No deben implementarse:

- Feed social
- Likes o comentarios
- Compartición pública
- Funciones premium
- Integración con wearables
- Recomendaciones con Machine Learning

---

# 7. Mejora Continua

Este documento puede actualizarse si:

- Cambian requisitos
- Se identifican nuevos riesgos
- Se ajustan métricas de rendimiento
- Se amplía el alcance del producto

El objetivo es garantizar que NoraFit sea:
Una aplicación gratuita, simple, estable y confiable para el seguimiento de entrenamientos.

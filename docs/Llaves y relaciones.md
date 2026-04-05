# Modelo de Relaciones entre Entidades - NoraFit
## Descripción
Este documento describe la estructura relacional y jerárquica de las entidades principales del sistema **NoraFit**, incluyendo:
- llaves primarias (PK)
- llaves foráneas (FK)
- relaciones entre entidades
- herencia
- composición
- propósito funcional de negocio

# 1. Entidad Users
Representa los usuarios registrados en la plataforma.

## Llave primaria
- `id`

## Atributos principales
- `username`
- `email`
- `password`
- `role`
- `created_at`

## Relaciones
### Users 1:N Routine
Un usuario puede poseer múltiples rutinas.

## Llave foránea relacionada
- `Routine.user_id → Users.id`

## Propósito
Permitir que cada usuario administre sus rutinas personalizadas.

---

# 2. Entidad Routine
Representa una rutina de entrenamiento creada por un usuario.

## Llave primaria
- `id`

## Llave foránea
- `user_id → Users.id`

## Atributos principales
- `routineName`
- `totalTimeSeconds`
- `createdAt`

## Relaciones
### Routine 1:N Exercise
Una rutina compone múltiples ejercicios.

## Llave foránea relacionada
- `Exercise.routine_id → Routine.id`

## Propósito
Agrupar ejercicios dentro de una sesión de entrenamiento.

---

# 3. Entidad Exercise
Entidad abstracta base para cualquier tipo de ejercicio.

## Llave primaria
- `id`

## Llave foránea
- `routine_id → Routine.id`

## Atributos principales
- `exerciseName`
- `description`

## Relaciones
### Herencia
- `Exercise → StrengthExercise`
- `Exercise → CardioExercise`

## Propósito
Centralizar la información común de cualquier ejercicio:
- nombre
- descripción
- rutina asociada

---

# 4. Entidad StrengthExercise
Especialización de `Exercise` para ejercicios de fuerza.

## Llave primaria
- `id` *(heredado de Exercise)*

## Atributos principales
- `hasWeight`

## Relaciones
### StrengthExercise 1:N StrengthSeries
Un ejercicio de fuerza contiene múltiples series.

## Llave foránea relacionada
- `StrengthSeries.strength_exercise_id → StrengthExercise.id`

## Propósito
Gestionar ejercicios con:
- repeticiones
- peso
- series

---

# 5. Entidad StrengthSeries
Representa cada serie de un ejercicio de fuerza.

## Llave primaria
- `id`

## Llave foránea
- `strength_exercise_id → StrengthExercise.id`

## Atributos principales
- `seriesNumber`
- `repetitions`
- `weight`
- `restTimeSeconds`

## Propósito
Guardar el detalle de cada serie realizada dentro de un ejercicio de fuerza.

---

# 6. Entidad CardioExercise
Entidad abstracta derivada de `Exercise`.

## Llave primaria
- `id` *(heredado de Exercise)*

## Atributos principales
- `durationMinutes`
- `intensity`
- `machineType`

## Relaciones
### Herencia
- `CardioExercise → HIITCardio`
- `CardioExercise → SimpleCardio`

## Propósito
Definir la base común para ejercicios cardiovasculares.

---

# 7. Entidad HIITCardio
Especialización de cardio por intervalos.

## Llave primaria
- `id` *(heredado de CardioExercise)*

## Atributos principales
- `rounds`
- `workTimeSeconds`
- `restTimeSeconds`

## Propósito
Representar entrenamientos HIIT con rondas de trabajo y descanso.

---

# 8. Entidad SimpleCardio
Especialización de cardio continuo.

## Llave primaria
- `id` *(heredado de CardioExercise)*

## Atributos principales
- `distanceKm`
- `averageSpeed`
- `inclineLevel`

## Propósito
Registrar ejercicios cardiovasculares continuos y sus métricas.

---

# Resumen de relaciones
- **Users 1:N Routine**
- **Routine 1:N Exercise**
- **Exercise → StrengthExercise (Herencia)**
- **Exercise → CardioExercise (Herencia)**
- **StrengthExercise 1:N StrengthSeries**
- **CardioExercise → HIITCardio (Herencia)**
- **CardioExercise → SimpleCardio (Herencia)**

---

# Reglas de integridad
- No puede existir una rutina sin usuario.
- No puede existir un ejercicio sin rutina.
- No puede existir una serie sin ejercicio de fuerza.
- Las entidades hijas deben depender de su entidad padre.
- Al eliminar una rutina deben eliminarse sus ejercicios.
- Al eliminar un ejercicio de fuerza deben eliminarse sus series.

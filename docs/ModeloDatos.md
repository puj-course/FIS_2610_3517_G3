# Modelo de datos del sistema NoraFit

## Introducción

Este documento describe el modelo de datos del sistema NoraFit, identificando las entidades principales, sus llaves primarias, sus llaves foráneas y las relaciones que existen entre ellas. El objetivo de esta documentación es dejar claridad sobre la estructura de persistencia del sistema y facilitar la comprensión de cómo se organiza la información dentro de la aplicación.

## Entidades del modelo

### User
La entidad `User` representa a los usuarios del sistema.

Llave primaria:
- `id`

Atributos principales:
- `username`
- `email`
- `password`
- `role`
- `created_at`

Relaciones:
- Un usuario puede tener varias rutinas asociadas.

### Routine
La entidad `Routine` representa una rutina de entrenamiento creada por un usuario.

Llave primaria:
- `id`

Atributos principales:
- `routineName`
- `totalTimeSeconds`
- `created_at`

Llave foránea:
- `user_id`

Relaciones:
- Cada rutina pertenece a un solo usuario.
- Una rutina puede contener varios ejercicios.

### Exercise
La entidad `Exercise` representa la información base de un ejercicio dentro del sistema.

Llave primaria:
- `id`

Atributos principales:
- `exerciseName`
- `description`

Llave foránea:
- `routine_id`

Relaciones:
- Cada ejercicio pertenece a una rutina.
- Esta entidad funciona como clase base para los tipos de ejercicio del dominio.

### StrengthExercise
La entidad `StrengthExercise` representa los ejercicios de fuerza.

Llave primaria:
- `id`

Atributos principales:
- `hasWeight`

Relaciones:
- Hereda de `Exercise`.
- Puede contener varias series de fuerza.

### StrengthSeries
La entidad `StrengthSeries` representa las series asociadas a un ejercicio de fuerza.

Llave primaria:
- `id`

Atributos principales:
- `seriesNumber`
- `repetitions`
- `weight`
- `restTimeSeconds`

Llave foránea:
- `exercise_id`

Relaciones:
- Cada serie pertenece a un ejercicio de fuerza.
- Un ejercicio de fuerza puede tener varias series.

### CardioExercise
La entidad `CardioExercise` representa la base de los ejercicios de cardio.

Llave primaria:
- `id`

Atributos principales:
- `durationMinutes`
- `intensity`
- `machineType`

Relaciones:
- Hereda de `Exercise`.
- Sirve como clase padre para los distintos tipos de cardio.

### HIITCardio
La entidad `HIITCardio` representa los ejercicios de cardio tipo HIIT.

Llave primaria:
- `id`

Atributos principales:
- `rounds`
- `workTimeSeconds`
- `restTimeSeconds`

Relaciones:
- Hereda de `CardioExercise`.

### SimpleCardio
La entidad `SimpleCardio` representa los ejercicios de cardio simple.

Llave primaria:
- `id`

Atributos principales:
- `distanceKm`
- `averageSpeed`
- `inclineLevel`

Relaciones:
- Hereda de `CardioExercise`.

## Relaciones entre entidades

### Relación entre User y Routine
Un usuario puede tener varias rutinas, pero cada rutina pertenece a un solo usuario.  
Esta relación se representa como una relación uno a muchos.

### Relación entre Routine y Exercise
Una rutina puede contener varios ejercicios, mientras que cada ejercicio pertenece a una sola rutina.  
Esta relación también se representa como una relación uno a muchos.

### Relación entre Exercise y sus tipos derivados
`Exercise` funciona como clase base para los ejercicios del sistema.  
A partir de esta entidad se derivan los ejercicios de fuerza y cardio, permitiendo organizar mejor el dominio.

### Relación entre StrengthExercise y StrengthSeries
Un ejercicio de fuerza puede tener varias series, y cada serie pertenece a un solo ejercicio de fuerza.  
Esta relación permite almacenar la información detallada de repeticiones, peso y descanso.

### Relación entre CardioExercise y sus subtipos
`CardioExercise` sirve como clase base para los ejercicios de cardio del sistema.  
A partir de esta entidad se derivan `HIITCardio` y `SimpleCardio`, que representan distintas formas de entrenamiento cardiovascular.

## Conclusión

El modelo de datos de NoraFit organiza la información de manera jerárquica y coherente, permitiendo relacionar usuarios, rutinas y ejercicios dentro del sistema. Esta estructura facilita la persistencia de los datos y ayuda a mantener una separación clara entre las entidades principales del dominio.
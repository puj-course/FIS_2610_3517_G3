# Modelo ER

El modelo separa claramente:

- **Planificación:** `routines` + `routine_exercises`
- **Ejecución:** `routine_sessions` + `series`

---

## Entidades

- **users** (PK: id_user)  
  Un usuario crea muchas rutinas y realiza muchas sesiones.

- **routines** (PK: id_routine, FK: id_user)  
  Pertenece a un usuario.  
  Se relaciona con muchos ejercicios (N:M).  
  Puede usarse en muchas sesiones.

- **exercises** (PK: id_exercise)  
  Puede estar en muchas rutinas y en muchas series.

- **routine_exercises** (PK compuesta: id_routine, id_exercise)  
  Resuelve la relación muchos-a-muchos entre rutinas y ejercicios.

- **routine_sessions** (PK: id_session, FK: id_user, id_routine)  
  Representa un entrenamiento realizado.

- **series** (PK: id_series, FK: id_session, id_exercise)  
  Registra repeticiones, peso y descanso de cada ejercicio en una sesión.

---

## Decisiones de Modelado

- Se separó planificación de ejecución.
- Se normalizó la relación Rutina–Ejercicio con tabla intermedia.
- Se mantienen claves foráneas para asegurar integridad referencial.

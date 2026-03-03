# Estructura de la base de datos en Supabase

## Objetivo
Documentar el orden de ejecución y el propósito de los scripts SQL usados para crear la estructura de la base de datos en Supabase, y proveer instrucciones para validar que las tablas y relaciones se crearon correctamente.

## Orden de ejecución
Ejecutar los archivos SQL dentro de la carpeta `db/` en el siguiente orden:

1. `schema.sql`  
   Crea las tablas base (`users`, `routines`, `exercises`, `series`) con sus columnas principales y tipos.

2. `structure.sql`  
   Añade columnas adicionales útiles (por ejemplo `ordering`, `duration_seconds`, `default_rest_seconds`, `weight`, `completed`) que complementan la estructura inicial.

3. `fk.sql`  
   Convierte columnas en NOT NULL cuando corresponda (si no existen valores nulos) y agrega las claves foráneas con reglas ON DELETE / ON UPDATE CASCADE para asegurar integridad referencial entre `users`, `routines`, `exercises` y `series`.

4. `sessions.sql`  
   Crea la tabla `routine_sessions`, usada para trackear sesiones reales de ejecución de rutinas.

> Notas: no ejecutar `test.sql` aquí; ese archivo se usa en la fase de pruebas/seed (sub-issue 3). Antes de aplicar `ALTER COLUMN ... SET NOT NULL` en `fk.sql`, verificar que no existan valores nulos en las columnas objetivo.

## Explicación breve de cada archivo
- `schema.sql`: definición base de tablas y columnas requeridas para la aplicación.
- `structure.sql`: extensiones y atributos que permiten mejorar el seguimiento y lógica de negocio (por ejemplo tiempo por ejercicio, descanso por defecto, peso por serie).
- `fk.sql`: agrega restricciones y claves foráneas para mantener la integridad entre tablas relacionadas.
- `sessions.sql`: tabla para registrar inicios y finales de sesiones de rutina (start, end, total_time_seconds).
- `test.sql` (uso posterior): datos de prueba para validar consultas y flujo (seed).

## Validación en Supabase (pasos recomendados)
1. Abrir SQL Editor en Supabase.
2. Ejecutar los scripts en el orden indicado y copiar/pegar el contenido.
3. Ejecutar estas consultas de verificación:

```sql
-- Ver todas las tablas públicas
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public';

-- Ver el esquema de una tabla (ejemplo: users)
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_name = 'users';

-- Probar select rápido (si ya insertaste datos)
SELECT id, username, email, role FROM users LIMIT 5;
```
El resultado esperado si todo está correcto es que debe aparecer una lista con las tablas creadas en la base de datos incluyendo al menos:
users
routines
exercises
series
routine_sessions

Si faltan tablas, significa que alguno de los archivos SQL no se ejecutó correctamente.

# Supabase: Configuración rápida

## Introducción
Aquí se documentan los pasos para crear el proyecto en Supabase, obtener la información de conexión y usar el SQL Editor para ejecutar los scripts SQL del sprint. Esta guía sirve para que el backend pueda conectarse y para generar evidencia en la entrega.

## Crear el proyecto
1. Ir a https://app.supabase.com y crear una cuenta o iniciar sesión.  
2. Pulsar "New project". Completar los campos: nombre del proyecto, contraseña para la conexión directa de la base de datos y región.

## Dónde encontrar Project URL y las API keys
1. En el dashboard del proyecto, en Settings-Data API se encuentra la Project URL. Para nuestro caso es la siguiente: https://cpsoknlsbxrnawbewtwg.supabase.co
2. En Settings-API están las keys. La anon/public key y la service_role key. Copiar ambas, pero NUNCA exponer por seguiridad la service_role en el frontend. Guardar las keys en variables de entorno del backend.  
3. Para la conexión directa a PostgreSQL ir a Settings-Database. Allí está la Direct connection string, para nuestro caso adjuntamos el ejemplo del formato sin la password por seguiridad.
postgresql://postgres:[YOUR-PASSWORD]@db.cpsoknlsbxrnawbewtwg.supabase.co:5432/postgres. Esa cadena se usa en Spring Boot o en herramientas como PgAdmin.

## Cómo abrir y usar el SQL Editor
1. En el menú lateral del dashboard del proyecto, seleccionar **SQL Editor**.  
2. Crear una nueva consulta, pegar el SQL que se quiera ejecutar por ejemplo `db/schema.sql` y hacer click en **Run**.  
3. Para verificar, después de ejecutar un `INSERT` o `CREATE`, ejecutar `SELECT * FROM users;` y revisar los resultados en la tabla de resultados del editor.

## Ejemplo de credenciales de prueba en SQL Editor
Se puede crear un usuario de prueba ejecutando lo siguiente en el SQL Editor:

INSERT INTO users (username, email, password, role)
VALUES ('testuser','test@example.com','$2b$10$EXAMPLEHASHEDPW','user');

SELECT id, username, email, role, created_at FROM users;

Usuario de prueba:
- username: testuser
- email: test@example.com
- password: (correspondiente al hash usado)

Si SELECT devuelve filas, la tabla y la conexión están correctamente creadas. filas, la tabla y la conexión están correctamente creadas.

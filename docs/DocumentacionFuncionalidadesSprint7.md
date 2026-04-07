Funcionalidades de ejercicios
## Objetivo
Documentar las funcionalidades implementadas durante el Sprint 7 relacionadas con la gestión de ejercicios dentro de las rutinas del sistema NoraFit.

## Alcance
En este sprint se implementaron las operaciones principales para administrar ejercicios asociados a una rutina.  
Las funcionalidades cubren:

- Agregar ejercicios a una rutina  
- Eliminar ejercicios de una rutina  
- Renombrar ejercicios  
- Consultar los ejercicios de una rutina  

Estas operaciones fueron desarrolladas en el backend y también integradas al menú de consola para facilitar su uso y validación.

## Funcionalidades implementadas
### 1. Agregar ejercicio a una rutina
Permite registrar un nuevo ejercicio dentro de una rutina existente.

Método relacionado:  
addExercise(Exercise e)

Descripción:  
Esta funcionalidad recibe un ejercicio y lo asocia a la rutina correspondiente. El sistema guarda la información en la base de datos y actualiza la estructura interna de la rutina.

Resultado esperado:  
El ejercicio queda almacenado y relacionado correctamente con su rutina.


### 2. Eliminar ejercicio de una rutina
Permite borrar un ejercicio previamente registrado.

Método relacionado:  
removeExercise(Long exerciseId)

Descripción:  
Esta funcionalidad elimina un ejercicio a partir de su identificador. Antes de eliminarlo, el sistema valida que el ejercicio exista para evitar errores en la operación.

Resultado esperado:  
El ejercicio desaparece de la rutina y de la base de datos asociada.


### 3. Renombrar ejercicio
Permite modificar el nombre de un ejercicio existente.

Método relacionado:  
renameExercise(String newName)

Descripción:  
Esta funcionalidad actualiza el nombre de un ejercicio dentro del sistema. Se utiliza cuando el usuario necesita corregir o cambiar el nombre registrado inicialmente.

Resultado esperado:  
El ejercicio conserva su información, pero con el nombre actualizado.


### 4. Consultar ejercicios de una rutina
Permite listar los ejercicios asociados a una rutina específica.

Método relacionado:  
getExercises() : List<Exercise>

Descripción:  
Esta funcionalidad obtiene todos los ejercicios relacionados con una rutina y los presenta de forma estructurada para su consulta.

Resultado esperado:  
El sistema muestra la lista de ejercicios disponibles dentro de la rutina consultada.


## Uso desde consola
Las funcionalidades anteriores también se integraron al menú de consola del sistema, lo que permite al usuario interactuar con ellas sin necesidad de usar directamente el código.

Desde la consola se puede:

- Agregar un ejercicio a una rutina  
- Eliminar un ejercicio por su identificador  
- Renombrar un ejercicio  
- Consultar los ejercicios de una rutina  


## Validación general
Durante el desarrollo del sprint se verificó que las operaciones implementadas mantuvieran coherencia con el modelo del sistema y con la estructura del dominio de NoraFit.

Además, se mantuvo trazabilidad del trabajo mediante commits asociados a la issue correspondiente.


## Conclusión
El Sprint 7 permitió ampliar las capacidades del sistema para la gestión de ejercicios dentro de las rutinas, dejando funcionalidades básicas de mantenimiento y consulta que apoyan la evolución de NoraFit como aplicación funcional.

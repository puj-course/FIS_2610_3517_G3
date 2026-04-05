# Sprint 6 - Gestión de Rutinas

## Descripción

En este sprint se implementaron las funcionalidades principales para la gestión de rutinas dentro de **NoraFit**. Estas funcionalidades permiten al usuario administrar sus rutinas personalizadas de entrenamiento de forma sencilla, segura y persistente.

---

## Funcionalidades implementadas

### 1. Crear rutina

Permite al usuario registrar una nueva rutina con un nombre único y su información asociada.

#### Flujo esperado

1. El usuario selecciona la opción **crear rutina**.
2. Ingresa el nombre de la rutina.
3. El sistema valida que el nombre no exista previamente.
4. La rutina se almacena en el sistema.
5. Se muestra un mensaje de confirmación.

#### Ejemplo de uso

```text
Entrada:
Nombre = "Pierna Día 1"

Salida esperada:
Rutina creada exitosamente.
```

---

### 2. Eliminar rutina

Permite borrar una rutina previamente registrada.

#### Flujo esperado

1. El usuario selecciona una rutina existente.
2. Confirma la eliminación.
3. El sistema elimina la rutina.
4. La rutina deja de aparecer en las consultas.

#### Ejemplo de uso

```text
Entrada:
Eliminar = "Pierna Día 1"

Salida esperada:
Rutina eliminada correctamente.
```

---

### 3. Renombrar rutina

Permite actualizar el nombre de una rutina existente.

#### Flujo esperado

1. El usuario selecciona la rutina.
2. Ingresa el nuevo nombre.
3. El sistema valida que el nuevo nombre no exista.
4. Se actualiza la información.
5. Se confirma el cambio.

#### Ejemplo de uso

```text
Entrada:
Anterior = "Pierna Día 1"
Nuevo = "Pierna Avanzada"

Salida esperada:
Rutina renombrada exitosamente.
```

---

### 4. Consultar rutinas

Permite visualizar todas las rutinas registradas por el usuario.

#### Flujo esperado

1. El usuario ingresa a la sección de rutinas.
2. El sistema recupera la lista almacenada.
3. Se muestran nombres y ejercicios asociados.

#### Ejemplo de uso

```text
Salida esperada:
- Full Body
- Push Pull Legs
- Pierna Avanzada
```

---

## Comportamiento esperado del sistema

* No se permiten nombres vacíos.
* No se permiten nombres duplicados.
* Al eliminar una rutina, debe desaparecer de futuras consultas.
* Al renombrar, los cambios deben persistir.
* Las consultas siempre deben reflejar el estado actualizado.

---

## Resultado del sprint

Con estas funcionalidades, NoraFit permite una administración básica pero completa de rutinas, mejorando la personalización del entrenamiento y la experiencia del usuario.

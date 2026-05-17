# Documentación de pantallas implementadas del frontend y flujo de navegación

## Introducción

En este documento se describen las pantallas desarrolladas en el frontend de NoraFit durante el sprint, así como el flujo de navegación implementado entre ellas. Las vistas construidas se enfocan en permitir que el usuario pueda visualizar sus rutinas y consultar la información de los ejercicios asociados de una forma clara y organizada.

## Pantalla "Routines"

La pantalla `Routines` corresponde a la vista principal donde el usuario puede visualizar las rutinas disponibles dentro de la aplicación.

En esta pantalla cada rutina se representa mediante una tarjeta visual que incluye:
- Nombre de la rutina.
- Vista resumida de algunos ejercicios.
- Botón para iniciar o acceder al detalle de la rutina.

El propósito de esta vista es permitir que el usuario identifique rápidamente sus entrenamientos y pueda navegar hacia la información detallada de cada rutina.

## Pantalla "Selected Routine"

La pantalla `Selected Routine` muestra el detalle completo de una rutina seleccionada por el usuario.

Dentro de esta vista se presenta:
- Nombre de la rutina.
- Botón para iniciar la rutina.
- Lista de ejercicios asociados.
- Información relacionada con series, peso y rango de repeticiones.

Esta pantalla permite visualizar de manera organizada la estructura completa del entrenamiento y sirve como punto principal para consultar los ejercicios de la rutina.

## Visualización de ejercicios de fuerza

Dentro de la pantalla `Selected Routine` se implementó la representación visual de ejercicios de fuerza como:
- Bench Press (Barbell)
- Lateral Raise (Dumbbell)
- Lat Pulldown (Cable)

Cada ejercicio muestra información relacionada con:
- Número de series.
- Peso utilizado.
- Rango de repeticiones.
- Tiempo de descanso.

La intención de esta representación es permitir que el usuario comprenda fácilmente la estructura de cada ejercicio dentro de su rutina.

## Flujo de navegación entre pantallas

El flujo de navegación implementado durante el sprint funciona de la siguiente manera:

1. El usuario ingresa a la pantalla `Routines`.
2. Selecciona una de las rutinas disponibles.
3. El sistema redirige a la pantalla `Selected Routine`.
4. Dentro de esta pantalla el usuario puede visualizar los ejercicios asociados a la rutina seleccionada.

Este flujo permite que la navegación dentro de NoraFit sea clara, progresiva y coherente con la organización del sistema.

## Relación con el modelo del sistema

Las pantallas desarrolladas en Flutter mantienen coherencia con la estructura definida en el backend y en el diagrama de clases del proyecto.

La pantalla `Routines` se relaciona con la entidad `Routine`, mientras que la vista `Selected Routine` refleja la relación entre rutinas y ejercicios definida en el modelo del dominio. Los ejercicios mostrados corresponden principalmente a ejercicios de fuerza relacionados con la entidad `StrengthExercise`.

De esta forma, el frontend representa visualmente la estructura lógica ya existente dentro del sistema.

## Conclusión

Las pantallas implementadas durante este sprint permiten que NoraFit cuente con una interfaz inicial funcional para la visualización de rutinas y ejercicios. Además, el flujo de navegación construido facilita la interacción del usuario con la aplicación y mantiene coherencia con la estructura general del proyecto.
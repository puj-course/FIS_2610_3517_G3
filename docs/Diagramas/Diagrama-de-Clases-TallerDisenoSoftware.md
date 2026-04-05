# Descripción del diagrama de clases

El diagrama de clases representa la estructura estática del sistema y muestra, de forma clara, las entidades principales que componen el dominio de la aplicación. En él se identifican las clases **Users**, **Routine**, **Statistics**, **Exercise** (abstracta), **StrengthExercise**, **CardioExercise** (abstracta), **StrengthSeries**, **HIITCardio** y **SimpleCardio**, cada una con sus respectivos **atributos**, **métodos** y **niveles de visibilidad**.

La notación utilizada evidencia la encapsulación del modelo: los atributos aparecen con visibilidad privada (`-`), mientras que las operaciones o métodos están expuestos con visibilidad pública (`+`). Esto permite observar de manera ordenada cómo se estructura la lógica del sistema y cómo cada clase cumple un rol específico dentro del modelo.

## Relaciones entre clases

El diagrama muestra relaciones correctas y coherentes con la lógica del dominio:

- **Users — Routine**: un usuario puede tener **0..*** rutinas, mientras que cada rutina pertenece a **1** usuario. Esto refleja que un usuario puede crear y administrar varias rutinas.
- **Users — Statistics**: cada usuario posee **1** conjunto de estadísticas, lo que permite almacenar y consultar su progreso dentro del sistema.
- **Routine — Exercise**: una rutina contiene **1..*** ejercicios, indicando que una rutina debe estar conformada por al menos un ejercicio.
- **Exercise** actúa como clase **abstracta base**, de la cual heredan los tipos de ejercicio especializados.
- **StrengthExercise** y **CardioExercise** heredan de **Exercise**, permitiendo extender el comportamiento general sin modificar la estructura base.
- **StrengthExercise — StrengthSeries**: existe una relación de composición/agregación donde un ejercicio de fuerza puede contener **0..*** series, lo que permite modelar la estructura interna del entrenamiento.
- **HIITCardio** y **SimpleCardio** heredan de **CardioExercise**, especializando los ejercicios cardiovasculares según su tipo.

Estas relaciones están modeladas con multiplicidades que permiten entender con precisión cómo interactúan los objetos dentro del sistema y cómo se organizan los datos en el dominio.

## Aplicación de principios SOLID

El diagrama también evidencia la aplicación de principios de diseño orientado a objetos, especialmente los principios SOLID:

### 1. **Single Responsibility Principle (SRP)**
Cada clase tiene una responsabilidad bien definida.  
- **Users** se encarga de autenticación, perfil y gestión de rutinas.
- **Routine** administra la información y organización de los ejercicios.
- **Exercise** concentra los datos comunes de cualquier ejercicio.
- **StrengthExercise** y **CardioExercise** manejan comportamientos específicos de cada tipo.
- **Statistics** se encarga del seguimiento y reporte del progreso.

Esto reduce la complejidad y facilita el mantenimiento del sistema.

### 2. **Open/Closed Principle (OCP)**
El modelo está abierto a extensión pero cerrado a modificación.  
Si en el futuro se desea agregar un nuevo tipo de ejercicio, basta con crear una nueva subclase heredada de **Exercise** o **CardioExercise**, sin necesidad de alterar la estructura general del sistema.

### 3. **Liskov Substitution Principle (LSP)**
Las subclases pueden reemplazar a su clase base sin romper el comportamiento del sistema.  
Por ejemplo, **HIITCardio** y **SimpleCardio** pueden ser tratados como ejercicios tipo **CardioExercise**, y cualquier lógica que trabaje con **Exercise** puede interactuar con sus subtipos de manera transparente.

### 4. **Interface Segregation Principle (ISP)**
El diseño evita que una clase dependa de métodos que no necesita.  
Las funcionalidades específicas de fuerza y cardio están separadas en clases especializadas, evitando que un único objeto concentre comportamientos innecesarios para otros tipos de ejercicio.

### 5. **Dependency Inversion Principle (DIP)**
Las clases de mayor nivel dependen de abstracciones y no de implementaciones concretas.  
En particular, **Routine** trabaja con la abstracción **Exercise**, lo cual permite manejar distintos tipos de ejercicios sin acoplarse a una implementación específica.

## Conclusión

En conjunto, este diagrama de clases presenta una estructura completa, organizada y coherente con los requerimientos del sistema. Incluye clases bien definidas, atributos y métodos con visibilidad adecuada, relaciones con multiplicidades correctas y una jerarquía de herencia que permite modelar de forma flexible los distintos tipos de ejercicios. Además, se evidencia la aplicación de principios SOLID, lo cual fortalece la modularidad, mantenibilidad y escalabilidad del proyecto.

<img width="6818" height="8192" alt="Animal Class Hierarchy Model-2026-03-19-164924" src="https://github.com/user-attachments/assets/32b01531-e491-431c-902d-903debfd976b" />

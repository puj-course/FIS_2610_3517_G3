![Diagrama de clases](docs/Diagramas/diagramas_clase/UserManagementRoutine.jpg)

### Diagrama de clases – Patrón Factory

El siguiente diagrama de clases representa la implementación del patrón de diseño **Factory Method** dentro del sistema, evidenciando cómo se resuelve el problema de rigidez en la creación de objetos de tipo ejercicio.

En la solución propuesta, se introduce una abstracción denominada `ExerciseFactory`, la cual define el contrato común para la creación de objetos `Exercise`. Esta interfaz establece el método `createExercise`, permitiendo desacoplar la lógica de instanciación del resto del sistema.

A partir de esta interfaz, se implementan múltiples fábricas concretas como `StrengthExerciseFactory`, `SimpleCardioExerciseFactory` y `HIITCardioExerciseFactory`. Cada una de estas clases es responsable de crear un tipo específico de ejercicio, encapsulando la lógica de construcción y configuración de sus atributos particulares.

El diagrama también evidencia cómo las clases del sistema, como la capa de servicios o la consola, ya no dependen directamente de las clases concretas de ejercicio, sino de la abstracción `ExerciseFactory`. Esto permite que el sistema sea más flexible y extensible, ya que la incorporación de nuevos tipos de ejercicio no requiere modificar el código existente, sino simplemente añadir una nueva fábrica concreta.

Adicionalmente, se observa que la responsabilidad de creación se delega completamente a las fábricas, cumpliendo con el principio de responsabilidad única (SRP) y reduciendo el acoplamiento entre componentes. Esto mejora significativamente la mantenibilidad del sistema y facilita su evolución a futuro.

En conjunto, el diagrama demuestra cómo el patrón Factory Method permite estructurar de manera limpia la creación de objetos complejos, promoviendo una arquitectura más modular, escalable y alineada con buenas prácticas de diseño de software.

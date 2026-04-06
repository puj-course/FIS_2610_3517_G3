DOCUMENTACION DEL MODELO DE DATOS
Sistema de Rutinas de Ejercicio

DESCRIPCION GENERAL
El modelo de datos representa un sistema para gestionar rutinas de entrenamiento fisico de forma personalizada. Esta estructurado alrededor de tres conceptos principales: el usuario que crea y administra sus rutinas, las rutinas que agrupan una serie de ejercicios, y los ejercicios, que pueden ser de tipo fuerza o cardio y presentan atributos especializados segun su naturaleza.

La arquitectura del modelo refleja la jerarquia de herencia definida en el diagrama de clases del sistema, traducida a un esquema relacional mediante la estrategia de tabla por subclase (Table Per Subclass). Esta estrategia consiste en crear una tabla por cada clase de la jerarquia, donde las tablas hijas comparten el mismo identificador que la tabla padre, usandolo como llave primaria y foranea al mismo tiempo.


ENTIDADES PRINCIPALES


USERS

Representa a los usuarios registrados en el sistema. Es la entidad raiz desde la que se desprende toda la estructura de datos. Sus atributos principales son el id como llave primaria autoincremental, el username y el email que identifican al usuario de forma unica, la password almacenada de forma cifrada, el role que define el nivel de acceso dentro del sistema, y el created_at que registra la fecha de registro.


ROUTINES

Representa una rutina de entrenamiento creada por un usuario. Agrupa un conjunto de ejercicios y almacena metadatos como el nombre de la rutina, el tiempo total acumulado en segundos y la fecha de creacion. Su llave primaria es el id y su llave foranea es user_id, que referencia a USERS.

La razon de esta relacion es que un usuario puede tener muchas rutinas, pero cada rutina pertenece a un unico usuario. Esto establece una relacion de uno a muchos entre USERS y ROUTINES. La llave foranea user_id garantiza la integridad referencial, impidiendo que una rutina exista sin un usuario propietario.


EXERCISES

Es la tabla base que representa el concepto abstracto de ejercicio. Dado que en el sistema existen distintos tipos de ejercicios, esta tabla actua como entidad padre en el esquema de herencia relacional. Sus atributos son el id como llave primaria, el exercise_name, la description, el exercise_type que funciona como discriminador del tipo de ejercicio, y el routine_id como llave foranea que referencia a ROUTINES.

La razon de esta relacion es que una rutina puede componerse de muchos ejercicios, y cada ejercicio pertenece a exactamente una rutina, estableciendo una relacion de uno a muchos entre ROUTINES y EXERCISES.


STRENGTH_EXERCISES

Extiende la entidad EXERCISES con los atributos propios de un ejercicio de fuerza. Su unico atributo adicional es has_weight, que indica si el ejercicio utiliza peso externo. Su llave primaria es exercise_id, que al mismo tiempo es llave foranea que referencia a EXERCISES.

La razon de usar el mismo id del ejercicio padre como llave primaria y foranea es garantizar que cada ejercicio de fuerza este vinculado exactamente a un ejercicio base y que no pueda existir de forma aislada. Esta es la implementacion de la herencia: STRENGTH_EXERCISES es una especializacion de EXERCISES.


STRENGTH_SERIES

Representa cada serie individual dentro de un ejercicio de fuerza. Sus atributos son el id como llave primaria, el series_number que indica el orden de la serie, las repetitions, el weight en kilogramos, el rest_time_seconds para el descanso posterior, y el strength_exercise_id como llave foranea que referencia a STRENGTH_EXERCISES.

La razon de esta relacion es que un ejercicio de fuerza puede contener varias series con distintas configuraciones, estableciendo una relacion de uno a muchos entre STRENGTH_EXERCISES y STRENGTH_SERIES. Separar las series en su propia tabla permite configurar cada una de forma independiente.


CARDIO_EXERCISES

Extiende la entidad EXERCISES con los atributos comunes a los ejercicios cardiovasculares, y a su vez actua como entidad padre para los dos tipos especificos de cardio. Sus atributos adicionales son duration_minutes, intensity, machine_type y cardio_type, este ultimo como discriminador del subtipo de cardio. Su llave primaria exercise_id es tambien llave foranea que referencia a EXERCISES.

La razon es la misma que para STRENGTH_EXERCISES: implementa la herencia desde EXERCISES. Adicionalmente, centraliza los atributos comunes a todos los ejercicios cardio para no duplicarlos en las tablas hijas.


HIIT_CARDIO

Especializacion de CARDIO_EXERCISES para ejercicios de tipo HIIT (High Intensity Interval Training). Sus atributos son rounds para el numero de rondas, work_time_seconds para la duracion de cada intervalo de trabajo, y rest_time_seconds para la duracion de cada intervalo de descanso. Su llave primaria exercise_id es tambien llave foranea que referencia a CARDIO_EXERCISES.

Es un segundo nivel de herencia. La cadena completa es EXERCISES -> CARDIO_EXERCISES -> HIIT_CARDIO. Cada nivel agrega solo los atributos propios de ese tipo sin repetir los del nivel superior.


SIMPLE_CARDIO

Especializacion de CARDIO_EXERCISES para ejercicios cardiovasculares de estado continuo. Sus atributos son distance_km para la distancia recorrida, average_speed para la velocidad promedio, e incline_level para el nivel de inclinacion de la maquina. Su llave primaria exercise_id es tambien llave foranea que referencia a CARDIO_EXERCISES.

Sigue el mismo patron de herencia que HIIT_CARDIO pero con atributos orientados al cardio de baja a media intensidad. La separacion en tabla propia evita que existan columnas nulas en una tabla unificada, manteniendo el esquema normalizado.


RESUMEN DE RELACIONES

USERS a ROUTINES: uno a muchos. Un usuario puede tener muchas rutinas, pero cada rutina pertenece a un solo usuario.

ROUTINES a EXERCISES: uno a muchos. Una rutina puede contener muchos ejercicios, pero cada ejercicio pertenece a una sola rutina.

EXERCISES a STRENGTH_EXERCISES: uno a cero o uno. Un ejercicio puede especializarse como ejercicio de fuerza mediante herencia.

EXERCISES a CARDIO_EXERCISES: uno a cero o uno. Un ejercicio puede especializarse como ejercicio cardio mediante herencia.

STRENGTH_EXERCISES a STRENGTH_SERIES: uno a muchos. Un ejercicio de fuerza puede tener muchas series configuradas.

CARDIO_EXERCISES a HIIT_CARDIO: uno a cero o uno. Un ejercicio cardio puede especializarse como HIIT.

CARDIO_EXERCISES a SIMPLE_CARDIO: uno a cero o uno. Un ejercicio cardio puede especializarse como cardio simple.


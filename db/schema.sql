--BASE DE DATOS
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE routines (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    routine_name VARCHAR(100) NOT NULL,
    total_time_seconds INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE exercises (
    id INT PRIMARY KEY AUTO_INCREMENT,
    routine_id INT,
    exercise_name VARCHAR(100) NOT NULL,
    description TEXT,
    FOREIGN KEY (routine_id) REFERENCES routines(id)
);

CREATE TABLE series (
    id INT PRIMARY KEY AUTO_INCREMENT,
    exercise_id INT,
    series_number INT NOT NULL,
    repetitions INT NOT NULL,
    rest_time_seconds INT,
    FOREIGN KEY (exercise_id) REFERENCES exercises(id)
);

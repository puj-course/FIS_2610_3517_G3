INSERT INTO users (username, email, password, role) VALUES ('testuser','t@t.com','hashedpw','user');
-- Supón id=1
INSERT INTO routines (user_id, routine_name, total_time_seconds) VALUES (1,'Full Body',3600);
-- Supón routine id=1
INSERT INTO exercises (routine_id, exercise_name, description, ordering) VALUES (1,'Squat','Sentadilla',1);
-- Supón exercise id=1
INSERT INTO series (exercise_id, series_number, repetitions, rest_time_seconds, weight) VALUES (1,1,12,60,40.00);

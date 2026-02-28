-- FOREIGN KEY en routines
ALTER TABLE routines
  MODIFY user_id INT NOT NULL,
  ADD CONSTRAINT fk_routines_user
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;

-- FOREIGN KEY en exercises
ALTER TABLE exercises
  MODIFY routine_id INT NOT NULL,
  ADD CONSTRAINT fk_exercises_routine
  FOREIGN KEY (routine_id) REFERENCES routines(id) ON DELETE CASCADE ON UPDATE CASCADE;

-- FOREIGN KEY en series
ALTER TABLE series
  MODIFY exercise_id INT NOT NULL,
  ADD CONSTRAINT fk_series_exercise
  FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE CASCADE ON UPDATE CASCADE;

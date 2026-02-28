CREATE TABLE IF NOT EXISTS routine_sessions (
  id INT PRIMARY KEY AUTO_INCREMENT,
  routine_id INT NOT NULL,
  user_id INT NOT NULL,
  started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  ended_at TIMESTAMP NULL,
  total_time_seconds INT NULL,
  CONSTRAINT fk_sessions_routine FOREIGN KEY (routine_id) REFERENCES routines(id) ON DELETE CASCADE,
  CONSTRAINT fk_sessions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

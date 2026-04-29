-- Online Exam System Schema

CREATE DATABASE IF NOT EXISTS exam_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE exam_system;

-- Users table
CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  real_name VARCHAR(50),
  phone VARCHAR(20),
  class_name VARCHAR(50),
  role VARCHAR(20) NOT NULL DEFAULT 'STUDENT',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Courses table
CREATE TABLE IF NOT EXISTS courses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  teacher_id BIGINT,
  description TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (teacher_id) REFERENCES users(id)
);

-- Questions table
CREATE TABLE IF NOT EXISTS questions (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_id BIGINT NOT NULL,
  type VARCHAR(20) NOT NULL,
  content TEXT NOT NULL,
  options JSON,
  answer VARCHAR(200) NOT NULL,
  analysis TEXT,
  difficulty INT DEFAULT 1,
  creator_id BIGINT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- Papers table
CREATE TABLE IF NOT EXISTS papers (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  course_id BIGINT NOT NULL,
  creator_id BIGINT NOT NULL,
  total_score INT DEFAULT 100,
  duration INT DEFAULT 120,
  start_time DATETIME,
  end_time DATETIME,
  published TINYINT DEFAULT 0,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- Paper-Question association
CREATE TABLE IF NOT EXISTS paper_questions (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  paper_id BIGINT NOT NULL,
  question_id BIGINT NOT NULL,
  sort_order INT DEFAULT 0,
  score INT DEFAULT 1,
  FOREIGN KEY (paper_id) REFERENCES papers(id) ON DELETE CASCADE,
  FOREIGN KEY (question_id) REFERENCES questions(id)
);

-- Student answers
CREATE TABLE IF NOT EXISTS student_answers (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  student_id BIGINT NOT NULL,
  paper_id BIGINT NOT NULL,
  question_id BIGINT NOT NULL,
  answer VARCHAR(500),
  is_correct TINYINT,
  score INT DEFAULT 0,
  graded TINYINT DEFAULT 0,
  submitted_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (student_id) REFERENCES users(id),
  FOREIGN KEY (paper_id) REFERENCES papers(id),
  FOREIGN KEY (question_id) REFERENCES questions(id)
);

-- Insert default admin user
INSERT IGNORE INTO users(username, password, real_name, role) VALUES('admin', 'admin123', '系统管理员', 'ADMIN');
INSERT IGNORE INTO users(username, password, real_name, role) VALUES('teacher1', '123456', '张老师', 'TEACHER');
INSERT IGNORE INTO users(username, password, real_name, class_name, role) VALUES('student1', '123456', '李同学', '计算机1班', 'STUDENT');

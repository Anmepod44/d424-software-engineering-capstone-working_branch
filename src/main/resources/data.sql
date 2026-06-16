-- Sample data for WGU Task Management System
-- Passwords are BCrypt encoded and correspond to plaintext: password123
-- Safe to re-run: all inserts are guarded against duplicates

-- Users
INSERT INTO users (username, email, password, role, created_at, updated_at)
SELECT 'admin', 'admin@taskmanagement.com',
       '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe',
       'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, email, password, role, created_at, updated_at)
SELECT 'john.doe', 'john.doe@example.com',
       '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe',
       'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'john.doe');

INSERT INTO users (username, email, password, role, created_at, updated_at)
SELECT 'jane.smith', 'jane.smith@example.com',
       '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe',
       'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'jane.smith');

-- Projects
INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Website Redesign', 'Redesign company website with modern UI',
       1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Website Redesign');

INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Mobile App Development', 'Develop iOS and Android mobile applications',
       2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Mobile App Development');

INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Database Migration', 'Migrate legacy database to PostgreSQL',
       1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Database Migration');

-- Tasks
INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Design homepage mockup', 'Create wireframes and mockups for new homepage',
       'IN_PROGRESS', 'HIGH', CURRENT_TIMESTAMP + INTERVAL '7 days', 1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Design homepage mockup');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Implement user authentication', 'Add login and registration functionality',
       'PENDING', 'URGENT', CURRENT_TIMESTAMP + INTERVAL '3 days', 2, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Implement user authentication');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Write API documentation', 'Document all REST API endpoints',
       'COMPLETED', 'MEDIUM', CURRENT_TIMESTAMP - INTERVAL '2 days', 2, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Write API documentation');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Set up PostgreSQL database', 'Install and configure PostgreSQL server',
       'IN_PROGRESS', 'HIGH', CURRENT_TIMESTAMP + INTERVAL '5 days', 3, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Set up PostgreSQL database');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Migrate user data', 'Transfer user data from old database',
       'PENDING', 'MEDIUM', CURRENT_TIMESTAMP + INTERVAL '10 days', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Migrate user data');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Update navigation menu', 'Redesign and implement new navigation',
       'PENDING', 'LOW', CURRENT_TIMESTAMP + INTERVAL '14 days', 1, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Update navigation menu');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Test mobile app on devices', 'Test app on various iOS and Android devices',
       'PENDING', 'HIGH', CURRENT_TIMESTAMP + INTERVAL '8 days', 2, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Test mobile app on devices');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Optimize database queries', 'Improve performance of slow queries',
       'CANCELLED', 'LOW', CURRENT_TIMESTAMP - INTERVAL '5 days', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Optimize database queries');

-- =============================================================================
-- init-db.sql
-- WGU Task Management System – Database Seed Data
-- Runs automatically on first PostgreSQL container startup.
-- All passwords are BCrypt encoded and correspond to: password123
-- =============================================================================

-- Insert sample users (skip if already present)
INSERT INTO users (username, email, password, role, created_at, updated_at)
SELECT 'admin', 'admin@taskmanagement.com',
       '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe',
       'ADMIN', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, email, password, role, created_at, updated_at)
SELECT 'john.doe', 'john.doe@example.com',
       '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe',
       'USER', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'john.doe');

INSERT INTO users (username, email, password, role, created_at, updated_at)
SELECT 'jane.smith', 'jane.smith@example.com',
       '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe',
       'USER', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'jane.smith');

-- Insert sample projects (skip if already present)
INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Website Redesign', 'Redesign company website with modern UI', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Website Redesign');

INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Mobile App Development', 'Develop iOS and Android mobile applications', 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Mobile App Development');

INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Database Migration', 'Migrate legacy database to PostgreSQL', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Database Migration');

-- Insert sample tasks (skip if already present)
INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Design homepage mockup', 'Create wireframes and mockups for new homepage',
       'IN_PROGRESS', 'HIGH', NOW() + INTERVAL '7 days', 1, 2, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Design homepage mockup');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Implement user authentication', 'Add login and registration functionality',
       'PENDING', 'URGENT', NOW() + INTERVAL '3 days', 2, 3, 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Implement user authentication');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Write API documentation', 'Document all REST API endpoints',
       'COMPLETED', 'MEDIUM', NOW() - INTERVAL '2 days', 2, 2, 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Write API documentation');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Set up PostgreSQL database', 'Install and configure PostgreSQL server',
       'IN_PROGRESS', 'HIGH', NOW() + INTERVAL '5 days', 3, 3, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Set up PostgreSQL database');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Migrate user data', 'Transfer user data from old database',
       'PENDING', 'MEDIUM', NOW() + INTERVAL '10 days', 3, 2, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Migrate user data');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Update navigation menu', 'Redesign and implement new navigation',
       'PENDING', 'LOW', NOW() + INTERVAL '14 days', 1, 3, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Update navigation menu');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Test mobile app on devices', 'Test app on various iOS and Android devices',
       'PENDING', 'HIGH', NOW() + INTERVAL '8 days', 2, 3, 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Test mobile app on devices');

INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at)
SELECT 'Optimize database queries', 'Improve performance of slow queries',
       'CANCELLED', 'LOW', NOW() - INTERVAL '5 days', 3, 2, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE title = 'Optimize database queries');

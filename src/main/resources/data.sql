-- Sample data for WGU Task Management System
-- Passwords are BCrypt encoded: "password123"

-- Insert sample users
INSERT INTO users (username, email, password, role, created_at, updated_at) VALUES
('admin', 'admin@taskmanagement.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('john.doe', 'john.doe@example.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jane.smith', 'jane.smith@example.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5gyg0JGvzb6Oe', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample projects
INSERT INTO projects (name, description, owner_id, created_at, updated_at) VALUES
('Website Redesign', 'Redesign company website with modern UI', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mobile App Development', 'Develop iOS and Android mobile applications', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Database Migration', 'Migrate legacy database to PostgreSQL', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample tasks
INSERT INTO tasks (title, description, status, priority, due_date, project_id, assigned_to, created_by, created_at, updated_at) VALUES
('Design homepage mockup', 'Create wireframes and mockups for new homepage', 'IN_PROGRESS', 'HIGH', CURRENT_TIMESTAMP + INTERVAL '7' DAY, 1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Implement user authentication', 'Add login and registration functionality', 'PENDING', 'URGENT', CURRENT_TIMESTAMP + INTERVAL '3' DAY, 2, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Write API documentation', 'Document all REST API endpoints', 'COMPLETED', 'MEDIUM', CURRENT_TIMESTAMP - INTERVAL '2' DAY, 2, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Set up PostgreSQL database', 'Install and configure PostgreSQL server', 'IN_PROGRESS', 'HIGH', CURRENT_TIMESTAMP + INTERVAL '5' DAY, 3, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Migrate user data', 'Transfer user data from old database', 'PENDING', 'MEDIUM', CURRENT_TIMESTAMP + INTERVAL '10' DAY, 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Update navigation menu', 'Redesign and implement new navigation', 'PENDING', 'LOW', CURRENT_TIMESTAMP + INTERVAL '14' DAY, 1, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Test mobile app on devices', 'Test app on various iOS and Android devices', 'PENDING', 'HIGH', CURRENT_TIMESTAMP + INTERVAL '8' DAY, 2, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Optimize database queries', 'Improve performance of slow queries', 'CANCELLED', 'LOW', CURRENT_TIMESTAMP - INTERVAL '5' DAY, 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

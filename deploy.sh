#!/bin/bash
# =============================================================================
# deploy.sh
# WGU Task Management System – EC2 Ubuntu Deployment Script
# =============================================================================

set -e

echo "============================================"
echo " WGU Task Management System – Deployment"
echo "============================================"

# --------------------------------------------------------------------------
# 1. Update system packages
# --------------------------------------------------------------------------
echo "[1/7] Updating system packages..."
sudo apt-get update -y
sudo apt-get upgrade -y

# --------------------------------------------------------------------------
# 2. Install Docker
# --------------------------------------------------------------------------
echo "[2/7] Installing Docker..."
sudo apt-get install -y ca-certificates curl gnupg lsb-release

sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | \
  sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
  https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker ubuntu
echo "  Docker installed: $(docker --version)"

# --------------------------------------------------------------------------
# 3. Install Docker Compose
# --------------------------------------------------------------------------
echo "[3/7] Installing Docker Compose..."
sudo curl -SL "https://github.com/docker/compose/releases/download/v2.24.0/docker-compose-linux-x86_64" \
  -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
echo "  Docker Compose installed: $(docker-compose --version)"

# --------------------------------------------------------------------------
# 4. Install Git
# --------------------------------------------------------------------------
echo "[4/7] Installing Git..."
sudo apt-get install -y git
echo "  Git installed: $(git --version)"

# --------------------------------------------------------------------------
# 5. Clone the repository
# --------------------------------------------------------------------------
echo "[5/7] Cloning repository..."

REPO_URL="https://github.com/Anmepod44/d424-software-engineering-capstone-working_branch.git"
APP_DIR="task-management-system"

if [ -d "$APP_DIR" ]; then
  echo "  Directory already exists – pulling latest changes..."
  cd "$APP_DIR"
  git pull
else
  git clone "$REPO_URL" "$APP_DIR"
  cd "$APP_DIR"
fi

git checkout working_branch 2>/dev/null || echo "  Already on working_branch – continuing."

# --------------------------------------------------------------------------
# 6. Build and start containers
# --------------------------------------------------------------------------
echo "[6/7] Building Docker image and starting containers..."
docker-compose down --remove-orphans 2>/dev/null || true
docker-compose up --build -d

# --------------------------------------------------------------------------
# 7. Wait for app to be ready, then seed the database
# --------------------------------------------------------------------------
echo "[7/7] Waiting for application to be ready..."

# Wait until the Spring Boot app responds on port 8080 (max 120 seconds)
MAX_WAIT=120
ELAPSED=0
until curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/login | grep -qE "200|302"; do
  if [ "$ELAPSED" -ge "$MAX_WAIT" ]; then
    echo "  ERROR: Application did not start within ${MAX_WAIT}s. Check logs with: docker-compose logs app"
    exit 1
  fi
  echo "  Still waiting... (${ELAPSED}s)"
  sleep 5
  ELAPSED=$((ELAPSED + 5))
done

echo "  Application is up. Seeding database..."

# Seed data – INSERT ... WHERE NOT EXISTS is safe to re-run on redeploys
docker exec taskmanagement-db psql -U taskuser -d taskmanagement <<'EOSQL'

-- Users (password for all accounts: password123)
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

-- Projects
INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Website Redesign', 'Redesign company website with modern UI', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Website Redesign');

INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Mobile App Development', 'Develop iOS and Android mobile applications', 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Mobile App Development');

INSERT INTO projects (name, description, owner_id, created_at, updated_at)
SELECT 'Database Migration', 'Migrate legacy database to PostgreSQL', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Database Migration');

-- Tasks
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

EOSQL

echo ""
echo "============================================"
echo " Deployment complete!"
echo " Application: http://$(curl -s http://169.254.169.254/latest/meta-data/public-ipv4):8080"
echo ""
echo " Login credentials:"
echo "   admin      / password123  (ADMIN)"
echo "   john.doe   / password123  (USER)"
echo "   jane.smith / password123  (USER)"
echo "============================================"

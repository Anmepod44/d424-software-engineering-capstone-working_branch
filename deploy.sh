#!/bin/bash
# =============================================================================
# deploy.sh
# WGU Task Management System – EC2 Ubuntu Deployment Script
# Installs dependencies, clones the repo, and starts the application
# using Docker and Docker Compose.
# Run as: bash deploy.sh
# =============================================================================

set -e  # Exit immediately on any error

echo "============================================"
echo " WGU Task Management System – Deployment"
echo "============================================"

# --------------------------------------------------------------------------
# 1. Update system packages
# --------------------------------------------------------------------------
echo "[1/6] Updating system packages..."
sudo apt-get update -y
sudo apt-get upgrade -y

# --------------------------------------------------------------------------
# 2. Install Docker
# --------------------------------------------------------------------------
echo "[2/6] Installing Docker..."
sudo apt-get install -y ca-certificates curl gnupg lsb-release

# Add Docker's official GPG key
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | \
  sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# Add the Docker apt repository
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
  https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io

# Start and enable Docker
sudo systemctl start docker
sudo systemctl enable docker

# Add ubuntu user to the docker group (avoids needing sudo for docker commands)
sudo usermod -aG docker ubuntu
echo "  Docker installed: $(docker --version)"

# --------------------------------------------------------------------------
# 3. Install Docker Compose
# --------------------------------------------------------------------------
echo "[3/6] Installing Docker Compose..."
sudo curl -SL "https://github.com/docker/compose/releases/download/v2.24.0/docker-compose-linux-x86_64" \
  -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
echo "  Docker Compose installed: $(docker-compose --version)"

# --------------------------------------------------------------------------
# 4. Install Git
# --------------------------------------------------------------------------
echo "[4/6] Installing Git..."
sudo apt-get install -y git
echo "  Git installed: $(git --version)"

# --------------------------------------------------------------------------
# 5. Clone the repository
# --------------------------------------------------------------------------
echo "[5/6] Cloning repository..."

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

# Check out the working branch
git checkout working_branch 2>/dev/null || echo "  Already on working_branch – continuing."

# --------------------------------------------------------------------------
# 6. Build and start with Docker Compose
# --------------------------------------------------------------------------
echo "[6/6] Building Docker image and starting containers..."
docker-compose down --remove-orphans 2>/dev/null || true
docker-compose up --build -d

echo ""
echo "============================================"
echo " Deployment complete!"
echo " Application is running at:"
echo " http://$(curl -s http://169.254.169.254/latest/meta-data/public-ipv4):8080"
echo "============================================"

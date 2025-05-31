# Music Microservices Project

This project splits a monolithic Spring Boot music service into four independent microservices—each handling a single REST operation (POST, GET, PUT, DELETE)—with a shared MariaDB backend. The system is containerized with Docker and orchestrated using Kubernetes (kind + NGINX Ingress).

## Microservices
- **post-song-microservice**: Handles POST requests to add new songs.
- **get-song-microservice**: Handles GET requests to retrieve songs.
- **put-song-microservice**: Handles PUT requests to update songs.
- **delete-song-microservice**: Handles DELETE requests to remove songs.

Each microservice has its own:
- Spring Boot controller
- `build.gradle` (with consistent dependencies)
- Dockerfile (multi-stage, Java 21)
- Kubernetes manifests (Deployment, Service)

## Database
- **MariaDB** is used as a shared backend, deployed with persistent storage and backup via Kubernetes manifests.

## Kubernetes & Ingress
- All microservices are exposed via an NGINX Ingress at `/api/songs/{operation}` (e.g., `/api/songs/post`).
- Ingress is configured to preserve RESTful paths (no rewrite).

## Scripts
- `build-images.sh`: Builds Docker images for all microservices.
- `setup.sh`: Builds images, (optionally) pushes to Docker Hub, creates the kind cluster, loads images, deploys all manifests, and sets up Ingress.
- `verify-endpoints.sh`: Tests all endpoints via Ingress, with robust feedback and error handling.
- `deploy-on-change.sh`: Watches for changes and redeploys as needed.

## Quick Start
1. **Build and Deploy**
   ```bash
   ./setup.sh
   ```
2. **Verify Endpoints**
   ```bash
   ./verify-endpoints.sh
   ```
3. **Access Services**
   - Use the following endpoints (via Ingress):
     - `POST   /api/songs/post`   (add song)
     - `GET    /api/songs/get`    (list songs)
     - `PUT    /api/songs/put/{id}` (update song)
     - `DELETE /api/songs/delete/{id}` (delete song)

## Development Notes
- All microservices use the same MariaDB credentials (`music`/`music`).
- Images are tagged as `n3vers4ydie/{service}:latest` and use `imagePullPolicy: Always`.
- For local development, ensure Docker, kubectl and kind are installed.
- To clean up: `kind delete cluster --name music`

# Étape 1 : Build
FROM node:18-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build:ssr

# Étape 2 : Serveur SSR
FROM node:18-alpine

WORKDIR /app

COPY --from=builder /app/package*.json ./
COPY --from=builder /app/dist ./dist
COPY --from=builder /app/.angular/cache ./angular-cache

RUN npm ci --omit=dev

EXPOSE 4000

CMD ["node", "dist/frontend/server/main.js"]

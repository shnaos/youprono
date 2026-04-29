# YouProno API

API Java Spring Boot pour YouProno, permettant de :

- Stocker les conversations générées par l’IA (MongoDB)
- Gérer les informations liées aux matchs (PostgreSQL)
- Générer des analyses de matchs, des pages d'équipes, etc.

## 🔧 Prérequis

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- (Facultatif) Un IDE Java (IntelliJ, VS Code avec extensions Java)
- Java 21 & Maven sont intégrés via Docker (aucune installation locale nécessaire)

---

## 🚀 Lancement du projet en local

### 1. Cloner le projet

```bash
git clone https://gitlab.com/leroudier.raphael/youpronoapi.git
cd youpronoapi
```

### 2. Copier le fichier `.env`

```bash
cp .env.example .env
```

Modifiez-le au besoin pour vos propres ports ou credentials.

### 3. Lancer les services

```bash
docker compose up --build
```

⏱️ Le backend sera accessible sur : [http://localhost:8080](http://localhost:8080)

MongoDB : `localhost:27017`  
PostgreSQL : `localhost:5432`

---

## 📁 Structure du projet

```
.
├── src/                 # Code source Java (Spring Boot)
├── Dockerfile           # Image app Spring
├── Dockerfile.dev       # Pour le dev avec reload auto
├── docker-compose.yml   # Compose multi-services (Postgres, Mongo, API)
├── .env.example         # Exemple de fichier d'environnement
├── application.yml      # Config Spring Boot
└── README.md
```

---

## 📌 Emplacement des fichiers

📂 src/main/java/house/wammys/youpronoapi/

- 📁 controller/ → Contient les contrôleurs REST (MatchController.java...)
- 📁 model/ → Contient les entités et modèles (Match.java, BasePostgresEntity.java...)
- 📁 repository/ → Contient les repositories JPA et MongoDB (MatchRepository.java...)
- 📁 service/ → Contient la logique métier (MatchService.java...)

📂 src/main/resources/

- 📄 application.yml → Fichier de configuration Spring Boot
- 📁 static/ et templates/ → Pour les assets web si vous servez du contenu statique

## 🔁 Redémarrage automatique en dev

Le projet utilise **Spring DevTools** pour recompiler et redémarrer automatiquement l’application en cas de changement dans `src/main/java`.

---

## 🧪 Tests

Les tests sont à venir (ou à compléter). Pour exécuter les tests :

```bash
./mvnw test
```

---

## 🛠 Variables d’environnement

Les variables sont centralisées dans un fichier `.env`

## 🤝 Contribuer

1. Forkez ce dépôt
2. Créez une branche (`git checkout -b feature/ma-feature`)
3. Commitez vos changements (`git commit -am 'Ajout de feature'`)
4. Pushez (`git push origin feature/ma-feature`)
5. Créez une _Merge Request_ sur GitLab

---

## 📜 Licence

Projet privé pour usage interne. Si vous souhaitez l’utiliser ou le forker, merci de contacter l’auteur.

---

## 👤 Auteur

Raphaël Leroudier  
📫 [Me contacter sur GitLab](https://gitlab.com/leroudier.raphael)

# Allgemein
## Teammitglieder
Artur Mustaf <br>
Dimitri Kreis <br>
Jan Zittel <br>
Philipp Grafmüller <br>
Felix Stapf <br>
Vorgänger: <br>
Darvin Weinheimer <br>
Vanessa Fabian <br>
Maximilian Berg <br>
Mathias Aff <br>

# Deployment

- [Umgebung](#umgebung)
- [Relevante Dateien](#relevante-dateien)
- [CI-Ablauf](#ci-ablauf)

## Umgebung
- Ubuntu Server 20.04
    - Docker
    - Docker-Compose
    - Github-Actions Runner (Direkt auf dem Host)
    - Reverse-Proxy mit TLS-Terminierung
    - Software:
        - git
        - maven
        - nodejs
        - npm

- Keycloak Server

## Wichtig
Elasticsearch muss erreichbar sein, wenn Spring startet.

<br>


## Relevante Dateien
Für das Deployment gibt es 3 Profile:
- dev
- prod
- local

In den zum jeweiligen Profil gehörigen Dateien müssen die Domains angepasst werden für:
- Backend
- Frontend
- Keycloak
- Database (Wenn Instanz nicht aus docker-compose verwendet wird)
- Elasticsearch (Wenn Instanz nicht aus docker-compose verwendet wird)

### Docker
Im Root-Verzeichnis befindet sich der Ordner docker-compose mit den Compose Dateien für die jeweilige Deployment-Umgebung:

```
docker-compose
├── dev
│   ├── backend.env
│   ├── docker-compose.yml
│   └── frontend.env
├── local
│   └── docker-compose.yml
└── prod
    ├── backend.env
    ├── docker-compose.yml
    └── frontend.env
```



"dev" und "prod" sind nahezu identisch. Die Unterschiede liegen darin, dass verschiedene Container-Images verwendet werden und die *.env Dateien andere Hostnames angeben.
Zum Ändern eines Profils muss man die Dateien mit entsprechenden Namen anpassen:
<br>

### Backend
```
backend/src/main/resources
├── application-dev.yml
├── application-local.yml
├── application-prod.yml
├── application.properties
└── application.yml
```
<br>

```
backend/src/test/resources
├── application-dev.yml
├── application-local.yml
├── application-prod.yml
├── application.yml
└── samples
```

<br>

### Frontend
```
frontend/src/environments/
├── environment.dev.ts
├── environment.prod.ts
└── environment.ts
```
Zum lokalen Entwickeln reicht hier die Standard Konfiguration.
<br>
<br>

## CI-Ablauf
Es wird Github Actions mit lokalem Runner verndet.
Beim Push in die Branches main oder development wird folgender Prozess gestartet:
- Git Repository auschecken
- backend/frontend:
    - Abhängigkeiten installieren
    - kompilieren
    - Tests ausführen
    - Container-Image erstellen
- docker-compose mit lokalen Images auf dem lokalen System deployen


## Weitere Ideen und Vorschläge
- Konfiguration durch Umgebungsvariablen
- Datenbank-Zugang über einen externen Secret-Storage verwalten
- Build- und Deployment-System voneinander unabhängig machen

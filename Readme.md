#Evoila Service Desk
##SOSE 2021 Team
- Artur Mustaf
- Jan Zittel
- Philipp Grafmüller
- Felix Stapf
##Aufbau
- [Keycloak](https://www.keycloak.org/)
- [Frontend](frontend)
- [Crawler](crawler)
- [Backend](backend)
<br>Benötigte Zugangsdaten zu API und Arango müssen bei evoila erfragt werden <br>
In den einzelnen Modulen müssen die Environment, oder propertiedateien verändert/erstellt werden.
###Keycloak
- Authentication Service
- 3 genutzte Clients
- 1 Realm
- Nutzerverwaltung
- Authentifizierung der Clients untereinander

Die Keycloak properties können dann von der Keycloak Adminkonsole ausgelesen werden.

###Frontend
- Stellt Daten von Backend da
- Abgesichert durch Keycloak (Muss aufgesetzt werden)
```
frontend/src/environments/
├── environment.dev.ts
├── environment.prod.ts
└── environment.ts
```
Abändern der Links an die passenden Adressen <br>
Zum lokalen Entwickeln reicht hier die Standard Konfiguration.
<br>
<br>
###Crawler
- Sammelt Daten von der Arango Datenbank
- Sammelt Daten von der vCloud Director API
- Sendet die Daten an das Backend
<br> Um den Crawler lauffähig zu bekommen müssen in den folgenden Dateien die Links angepasst werden
```
crawler/src/main/ressources
├── application-dev.yml
├── application-local.yml
├── application-prod.yml
├── application.properties
└── application.yml
```
Erstellen einer File namens _logindata.properties_ mit dem Format:
```
crawler/src/main/ressources

kc.clientID=crawlerClient
kc.clientSecret=
kc.grantType=client_credentials
kc.URI=
kc.realm=
kc.tokenURI=
api.baseURL=https://vcloud.evoila.io/api
api.user=
api.password=
backend.baseURL=
```
Erstellen einer File namens _arangodb.properties_ mit dem Format:
<br> wird von der Arango DB zum verbinden genutzt
```
crawler/src/main/ressources

arangodb.hosts=
arangodb.user=
arangodb.password=
```
Zum direkten Holen und Senden von Daten muss die Annotation dieser Methode aktiviert werden:
```
crawler/src/main/java/com.crawler/CrawlerApplication.java/
-> doAfterInit()
```
###Backend
- Legt von Crawler empfangene Daten in einer Postgres Datenbank ab
- Ist die API für unser Frontend, stellt Endpunkte bereit für das Auslesen der Daten
- Endpunkte sind abhängig vom Nutzen nur für Admins zugänglich
- Authentifizierung findet über einen Keycloakserver statt
  <br> Um das Backend lauffähig zu bekommen müssen in den folgenden Dateien die Links angepasst werden
```
backend/src/main/ressources
├── application-dev.yml
├── application-local.yml
├── application-prod.yml
├── application.properties
└── application.yml
```
Erstellen einer File namens _logindata.properties_ mit dem Format:
```
backend/src/main/ressources

kc.clientID=crawlerClient
kc.clientSecret=
kc.grantType=client_credentials
kc.URI=
kc.realm=
kc.tokenURI=
api.baseURL=https://vcloud.evoila.io/api
api.user=
api.password=
backend.baseURL=
```
#Wichtig
Docker Compose laufen bevor das Backend starten kann <br>
Das Backend muss laufen bevor der Crawler Daten daran senden kann (Also läuft das Backend am besten bevor der Crawler gestartet wird)<br>
Frontend kann immer gestartet werden, wird nur nicht befüllt wenn das Backend nicht läuft <br>
#Altes Team
## Allgemein
### Teammitglieder
Darvin Weinheimer <br>
Vanessa Fabian <br>
Maximilian Berg <br>
Mathias Aff <br>

## Deployment

- [Umgebung](#Umgebung)
- [Relevante Dateien](#relevante-dateien)
- [CI-Ablauf](#ci-ablauf)

### Umgebung
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

### Wichtig
Elasticsearch muss erreichbar sein, wenn Spring startet.

<br>


### Relevante Dateien
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

#### Docker
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

#### Backend
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

#### Frontend
```
frontend/src/environments/
├── environment.dev.ts
├── environment.prod.ts
└── environment.ts
```
Zum lokalen Entwickeln reicht hier die Standard Konfiguration.
<br>
<br>

### CI-Ablauf
Es wird Github Actions mit lokalem Runner verndet.
Beim Push in die Branches main oder development wird folgender Prozess gestartet:
- Git Repository auschecken
- backend/frontend:
    - Abhängigkeiten installieren
    - kompilieren
    - Tests ausführen
    - Container-Image erstellen
- docker-compose mit lokalen Images auf dem lokalen System deployen


### Weitere Ideen und Vorschläge
- Konfiguration durch Umgebungsvariablen
- Datenbank-Zugang über einen externen Secret-Storage verwalten
- Build- und Deployment-System voneinander unabhängig machen

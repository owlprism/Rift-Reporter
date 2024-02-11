# Projet de développement mobile : League of Legends API

Dans le cadre du cours de développement mobile, nous crééons une application en utilisant le langage de programmation Kotlin. Dans notre cas, nous avons pris la décision de concentrer nos efforts sur une application tournant autour de l'API de League of Legends.

#### Description de l'application

L'objectif de cette application est de fournir aux joueurs de League of Legends une application mobile pour suivre leur expérience de jeu. Avec cette application, les utilisateurs peuvent facilement consulter leur compte et accéder à une variété d'informations cruciales pour améliorer leur expérience de jeu.

Tout d'abord, les utilisateurs peuvent visualiser les informations de leur compte (leur niveau, leur rang), ce qui permet de consulter leur profil mais aussi le profil de leurs amis. 

De plus, l'application offrira aussi l'accès à l'historique des parties, permettant de retracer les victoires mémorables et d'avoir une visualisation de leur défaites. Ils pourront aussi consulter leurs champions favoris. Afin de savoir quel personnage chaque joueur va pouvoir incarner chaque semaine, la rotation des champions de la semaine sera aussi disponible, les aidant à diversifier leur expérience de jeu. 

En somme, cette application offre une expérience mobile complète pour les fans de League of Legends, améliorant leur immersion dans le monde de Runeterra et les aidant à atteindre le rang de Challenger.

#### [Lien RiotGames API]([https://developer.riotgames.com/apis#lol-status-v4/GET_getPlatformData](https://developer.riotgames.com/apis))

## Références API

### > Visualisation du profil d'un utilisateur

#### Informations générales de l'utilisateur

```
  GET /lol/summoner/v4/summoners/by-name/{summonerName}
```

| Paramètre | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `summonerName` | `string` | Votre nom de jeu |

#### Champions les plus joués par l'utilisateur

```
  GET /lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}/top
```

| Paramètre | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `encryptedPUUID` | `string` | PUUID encrypté | 

### > Visualisation de l'historique des parties d'un utilisateur

#### Récupération de l'ID des différents matchs

```
  GET /lol/match/v5/matches/by-puuid/{puuid}/ids
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `puuid`| `string` |PUUID|


#### Visualisation d'un match spécifique

```
  GET /lol/match/v5/matches/{matchId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `matchId`| `string` |ID du match|


### > Visualisation de la rotation des champions

```
  GET /lol/platform/v3/champion-rotations
``` 

 ### > Incident et maintenance

```
  GET  /lol/status/v4/platform-data 
```


## Auteurs

- GRESSIER Clément
- LUCAS Kalvin

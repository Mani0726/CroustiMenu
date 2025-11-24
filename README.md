# CroustiMenu

## **Présentation**
***CroustiMenu*** est une application qui permet de consulter facilement le menu du midi de votre Crous, en France métropolitaine et outre-mer.  
Vous pouvez rechercher un Crous en fonction d'une région, afficher l’ensemble des Crous sur une carte ou même enregistrer vos Crous favoris pour y accéder en un clic.

## Fonctionnalités principales
- Barre de recherche pour filtrer les Crous par nom dans une région
- Carte interactive avec tous les restaurants CROUS
- Consultation du menu du jour pour chaque Crous
- Ajout / supression de Crous en favoris
- Accès rapide aux favoris

## **Navigation**

### Page d'Accueil - Liste des régions : 
Lors du lancement de l'application, vous arrivez sur la liste des régions des Crous.
En sélectionnant une région, vous accédez à la liste des Crous de cette région.

### Liste des Crous :
Pour chaque région, les Crous sont affichés sous forme de cartes. Une barre de recherche permet de rechercher les Crous par nom.
Chaque Crous affiche :
- le nom du crous,
- son adresse,
- un bouton "voir le menu" pour accéder au détail,
- un bouton "coeur" permettant d'ajouter ou de retirer le crous des favoris.

### Détail d'un Crous :
L'écran affiche :
- le nom et les coordonnées du crous sélectionné (adresse, téléphone, mail),
- le menu du jour. Dans certains cas, le menu du jour n'est pas encore renseigné, une indication "Aucun menu disponible pour aujourd'hui" est indiqué. En règle générale, le menu est disponible en semaine aux alentours des 12h30.
- un coeur, permettant de gérer le statut favori,
- une flèche retour qui ramène à l'écran d'avant.

### Carte :
Depuis la page d'accueil, un onglet "Carte" permet d'afficher :
- une carte du monde avec des pointeurs sur tous les Crous
Un clic sur un marqueur permet d'ouvrir directement la fiche détaillée du Crous.

### Favoris :
L’icône cœur en haut à droite de la topBar permet d’accéder à l’écran « Favoris » :
- liste des restaurants marqués en favori.
Les favoris sont stockés localement pour être conservés entre les lancements de l’application.
Vous pouvez revenir au menu de la liste des régions ou de la carte en cliquant simplement sur le logo "CroustiMenu", situé en haut de l'écran.


### L'API choisie :
- https://www.data.gouv.fr/dataservices/api-croustillant/ : Fournit la liste des Crous et leurs menus du jour.


## Attention !
Pour une meilleure expérience utilisateur, veuillez mettre votre téléphone en mode clair et non en mode sombre !

## 🤓 Projet réalisé par
Amandine Laymajoux FIA4
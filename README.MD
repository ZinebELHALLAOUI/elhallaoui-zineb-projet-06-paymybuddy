# Pay My Buddy

Bienvenue dans le projet Pay My Buddy ! Cette application vise à permettre aux clients de gérer leurs finances en effectuant des transferts d'argent et en payant leurs amis. Ce README fournit des informations essentielles pour comprendre et contribuer au projet.

## Présentation

Ce projet met en œuvre le back-end et le front-end de l'application en utilisant la stack technique suivante :

- Spring Boot 2 pour la gestion cycle de vie des objets et transactions
- Spring security 5 pour la gestion d'authentification
- Maven pour la gestion des dépendances
- JDBC template pour communiquer avec la base de données
- Thymeleaf comme moteur de template
- HTML/CSS et Bootstrap 5 pour les écrans

## Fonctionnalités

1. **Enregistrement d'utilisateur :**
    - Les utilisateurs peuvent s'enregistrer facilement avec une adresse e-mail.

2. **Connexion d'utilisateur :**
    - Les utilisateurs peuvent se connecter à l'application en utilisant leur identifiant e-mail unique.

3. **Gestion d'amis :**
    - Les utilisateurs peuvent ajouter des amis à leur réseau en utilisant leur adresse e-mail.
    - La possibilité de vérifier si un ami est déjà enregistré dans la base de données.

4. **Dépôt d'argent :**
    - Les utilisateurs peuvent déposer de l'argent sur leur compte dans l'application.

5. **Transfert d'argent entre utilisateurs :**
    - Les utilisateurs peuvent effectuer des paiements à d'autres utilisateurs enregistrés sur l'application.
    - Les transactions peuvent être effectuées à partir du solde disponible sur le compte de l'utilisateur.

6. **Retrait d'argent :**
    - Les utilisateurs peuvent transférer de l'argent depuis leur compte dans l'application vers leur compte bancaire.

7. **Frais de transaction :**
    - À chaque transaction, l'application prélève un pourcentage de 0,5 %.

## Installation

1. Clonez ce référentiel sur votre machine locale.
2. Assurez-vous d'avoir Java 17 ou une version ultérieure installée.
3. Exécutez la commande `mvn install` pour installer toutes les dépendances.

## Utilisation

Pour lancer l'application, exécutez la classe principale `PayMyBuddy` et accédez http://localhost:8080/.

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.
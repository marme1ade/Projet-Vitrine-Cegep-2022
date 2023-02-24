# Projet-Vitrine-Cegep-2022

## Installation du projet
Le projet est présentement supporté sur Windows 10 seulement. L'utilisation de Intellij IDEA est fortement recommendée pour compiler et editer le projet. Le projet utilise la libraire processing.core de processing.org et n'utilise pas PDE (Processing Developement Environnement)

### Dépendences
Java JDK 17
https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

Pour la kinnect:
Installation de libusbk avec Zadig
https://github.com/OpenKinect/libfreenect2/blob/master/README.md#windows--visual-studio

### Configurations pour Intellij IDEA
- Cloner le repo et ouvrir le dossier à partir de l'IDE

- Configurer le SDK et le niveau language à Java 17 (File > Projet Structure > Project)

- S'assurer que les modules sont cochés à Export et leur Scope à "Compile"

- Ajouter un artéfact qui pointe au fichier src>org>vitrine>core>Main.java

- Build le projet, et compiler le Main.java

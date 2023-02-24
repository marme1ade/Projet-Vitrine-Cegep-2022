# Projet-Vitrine-Cegep-2022
___
## Installation du projet

Le projet est présentement supporté sur Windows 10 seulement. L'utilisation de [Intellij IDEA](https://www.jetbrains.com/idea/) est fortement recommendée pour compiler et éditer le projet. Le projet utilise la libraire processing.core de processing.org et n'utilise pas PDE (Processing Developement Environnement)
### Dépendences
- [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

#### Pour la kinect:
- Installation du [SDK 2.0 kinect de microsoft](https://www.microsoft.com/en-us/download/details.aspx?id=44561)

- Installation de libusbk avec [Zadig](https://github.com/OpenKinect/libfreenect2/blob/master/README.md#windows--visual-studio)

### Configurations pour Intellij IDEA
- Cloner le repo et ouvrir le dossier à partir de l'IDE

- Dans (File > Projet Structure > Project) :
  - Configurer le SDK et le niveau language à Java 17

  - S'assurer que les modules sont cochés à Export et leur Scope à "Compile"

  - Ajouter un artéfact qui pointe au fichier src>org>vitrine>core>Main.java

- Build le projet, et compiler le Main.java

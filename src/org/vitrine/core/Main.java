package org.vitrine.core;

import org.vitrine.common.Utils;
import org.vitrine.core.api.Server;
import org.vitrine.object.Television;
import processing.core.PApplet;

import java.lang.reflect.Constructor;

import org.vitrine.core.Console.Color;

public class Main {
    private static boolean isRunning = true;
    private static final java.io.Console systemConsole = System.console();
    private static Sketch currentSketch = null;
    private static Server apiServer;
    public static final Television tv = new Television();

    public static void main(String... args) {
        Console.println("- Chargement du fichier de configuration", Color.YELLOW);
        Config.loadConfig();
        Console.println("- Chargement du fichier de configuration terminé", Color.GREEN);
        Console.println("- Chargement du sketch", Color.YELLOW);
        Main.loadSketch("examples.koch.Koch");
        Console.println("- Sketch démarré ", Color.GREEN);

        Console.println("- Lancement du serveur API", Color.YELLOW);
        apiServer = new Server(Config.getApiServerPort());
        apiServer.start();
        Console.println("- Serveur API lancé sur le port " + Config.getApiServerPort(), Color.GREEN);

        tv.generateTotp(); // Generate initial Totp for TV

        PeriodicTasks.start();
        Console.println("- Tâches périodiques démarrée", Color.GREEN);

        if (systemConsole != null) {
            Console.println("Lecture de commande en cours, entrer HELP pour la liste des commandes", Color.CYAN);
            while (isRunning) {
                    executeCommand(systemConsole.readLine());
            }

            Console.println("Fin du programme", Color.RED);

            currentSketch.exit();
        } else { // No system console, app is running on an IDE
            Console.println("L'application a été démarrée dans un IDE, la lecture de commandes est désactivée.", Color.MAGENTA);
        }

        Utils.debugGenerateTotp();
    }

    /**
     * Load a sketch
     * @param sketchName Reference of the sketch class
     */
    public static boolean loadSketch(String sketchName) {
        String completeSketchReference = "org.vitrine.sketchs." + sketchName;

        if (currentSketch != null) {
            currentSketch.close();
        }

        try {
            Class<?> c = Class.forName(completeSketchReference);
            Constructor<?> cons = c.getConstructor();
            Sketch sketch = (Sketch) cons.newInstance();

            PApplet.runSketch(new String[]{sketch.getClass().getSimpleName()}, sketch);

            currentSketch = sketch;
            return true;

        } catch (Exception e) {
            Console.println(e.toString(), Color.RED);
            return false;
        }
    }

    /**
     * Execute a command from the console
     * @param commandStr Command string
     */
    private static void executeCommand(String commandStr) {
        String[] args = commandStr.split(" "); // Arguments / parameters are seperated by space
        String command = args[0]; // argument 0 is the command

        switch (command.toLowerCase()) {
            case "exit":
                isRunning = false;
                break;
            case "help":
            case "?":
                Console.println("[Liste des commandes]", Color.GREEN);
                Console.println("list - Affiche la liste des sketchs disponible", Color.MAGENTA);
                Console.println("load (nom du sketch) - Remplace le sketch actuel", Color.MAGENTA);
                Console.println("stop - Arrête le sketch actuel, mais ne ferme pas l'application", Color.MAGENTA);
                Console.println("exit - Ferme l'application", Color.MAGENTA);
                Console.println("clear - Nettoyer la console", Color.MAGENTA);
                Console.println("help / ? - Affiche la liste des commandes", Color.MAGENTA);
                Console.println("[Fin liste des commandes]", Color.GREEN);
                break;
            case "load":
                if (args.length < 2) {
                    Console.println("Veuillez spécifier le nom du sketch", Color.MAGENTA);
                    break;
                }

                if (currentSketch != null) {
                    currentSketch.close();
                }

                try {
                    loadSketch(args[1]);
                    Console.println("- Sketch démarré ", Color.GREEN);
                } catch (RuntimeException e) {
                    Console.println("Une erreur s'est produite lors du chargement du sketch, veuillez vérifier le nom", Color.RED);
                }
                break;
            case "stop":
                if (currentSketch != null) currentSketch.close();
                break;
            case "list":
                Console.println("List of sketchs:", Console.Color.MAGENTA);
                for (String sketch : Utils.getFractalsSketchList()) {
                    Console.println(sketch, Console.Color.MAGENTA);
                }
                break;
            case "cls":
            case "clear":
                Console.clear();
                break;
            default:
                Console.println("Cette commande n'existe pas !", Color.MAGENTA);
                break;
        }
    }
}

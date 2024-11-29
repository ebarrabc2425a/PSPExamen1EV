import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Util.titular;

/**
 * First Dates
 * Examen 1EV
 *
 * @author Eduardo Barra Balao
 * @version 0.1
 */
public class Principal {
    private static final int NUM_MESAS=5;           // Número de mesas del restaurante

    public static void main(String[] args) {
        titular("FIRST DATES");

        List<Mesa> mesas=Fabrica.generarMesas(NUM_MESAS);
        List<Persona> personas = Fabrica.generarPersonas(mesas);
        mostrarPersonas(personas);
        System.out.println();

        // mostrarCompatibilidades();
        // System.out.println();

        Instant inicio=Instant.now();

        // Lanzamiento de hilos
        try (ExecutorService ex = Executors.newFixedThreadPool(personas.size())) {
            for (Persona persona : personas)
                ex.execute(persona);
            ex.shutdown(); // No se aceptarán más tareas

            try {
                System.out.println("Esperando a que las tareas terminen...");
                if (!ex.awaitTermination(10, TimeUnit.MINUTES)) {
                    System.out.println("El tiempo de espera terminó. Se fuerza la finalización.");
                    ex.shutdownNow(); // Forzar cierre si aún hay tareas en ejecución
                } else {
                    System.out.println("Todas las tareas terminaron exitosamente.");
                }
            } catch (InterruptedException e) {
                System.out.println("Hilo principal interrumpido mientras esperaba.");
                ex.shutdownNow();
            }

            Instant fin = Instant.now();
            System.out.printf("Tiempo transcurrido desde que entró la primera persona: %s",
                Duration.between(inicio, fin).toSeconds());

            // Mostrar las mesas con las personas que se han sentado en cada mesa
            mostrarMesas(mesas);

            System.out.println("FIN");
        }

    }

    private static void mostrarCompatibilidades() {
        titular("Compatibilidades");
        ArrayList<Persona> personas1 = new ArrayList<>();
        ArrayList<Persona> personas2 = new ArrayList<>();
        int i = 0;
        for (Orientacion orientacion : Orientacion.values())
            for (Genero genero : Genero.values())
                personas1.add(new Persona("X", genero, orientacion));
        for (Orientacion orientacion : Orientacion.values())
            for (Genero genero : Genero.values())
                personas2.add(new Persona("Y", genero, orientacion));
        for (Persona persona1 : personas1)
            for (Persona persona2 : personas2) {
                System.out.printf("%s y\n%s => %s\n", persona1, persona2, persona1.esCompatible(persona2));
                System.out.println("-".repeat(3));
            }
    }

    private static void mostrarMesas(@NotNull List<Mesa> mesas) {
        titular("Mesas");
        for (Mesa mesa : mesas)
            System.out.println(mesa);
    }

    private static void mostrarPersonas(@NotNull List<Persona> personas) {
        titular("Personas");
        for (Persona persona : personas)
            System.out.println(persona);
    }
}

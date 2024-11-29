import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static util.Util.mensaje;

public class Persona implements Runnable {
    private static final int MIN_NUMERO=1;      // Mínimo número de una persona
    private static final int MIN_LONGITUD=1;    // Longitud mínima del nombre
    private static int contador=0;              // Número total de personas creadas
    private int numero;                         // >=1, AUTO
    private String nombre;                      // NO NULO, NO VACIO, NO BLANCO, LONGITUD>=2
    private Genero genero;                      // NO NULO
    private Orientacion orientacion;            // NO NULO
    private List<Mesa> mesas;                   // Mesas

    public Persona(String nombre, Genero genero, Orientacion orientacion, List<Mesa> mesas) {
        setNombre(nombre);
        setGenero(genero);
        setOrientacion(orientacion);
        this.mesas = mesas;
        setNumero(++contador);
    }

    public Persona(String nombre, Genero genero, Orientacion orientacion) {
        this(nombre, genero, orientacion, null);
    }

    private boolean mismoGenero(@NotNull Genero otro) {
        return genero.equals(otro);
    }

    public boolean esCompatible(@NotNull Persona otra) {
        HashMap<Orientacion,HashSet<Orientacion>> orientaciones=Fabrica.getCompatibilidadesOri();
        if (orientaciones.get(orientacion).contains(otra.orientacion))
            switch (orientacion) {
                case ASEXUAL:
                    return true;
                case BISEXUAL:
                    return  otra.orientacion.equals(Orientacion.BISEXUAL)
                            || (otra.orientacion.equals(Orientacion.HETEROSEXUAL) && !mismoGenero(otra.genero))
                            || (otra.orientacion.equals(Orientacion.HOMOSEXUAL) && mismoGenero(otra.genero));
                case HETEROSEXUAL:
                    return  (otra.orientacion.equals(Orientacion.BISEXUAL) && !mismoGenero(otra.genero))
                            || (otra.orientacion.equals(Orientacion.HETEROSEXUAL) && !mismoGenero(otra.genero));
                case HOMOSEXUAL:
                    return  (otra.orientacion.equals(Orientacion.BISEXUAL) && mismoGenero(otra.genero))
                            || (otra.orientacion.equals(Orientacion.HOMOSEXUAL) && mismoGenero(otra.genero));
                default:
                    System.err.printf("Orientación desconocida [orientacion=%s]", orientacion);
                    System.exit(1);
        }
        return false;
    }

    private void setGenero(@NotNull Genero genero) {
        this.genero = genero;
    }

    private void setNumero(int numero) {
        assert this.numero >= MIN_NUMERO:
            "El número debe ser >=%d [numero=%d]".formatted(MIN_NUMERO, numero);
        this.numero = numero;

    }

    private void setNombre(@NotNull String nombre) {
        assert !nombre.isBlank():
            "El nombre no puede estar vacío ni en blanco [nombre=%s]".formatted(nombre);
        assert nombre.length() >= MIN_LONGITUD:
            "La longitud del nombre debe ser >=%d [nombre=%s]".formatted(MIN_LONGITUD, nombre);
        this.nombre = nombre;
    }

    private void setOrientacion(@NotNull Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    @Override
    public String toString() {
        return "[%s] [%s] [%s] [%s]".formatted(numero, nombre, genero, orientacion);
    }

    @Override
    public void run() {
        final int MAX_TIEMPO1=5000;                                 // Nº máximo de milisegundos de pausa para pensar
        final String CORAZON_ROJO="\u2764\uFE0F";                   // Corazón rojo
        final String CORAZON_ROTO="\uD83D\uDC94";                   // Corazón roto
        final String CARA_CON_MEJILLAS_SONROJADAS="\uD83D\uDE33";   // Cara con mejillas sonrojadas
        boolean sentada=false;                                      // ¿La persona se ha sentado?

        do {
            int tiempo1 = (int) (Math.random() * MAX_TIEMPO1) + 1; // Tiempo entre 0 y 5000 milisegundos => [0-5] segundos

            // Pensar un tiempo aleatorio
            try {
                Thread.sleep(tiempo1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Buscar aleatoriamente una mesa
            int numMesa = (int) (Math.random() * mesas.size());
            Mesa mesa = mesas.get(numMesa);

            // Intentar sentarse en la mesa elegida
            synchronized (mesa) {
                if (mesa.estaVacia()) {
                    mesa.sentar(this);
                    mensaje("%s Me he sentado en la mesa %d y espero a mi futuro amor"
                        .formatted(this, mesa.getNumero()));
                    sentada=true;
                } else if (!mesa.estaLlena()) {
                    Persona p1=mesa.getPrimeraPersonaSentada();
                    if (esCompatible(p1)) {
                        mesa.sentar(this);
                        mensaje("%s Me he sentado en la mesa %d con %s y somos compatibles %s"
                            .formatted(this, mesa.getNumero(), p1, CORAZON_ROJO));
                        sentada=true;
                    } else
                        mensaje(("%s Me he encontrado en la mesa %d con %s y no somos compatibles"
                            +" desgraciadamente %s")
                            .formatted(this, mesa.getNumero(), p1, CORAZON_ROTO));
                } else  // Mesa llena
                    mensaje("%s ¡Perdón! Que vaya bien vuestra cena %s"
                        .formatted(this, CARA_CON_MEJILLAS_SONROJADAS));
            }
        } while (!sentada);
    }
}

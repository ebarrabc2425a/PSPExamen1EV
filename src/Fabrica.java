import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Fabrica {
    private static List<Mesa> mesas;
    private static List<Persona> personas;
    private static HashMap<Orientacion,HashSet<Orientacion>> compatibilidadesOri;
    private Fabrica() {}

    public static List<Mesa> generarMesas(int n) {
        if (n<1)
            throw new IllegalArgumentException("No es posible generar %d mesas [mínimo %d]".formatted(n,1));
        mesas = new ArrayList<>(n);
        for (int i = 1; i <= n; i++)
            mesas.add(new Mesa());
        return mesas;
    }

    public static List<Persona> generarPersonas(List<Mesa> mesas) {
        String[] nombres={  "AMANCIO", "TIBURCIO", "IGOR", "DOROTEA", "DULCINEA",
                            "LIZBETH", "LEOVIGILDO", "ODISEA", "ADALBERTO", "GALATEA"   };
        String generos="MMMFFFMFMF";
        String[] orientaciones={    "HE", "HE", "HO", "HE", "HO",
                                    "HE", "B", "B", "B", "B"       };
        personas = new ArrayList<>();
        for (int i = 0; i < nombres.length; i++)
            personas.add(new Persona(   nombres[i],
                                        Genero.getGenero(generos.charAt(i)),
                                        Orientacion.getOrientacion(orientaciones[i]),
                                        mesas)                                          );
        return personas;
    }

    // Devuelve un mapa con las compatibilidades de personas según orientación
    public static HashMap<Orientacion,HashSet<Orientacion>> getCompatibilidadesOri() {
        compatibilidadesOri=new HashMap<>();
        // ASEXUAL
        HashSet<Orientacion> ho=new HashSet<>();
        ho.add(Orientacion.ASEXUAL);
        compatibilidadesOri.put(Orientacion.ASEXUAL,ho);

        // BISEXUAL
        ho=new HashSet<>();
        ho.add(Orientacion.BISEXUAL);
        ho.add(Orientacion.HETEROSEXUAL);   // Género contrario
        ho.add(Orientacion.HOMOSEXUAL);     // Mismo género
        compatibilidadesOri.put(Orientacion.BISEXUAL,ho);

        // HETEROSEXUAL
        ho=new HashSet<>();
        ho.add(Orientacion.BISEXUAL);       // Género contrario
        ho.add(Orientacion.HETEROSEXUAL);   // Género contrario
        compatibilidadesOri.put(Orientacion.HETEROSEXUAL,ho);

        // HOMOSEXUAL
        ho=new HashSet<>();
        ho.add(Orientacion.BISEXUAL);       // Mismo género
        ho.add(Orientacion.HOMOSEXUAL);     // Mismo género
        compatibilidadesOri.put(Orientacion.HOMOSEXUAL,ho);

        return compatibilidadesOri;
    }
}

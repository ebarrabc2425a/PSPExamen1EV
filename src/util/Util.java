package util;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Util {
    private static final String C_LINEA="-";    // Carácter para mostrar líneas
    private static final int LON_LINEA=50;      // Longitud por defecto de una línea
    private Util() {}

    public static void linea() {
        linea(LON_LINEA);
    }

    public static void linea(int longitud) {
        System.out.println(C_LINEA.repeat(longitud));
    }

    public static void mensaje(@NotNull String texto) {
        Instant t=Instant.now();
        // Configurar el formato para la fecha y hora en español, con precisión de nanosegundos
        DateTimeFormatter f=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSSSSSSSS")
                .withZone(ZoneId.systemDefault());

        System.out.printf("[%s] %s\n", f.format(t), texto);
    }


    public static void titular(@NotNull String texto) {
        int longitud=texto.length();
        System.out.println(C_LINEA.repeat(longitud));
        System.out.println(texto);
        System.out.println(C_LINEA.repeat(longitud));
    }
}

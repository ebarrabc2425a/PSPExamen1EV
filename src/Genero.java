import org.jetbrains.annotations.NotNull;

public enum Genero {
    MASCULINO, FEMENINO;

    public static Genero getGenero(char c) {
        return switch(c) {
            case 'M' -> MASCULINO;
            case 'F' -> FEMENINO;
            default -> null;
        };
    }
}

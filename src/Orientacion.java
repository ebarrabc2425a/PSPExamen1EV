import org.jetbrains.annotations.NotNull;

public enum Orientacion {
    ASEXUAL, BISEXUAL, HETEROSEXUAL, HOMOSEXUAL;

    public static Orientacion getOrientacion(@NotNull String corto) {
        return switch(corto) {
            case "A" -> ASEXUAL;
            case "B" -> BISEXUAL;
            case "HE" -> HETEROSEXUAL;
            case "HO" -> HOMOSEXUAL;
            default -> null;
        };
    }
}

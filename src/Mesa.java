import org.jetbrains.annotations.NotNull;

public class Mesa {
    private static final int AFORO=2;           // Número total de personas que se pueden sentar en una mesa
    private static final int MIN_NUMERO=1;      // Mínimo número de una mesa
    private static int contador=0;              // Número total de mesas creadas
    private int numero;                         // >=1, AUTO
    private Persona[] personas;                 // Personas sentadas a la mesa. Siempre se ocupa primero persona[0]

    public Mesa() {
        personas=new Persona[AFORO];
        setNumero(++contador);
    }

    public boolean estaLlena() {
        return numPersonas()==personas.length;
    }

    public boolean estaVacia() {
        return numPersonas()==0;
    }

    public int getNumero() {
        return numero;
    }

    // Devuelve la primera persona que se sienta en la mesa (personas[0])
    // Error si la mesa está vacía
    public Persona getPrimeraPersonaSentada() {
        if (estaVacia())
            throw new IllegalStateException("La mesa está vacía. No es posible obtener la primera persona sentada.");
        return personas[0];
    }

    public boolean sentar(@NotNull Persona persona) {
        if (estaLlena())
            throw new IllegalStateException("No es posible sentar a la persona %s a la mesa ya que está llena [mesa=%s]"
                .formatted(persona, this));
        for (int i = 0; i < personas.length; i++) {
            if (personas[i]==null) {
                personas[i] = persona;
                return true;
            }
        }
        // PROGRAMACIÓN DEFENSIVA
        System.err.println("Error inesperado en sentar. La ejecución no debería haber llegado aquí");
        System.exit(1);
        return false;
    }

    private void setNumero(int numero) {
        assert this.numero >= MIN_NUMERO:
            "El número debe ser >=%d [numero=%d]".formatted(MIN_NUMERO, numero);
        this.numero = numero;
    }

    // Número de personas sentadas en la mesa
    public int numPersonas() {
        int n=0;
        for (int i = 0; i < personas.length; i++)
            if (personas[i] != null)
                n++;
        return n;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[%d]".formatted(numero));
        for (int i = 0; i < personas.length; i++) {
            sb.append(" ");
            sb.append(personas[i]);
        }
        return sb.toString();
    }
}

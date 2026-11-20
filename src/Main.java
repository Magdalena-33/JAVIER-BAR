import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final char[] CODES = {'D', 'A', 'M', 'I', 'C'};
    static final String[] NOMBRES = {"DESAYUNOS", "COMIDAS", "MERIENDAS", "CENAS", "COPAS"};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        float[] sumaPorCategoria = new float[CODES.length];
        int[] contarEntradasPorCategoria = new int[CODES.length];

        String linea;
        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(linea);
            String textoCategoria = st.nextToken();
            float importe = Float.parseFloat(st.nextToken());

            if (textoCategoria.equals("N") && importe == 0.0f) {
                // Fin del día: se muestra el resultado al momento y se reinicia todo
                System.out.println(procesarDia(sumaPorCategoria, contarEntradasPorCategoria));
                sumaPorCategoria = new float[CODES.length];
                contarEntradasPorCategoria = new int[CODES.length];
            } else {
                int pos = getPosCategoria(textoCategoria.charAt(0));
                if (pos >= 0) {
                    anadirVenta(sumaPorCategoria, contarEntradasPorCategoria, pos, importe);
                }
            }
        }
    }

    static String getMax(float[] sum) {
        int posMax = 0;
        boolean empate = false;
        for (int i = 1; i < sum.length; i++) {
            if (sum[i] > sum[posMax]) { posMax = i; empate = false; }
            else if (sum[i] == sum[posMax]) { empate = true; }
        }
        return empate ? "EMPATE" : NOMBRES[posMax];
    }

    static String getMin(float[] sum) {
        int posMin = 0;
        boolean empate = false;
        for (int i = 1; i < sum.length; i++) {
            if (sum[i] < sum[posMin]) { posMin = i; empate = false; }
            else if (sum[i] == sum[posMin]) { empate = true; }
        }
        return empate ? "EMPATE" : NOMBRES[posMin];
    }

    static String procesarDia(float[] sum, int[] contador) {
        String supera = (mediaComida(sum, contador) > mediaTotalDia(sum, contador)) ? "SI" : "NO";
        return getMax(sum) + "#" + getMin(sum) + "#" + supera;
    }

    static float mediaComida(float[] sum, int[] cont) {
        int pos = getPosCategoria('A');
        if (cont[pos] == 0) return 0.0f;
        return sum[pos] / cont[pos];
    }

    static float mediaTotalDia(float[] sum, int[] cont) {
        float sumaTotal = 0.0f;
        int contTotal = 0;
        for (int i = 0; i < sum.length; i++) {
            sumaTotal += sum[i];
            contTotal += cont[i];
        }
        if (contTotal == 0) return 0.0f;
        return sumaTotal / contTotal;
    }

    static void anadirVenta(float[] sumParcial, int[] cont, int pos, float impo) {
        sumParcial[pos] += impo;
        cont[pos]++;
    }

    static int getPosCategoria(char cod) {
        for (int i = 0; i < CODES.length; i++) {
            if (CODES[i] == cod) return i;
        }
        return -1;
    }
}
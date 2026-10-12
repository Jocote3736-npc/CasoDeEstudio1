import java.util.Scanner;

public class Main {
    Scanner reader = new Scanner(System.in);
    int opcion;
    PilaDinamica pila = new PilaDinamica();

    public String obtenerValorVariable(String nombreVariable) {
        switch (nombreVariable.trim().toLowerCase()) {
            case "saludo":
                return "Hola, ";
            case "nombre":
                return "Carlos";
            case "mensaje":
                return "Bienvenido al sistema.";
            default:
                return null;
        }
    }

    public void limpiaConsola() {
        try {
            String sistemOperativo = System.getProperty("os.name");
            if (sistemOperativo.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void pausa() {
        System.out.println("\n Presione Enter para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }

    public void menuPruebas() {
        opcion = -1;
        while (opcion != 0) {
            limpiaConsola();
            System.out.println("\n****** OPERACIONES DIRECTAS DE LA PILA ******\n");
            System.out.println("    1. Apilar carácter/token (Push)");
            System.out.println("    2. Desapilar carácter/token (Pop)");
            System.out.println("    3. Ver tope (Peek)");
            System.out.println("    0. Regresar al menú principal\n");
            System.out.print("    Digite la opción: ");
            opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el valor a apilar: ");
                    String val = reader.nextLine();
                    pila.push(val);
                    System.out.println("¡Elemento apilado con éxito!");
                    pausa();
                    break;
                case 2:
                    String eliminado = pila.pop();
                    if (eliminado != null) {
                        System.out.println("Elemento desapilado: " + eliminado);
                    }
                    pausa();
                    break;
                case 3:
                    String tope = pila.peek();
                    if (tope != null) {
                        System.out.println("Elemento en el tope: " + tope);
                    }
                    pausa();
                    break;
                case 0:
                    opcion = -1;
                    return;
                default:
                    System.out.println("\n *** Opción Inválida. ***");
                    pausa();
                    break;
            }
        }
    }

    //Uso explicativo de la Pila para balanceo
    public void evaluarBalanceo() {
        limpiaConsola();
        System.out.println("\n****** ANÁLISIS DE SINTAXIS Y BALANCEO (USANDO PILA) ******\n");
        System.out.println("Ingrese la cadena de impresión o expresión a evaluar:");
        System.out.print("> ");
        String expresion = reader.nextLine();

        pila.vaciar();
        boolean balanceado = true;
        boolean comillaSimpleAbierta = false;
        boolean comillaDobleAbierta = false;

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // La pila controla la apertura y cierre de comillas literales
            if (c == '\'' && !comillaDobleAbierta) {
                if (!comillaSimpleAbierta) {
                    comillaSimpleAbierta = true;
                    pila.push("'");
                } else {
                    comillaSimpleAbierta = false;
                    if (pila.estaVacia() || !pila.pop().equals("'")) { 
                        balanceado = false;
                        break;
                    }
                }
                continue;
            }

            if (c == '"' && !comillaSimpleAbierta) {
                if (!comillaDobleAbierta) {
                    comillaDobleAbierta = true;
                    pila.push("\""); 
                } else {
                    comillaDobleAbierta = false;
                    if (pila.estaVacia() || !pila.pop().equals("\"")) { 
                        balanceado = false;
                        break;
                    }
                }
                continue;
            }

            if (comillaSimpleAbierta || comillaDobleAbierta) {
                continue;
            }

            // Delimitadores estructurales apilados
            if (c == '(' || c == '{' || c == '[') {
                pila.push(String.valueOf(c));
            } else if (c == ')' || c == '}' || c == ']') {
                if (pila.estaVacia()) {
                    balanceado = false;
                    break;
                }
                String tope = pila.pop();
                if ((c == ')' && !tope.equals("(")) ||
                    (c == '}' && !tope.equals("{")) ||
                    (c == ']' && !tope.equals("["))) {
                    balanceado = false;
                    break;
                }
            }
        }

        if (!pila.estaVacia() || comillaSimpleAbierta || comillaDobleAbierta) {
            balanceado = false;
        }

        System.out.println("\n----------------------------------------");
        if (balanceado) {
            System.out.println("Resultado: ¡La cadena está CORRECTAMENTE balanceada usando la pila!");
        } else {
            System.out.println("Resultado: ¡ERROR! La pila detectó desbalanceo o delimitadores sin cerrar.");
        }
        System.out.println("----------------------------------------");
        pausa();
    }

    //Uso explicativo de la Pila para evaluar y concatenar tokens
    public void evaluarConcatenacion() {
        limpiaConsola();
        System.out.println("\n****** EVALUACIÓN Y CONCATENACIÓN USANDO LA PILA ******\n");
        System.out.println("Variables disponibles: saludo, nombre, mensaje");
        System.out.println("Ingrese la expresión (ej. saludo + nombre):");
        System.out.print("> ");
        String expresion = reader.nextLine();

        String[] tokens = expresion.split("\\+");
        pila.vaciar();

        // 1. Apilamos cada token de la expresión
        for (String token : tokens) {
            pila.push(token.trim());
        }

        // 2. Desapilamos los tokens para procesar la concatenación
        int totalTokens = pila.getTamano();
        String[] tokensOrdenados = new String[totalTokens];
        
        for (int i = totalTokens - 1; i >= 0; i--) {
            tokensOrdenados[i] = pila.pop();
        }

        StringBuilder resultadoFinal = new StringBuilder();
        boolean error = false;

        for (String token : tokensOrdenados) {
            if ((token.startsWith("\"") && token.endsWith("\"")) || 
                (token.startsWith("'") && token.endsWith("'"))) {
                String literal = token.substring(1, token.length() - 1);
                resultadoFinal.append(literal);
            } else if (obtenerValorVariable(token) != null) {
                resultadoFinal.append(obtenerValorVariable(token));
            } else {
                System.out.println("\n[Error]: Token o variable no reconocida en la pila: '" + token + "'");
                error = true;
                break;
            }
        }

        if (!error) {
            System.out.println("\n----------------------------------------");
            System.out.println("¡Expresión procesada exitosamente mediante la pila!");
            System.out.println("Resultado concatenado: \"" + resultadoFinal.toString() + "\"");
            System.out.println("----------------------------------------");
        }

        pausa();
    }

    public void menuFuncionalidades() {
        opcion = -1;
        while (opcion != 0) {
            limpiaConsola();
            System.out.println("\n****** ANÁLISIS DE CADENAS DE IMPRESIÓN ******\n");
            System.out.println("    1. Sintaxis y balanceo de comillas/delimitadores");
            System.out.println("    2. Evaluar y concatenar expresión (Cadenas y Variables)");
            System.out.println("    0. Regresar al menú principal\n");
            System.out.print("    Digite la opción: ");
            opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1:
                    evaluarBalanceo();
                    break;
                case 2:
                    evaluarConcatenacion();
                    break;
                case 0:
                    opcion = -1;
                    return;
                default:
                    System.out.println("\n *** Opción Inválida. ***");
                    pausa();
                    break;
            }
        }
    }

    public void menu() {
        opcion = -1;
        while (opcion != 0) {
            limpiaConsola();
            System.out.println("\n****** ANALIZADOR DE CADENAS DE IMPRESIÓN (PILAS) ******\n");
            System.out.println("    1. Pruebas directas de la Pila");
            System.out.println("    2. Funcionalidades del Análisis de Cadenas");
            System.out.println("    0. Salir del programa\n");
            System.out.print("    Digite la opción: ");
            opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1:
                    menuPruebas();
                    break;
                case 2:
                    menuFuncionalidades();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("\n *** Opción Inválida. ***");
                    pausa();
                    break;
            }
        }
    }

    public void main(String[] args) {
        menu();
    }
}


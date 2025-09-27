import java.util.Scanner;

public class Interface {
    // atributo
    private Scanner scanner = new Scanner(System.in);

    // enum
    public enum MenuOption {
        INSERIR(1, "Inserir caractere na árvore"),
        REMOVER(2, "Remover caractere da árvore"),
        CODIFICAR(3, "Codificar (texto para morse)"),
        DECODIFICAR(4, "Decodificar (morse para texto)"),
        SAIR(5, "Sair");

        private final int value;
        private final String description;

        MenuOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        public static MenuOption fromInt(int chosenOption) {
            for (MenuOption option : MenuOption.values()) {
                if (option.getValue() == chosenOption) {
                    return option;
                }
            }
            return null;
        }
    }

    // método principal
    public void displayMenu() {
        MenuOption option;
        do {
            System.out.println("==== MENU MORSE CODE ====");
            for (MenuOption mo : MenuOption.values()) {
                System.out.printf("%d - %s\n", mo.getValue(), mo.getDescription());
            }
            System.out.print("Escolha uma opção: ");
            int input = readOption();

            option = MenuOption.fromInt(input);

            if (option == null) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            switch (option) {
                case INSERIR:
                    insertCharacter();
                    break;
                case REMOVER:
                    removeCharacter();
                    break;
                case CODIFICAR:
                    encode();
                    break;
                case DECODIFICAR:
                    decode();
                    break;
                case SAIR:
                    System.out.println("Saindo do programa...");
                    break;
            }
        } while (option != MenuOption.SAIR);
    }

    // métodos auxiliares
    private int readOption() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void insertCharacter() {
        System.out.println("[Inserir] — Implemente aqui a lógica de inserção.");
    }

    private void removeCharacter() {
        System.out.println("[Remover] — Implemente aqui a lógica de remoção.");
    }

    private void encode() {
        System.out.println("[Codificar] — Implemente aqui a lógica de codificação.");
    }

    private void decode() {
        System.out.println("[Decodificar] — Implemente aqui a lógica de decodificação.");
    }
}

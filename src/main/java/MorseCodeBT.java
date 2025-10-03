import myutils.Node;

public class MorseCodeBT {
    private Node<String> root;

    private static final char[] letters = {
            'A','B','C','D','E','F','G','H','I','J','K','L','M',
            'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            '0','1','2','3','4','5','6','7','8','9'
    };

    private static final String[] morseCodes = {
            ".-","-...","-.-.","-..",".","..-.","--.","....","..",
            ".---","-.-",".-..","--","-.","---",".--.","--.-",".-.",
            "...","-","..-","...-",".--","-..-","-.--","--..",
            "-----",".----","..---","...--","....-",".....","-....","--...","---..","----."
    };

    public MorseCodeBT() { root = new Node<>(""); }

    public Node<String> getRoot() { return root; }

    private String morseMap(char l){
        String morseCode = null;
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == l) {
                morseCode = morseCodes[i];
                break;
            }
        }

        return morseCode;
    }

    private Node<String> insert(Node<String> node, String letter, String morseCode) {
        if (node == null) {
            node = new Node<>("");
        }

        if (morseCode.isEmpty()) {
            node.setElement(letter);
            return node;
        }

        if (morseCode.charAt(0) == '.') {
            node.setLeft(insert(node.getLeft(), letter, morseCode.substring(1)));
        } else if (morseCode.charAt(0) == '-') {
            node.setRight(insert(node.getRight(), letter, morseCode.substring(1)));
        }

        return node;
    }

    public void insert(char letter) {
        letter = Character.toUpperCase(letter);

        String morseCode = morseMap(letter);

        if (morseCode != null) {
            root = insert(root, String.valueOf(letter), morseCode);
        }
    }

    private String getLetterMorse(Node<String> node, char letter) {
        if (node == null) return null;

        if (node.getElement().equals(String.valueOf(letter))) {
            return "";
        }

        // tenta pela esquerda
        String leftPath = getLetterMorse(node.getLeft(), letter);
        if (leftPath != null) {
            return "." + leftPath;
        }

        // tenta pela direita
        String rightPath = getLetterMorse(node.getRight(), letter);
        if (rightPath != null) {
            return "-" + rightPath;
        }

        // não achou
        return null;
    }

    public String encrypt(String input) {
        StringBuilder sb = new StringBuilder();

        for (char c : input.toUpperCase().toCharArray()) {
            if (c == ' ') {
                sb.append("/ ");
            } else {
                String morse = getLetterMorse(root, c);
                if (morse != null) {
                    sb.append(morse).append(" ");
                }
            }
        }

        return sb.toString().trim();
    }

    public String decrypt(String morse) {
        if (morse == null || morse.trim().isEmpty()) return "";

        StringBuilder out = new StringBuilder();
        String[] words = morse.trim().split(" / "); // separa palavras

        for (int w = 0; w < words.length; w++) {
            String[] letters = words[w].trim().split(" "); // separa letras
            for (String letter : letters) {
                if (!letter.isEmpty()) {
                    out.append(decodeLetter(letter, root));
                }
            }
            if (w < words.length - 1) {
                out.append(" "); // espaço entre palavras
            }
        }

        return out.toString();
    }

    // Recursivamente percorre a árvore para decodificar uma única letra
    private String decodeLetter(String code, Node<String> node) {
        if (node == null) {
            return "?"; // caminho inválido
        }
        if (code.isEmpty()) {
            // fim do caminho: retorna o símbolo do nó ou '?'
            return (node.getElement() != null && !node.getElement().isEmpty())
                    ? node.getElement()
                    : "?";
        }

        char c = code.charAt(0);
        String rest = code.substring(1);

        if (c == '.') {
            return decodeLetter(rest, node.getLeft());
        } else if (c == '-') {
            return decodeLetter(rest, node.getRight());
        } else {
            return "?"; // caractere inválido
        }
    }


}

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

    /**
     * Decodifica uma string em código Morse para texto, usando a árvore construída.
     * Regras:
     * - Ponto (.) caminha para a esquerda; traço (-) para a direita.
     * - Espaço separa letras; "/" separa palavras.
     * - Se um caminho não levar a um nó com letra (ou a árvore não tiver esse caractere),
     *   adiciona '?' como marcador.
     * Implementação totalmente recursiva e sem estruturas prontas.
     *
     * @param morse sequência Morse (ex.: "... --- ... / ... --- ...")
     * @return texto decodificado (ex.: "SOS SOS")
     */
    public String decrypt(String morse) {
        if (morse == null) return "";
        String cleaned = morse.trim();
        if (cleaned.isEmpty()) return "";
        StringBuilder out = new StringBuilder();
        // Inicia na raiz, ainda não percorreu nenhum símbolo da letra atual (moved = false)
        decryptRecursive(cleaned, 0, root, out, false);
        return out.toString();
    }

    // Percorre recursivamente a string Morse e a árvore simultaneamente.
    // idx: posição atual na string 'morse'
    // current: nó atual na árvore para a letra em construção
    // out: acumulador de resultado
    // moved: indica se já percorremos ao menos um '.' ou '-' desde o último separador
    private void decryptRecursive(String morse, int idx, Node<String> current, StringBuilder out, boolean moved) {
        if (idx >= morse.length()) {
            // Fim da string: fecha a última letra, se houve movimento
            if (moved) appendLetter(current, out);
            return;
        }

        char ch = morse.charAt(idx);

        if (ch == '.') {
            // desce à esquerda
            decryptRecursive(morse, idx + 1, current != null ? current.getLeft() : null, out, true);
            return;
        }

        if (ch == '-') {
            // desce à direita
            decryptRecursive(morse, idx + 1, current != null ? current.getRight() : null, out, true);
            return;
        }

        if (ch == ' ') {
            // fim de uma letra; fecha só se houve percurso desde o último separador
            if (moved) appendLetter(current, out);
            // reinicia na raiz para a próxima letra
            decryptRecursive(morse, idx + 1, root, out, false);
            return;
        }

        if (ch == '/') {
            // fim de uma palavra; fecha a letra em curso (se houver)
            if (moved) appendLetter(current, out);
            // adiciona um espaço entre palavras (evita espaços duplos)
            if (out.length() > 0 && out.charAt(out.length() - 1) != ' ') out.append(' ');
            // reinicia na raiz para a próxima letra/palavra
            decryptRecursive(morse, idx + 1, root, out, false);
            return;
        }

        // Caracter inesperado (não deveria ocorrer pois a interface valida a entrada)
        // Avança sem alterar estado para manter robustez.
        decryptRecursive(morse, idx + 1, current, out, moved);
    }

    // Fecha a letra atual: se 'current' tem um símbolo válido, usa-o; caso contrário, insere '?'
    private void appendLetter(Node<String> current, StringBuilder out) {
        if (current != null) {
            String elem = current.getElement();
            if (elem != null && !elem.isEmpty()) {
                out.append(elem);
                return;
            }
        }
        out.append('?');
    }


}

package myutils;

public class BinaryTreeMorse {
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

    public BinaryTreeMorse() { root = new Node<>(""); }

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
}

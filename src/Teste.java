public class Teste {
    public static void main(String[] args) {
        MorseCodeBT tree = new MorseCodeBT();
        for (char c = 'A'; c <= 'Z'; c++) tree.insert(c);
        for (char c = '0'; c <= '9'; c++) tree.insert(c);

        System.out.println(tree.encrypt("SOS"));
    }
}

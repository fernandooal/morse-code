import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import myutils.Node;

public class MorseCodeInterface extends Application {

    private MorseCodeBT morseTree;
    private TextArea outputArea;
    private Canvas canvas;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        morseTree = new MorseCodeBT();

        primaryStage.setTitle("Sistema de Código Morse - Árvore Binária");

        // Layout principal
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // Painel superior - Título
        VBox topPanel = createTopPanel();
        mainLayout.setTop(topPanel);

        // Painel central - Canvas para visualização da árvore
        ScrollPane canvasScroll = createCanvasPanel();
        mainLayout.setCenter(canvasScroll);

        // Painel direito - Controles
        VBox rightPanel = createControlPanel();
        mainLayout.setRight(rightPanel);

        // Painel inferior - Status
        statusLabel = new Label("Sistema iniciado. Árvore vazia.");
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        statusLabel.setTextFill(Color.BLUE);
        statusLabel.setPadding(new Insets(5));
        mainLayout.setBottom(statusLabel);

        Scene scene = new Scene(mainLayout, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createTopPanel() {
        VBox topPanel = new VBox(5);
        topPanel.setAlignment(Pos.CENTER);
        topPanel.setPadding(new Insets(10));
        topPanel.setStyle("-fx-background-color: #2c3e50;");

        Label titleLabel = new Label("SISTEMA DE CÓDIGO MORSE");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        Label subtitleLabel = new Label("Árvore Binária de Busca - Recursiva");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitleLabel.setTextFill(Color.LIGHTGRAY);

        topPanel.getChildren().addAll(titleLabel, subtitleLabel);
        return topPanel;
    }

    private ScrollPane createCanvasPanel() {
        canvas = new Canvas(1600, 800);
        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);
        scrollPane.setStyle("-fx-background-color: #ecf0f1;");

        drawEmptyTree();

        return scrollPane;
    }

    private VBox createControlPanel() {
        VBox controlPanel = new VBox(15);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setMinWidth(350);
        controlPanel.setMaxWidth(350);
        controlPanel.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 2;");

        // Seção 1: Inserir caracteres
        VBox insertSection = createInsertSection();

        // Seção 2: Codificar
        VBox encodeSection = createEncodeSection();

        // Seção 3: Decodificar
        VBox decodeSection = createDecodeSection();

        // Área de output
        Label outputLabel = new Label("Resultado:");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(8);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-control-inner-background: #ffffff;");

        // Botão para limpar árvore
        Button clearButton = new Button("Limpar Árvore");
        clearButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        clearButton.setOnAction(e -> clearTree());

        Separator sep1 = new Separator();
        Separator sep2 = new Separator();
        Separator sep3 = new Separator();

        controlPanel.getChildren().addAll(
                insertSection, sep1,
                encodeSection, sep2,
                decodeSection, sep3,
                outputLabel, outputArea,
                clearButton
        );

        return controlPanel;
    }

    private VBox createInsertSection() {
        VBox section = new VBox(8);

        Label sectionLabel = new Label("1. INSERIR CARACTERES");
        sectionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        sectionLabel.setTextFill(Color.web("#2c3e50"));

        // Botão para inserir alfabeto completo
        Button insertAlphabetButton = new Button("Inserir Alfabeto (A-Z)");
        insertAlphabetButton.setMaxWidth(Double.MAX_VALUE);
        insertAlphabetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        insertAlphabetButton.setOnAction(e -> insertAlphabet());

        // Botão para inserir números
        Button insertNumbersButton = new Button("Inserir Números (0-9)");
        insertNumbersButton.setMaxWidth(Double.MAX_VALUE);
        insertNumbersButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white;");
        insertNumbersButton.setOnAction(e -> insertNumbers());

        // Inserir caractere individual
        HBox charBox = new HBox(5);
        TextField charField = new TextField();
        charField.setPromptText("Digite um caractere");
        charField.setPrefWidth(200);

        Button insertCharButton = new Button("Inserir");
        insertCharButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        insertCharButton.setOnAction(e -> insertCharacter(charField));

        charBox.getChildren().addAll(charField, insertCharButton);

        section.getChildren().addAll(
                sectionLabel,
                insertAlphabetButton,
                insertNumbersButton,
                charBox
        );

        return section;
    }

    private VBox createEncodeSection() {
        VBox section = new VBox(8);

        Label sectionLabel = new Label("2. CODIFICAR (Texto → Morse)");
        sectionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        sectionLabel.setTextFill(Color.web("#2c3e50"));

        TextField encodeField = new TextField();
        encodeField.setPromptText("Digite o texto a codificar");

        Button encodeButton = new Button("Codificar");
        encodeButton.setMaxWidth(Double.MAX_VALUE);
        encodeButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        encodeButton.setOnAction(e -> encodeText(encodeField));

        section.getChildren().addAll(sectionLabel, encodeField, encodeButton);

        return section;
    }

    private VBox createDecodeSection() {
        VBox section = new VBox(8);

        Label sectionLabel = new Label("3. DECODIFICAR (Morse → Texto)");
        sectionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        sectionLabel.setTextFill(Color.web("#2c3e50"));

        TextField decodeField = new TextField();
        decodeField.setPromptText("Ex: ... --- ... (use / para espaços)");

        Button decodeButton = new Button("Decodificar");
        decodeButton.setMaxWidth(Double.MAX_VALUE);
        decodeButton.setStyle("-fx-background-color: #16a085; -fx-text-fill: white; -fx-font-weight: bold;");
        decodeButton.setOnAction(e -> decodeText(decodeField));

        section.getChildren().addAll(sectionLabel, decodeField, decodeButton);

        return section;
    }

    // Métodos de ação

    private void insertAlphabet() {
        for (char c = 'A'; c <= 'Z'; c++) {
            morseTree.insert(c);
        }
        updateStatus("Alfabeto completo inserido (A-Z)");
        outputArea.setText("Alfabeto completo foi inserido na árvore!");
        drawTree();
    }

    private void insertNumbers() {
        for (char c = '0'; c <= '9'; c++) {
            morseTree.insert(c);
        }
        updateStatus("Números inseridos (0-9)");
        outputArea.setText("Números de 0 a 9 foram inseridos na árvore!");
        drawTree();
    }

    private void insertCharacter(TextField field) {
        String input = field.getText().trim().toUpperCase();

        if (input.isEmpty()) {
            showError("Digite um caractere para inserir!");
            return;
        }

        if (input.length() > 1) {
            showError("Digite apenas UM caractere por vez!");
            return;
        }

        char c = input.charAt(0);

        if (!isValidChar(c)) {
            showError("Caractere inválido! Use apenas letras (A-Z) ou números (0-9).");
            return;
        }

        morseTree.insert(c);
        updateStatus("Caractere '" + c + "' inserido com sucesso");
        outputArea.setText("Caractere '" + c + "' foi inserido na árvore!");
        field.clear();
        drawTree();
    }

    private void encodeText(TextField field) {
        if (!checkTreeNotEmpty()) {
            return;
        }

        String input = field.getText().trim();

        if (input.isEmpty()) {
            showError("Digite um texto para codificar!");
            return;
        }

        if (!isValidString(input)) {
            showError("Texto contém caracteres inválidos! Use apenas letras, números e espaços.");
            return;
        }

        String morse = morseTree.encrypt(input);
        updateStatus("Texto codificado com sucesso");
        outputArea.setText("Texto original: " + input + "\n\nCódigo Morse: " + morse);
    }

    private void decodeText(TextField field) {
        if (!checkTreeNotEmpty()) {
            return;
        }

        String input = field.getText().trim();

        if (input.isEmpty()) {
            showError("Digite um código morse para decodificar!");
            return;
        }

        if (!isValidMorseString(input)) {
            showError("Código morse inválido! Use apenas pontos (.), traços (-), espaços e barras (/).");
            return;
        }

        try {
            String decoded = morseTree.decrypt(input);

            if (decoded.contains("?")) {
                updateStatus("Decodificação concluída com alguns caracteres não reconhecidos");
                outputArea.setText("Código Morse: " + input +
                        "\n\nTexto decodificado: " + decoded +
                        "\n\nNota: O símbolo '?' indica código morse não reconhecido.");
            } else {
                updateStatus("Código morse decodificado com sucesso");
                outputArea.setText("Código Morse: " + input + "\n\nTexto decodificado: " + decoded);
            }
        } catch (Exception e) {
            showError("Erro ao decodificar: " + e.getMessage());
            outputArea.setText("Erro ao processar o código morse. Verifique o formato de entrada.");
        }
    }

    private void clearTree() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Limpeza");
        alert.setHeaderText("Deseja realmente limpar a árvore?");
        alert.setContentText("Todos os dados serão perdidos!");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                morseTree = new MorseCodeBT();
                updateStatus("Árvore limpa");
                outputArea.setText("Árvore foi limpa. Insira novos caracteres.");
                drawEmptyTree();
            }
        });
    }

    // Métodos de validação

    private boolean checkTreeNotEmpty() {
        if (morseTree.getRoot().getElement().isEmpty() &&
                morseTree.getRoot().getLeft() == null &&
                morseTree.getRoot().getRight() == null) {
            showError("A árvore está vazia! Insira caracteres antes de codificar/decodificar.");
            return false;
        }
        return true;
    }

    private boolean isValidChar(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    private boolean isValidString(String s) {
        for (char c : s.toCharArray()) {
            if (c != ' ' && !isValidChar(Character.toUpperCase(c))) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidMorseString(String s) {
        for (char c : s.toCharArray()) {
            if (c != '.' && c != '-' && c != ' ' && c != '/') {
                return false;
            }
        }
        return true;
    }

    // Métodos de desenho da árvore

    private void drawTree() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Node<String> root = morseTree.getRoot();

        if (root == null || (root.getElement().isEmpty() && root.getLeft() == null && root.getRight() == null)) {
            drawEmptyTree();
            return;
        }

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        drawNode(gc, root, canvas.getWidth() / 2, 50, canvas.getWidth() / 4, "ROOT");
    }

    private void drawNode(GraphicsContext gc, Node<String> node, double x, double y, double xOffset, String path) {
        if (node == null) {
            return;
        }

        // Desenha filhos primeiro (para linhas ficarem atrás)
        if (node.getLeft() != null) {
            double newX = x - xOffset;
            double newY = y + 80;
            gc.setStroke(Color.web("#3498db"));
            gc.setLineWidth(2);
            gc.strokeLine(x, y + 20, newX, newY - 20);

            // Label do caminho
            gc.setFill(Color.web("#3498db"));
            gc.fillText(".", x - xOffset/2 - 10, y + 45);

            drawNode(gc, node.getLeft(), newX, newY, xOffset / 2, path + " .");
        }

        if (node.getRight() != null) {
            double newX = x + xOffset;
            double newY = y + 80;
            gc.setStroke(Color.web("#e74c3c"));
            gc.setLineWidth(2);
            gc.strokeLine(x, y + 20, newX, newY - 20);

            // Label do caminho
            gc.setFill(Color.web("#e74c3c"));
            gc.fillText("-", x + xOffset/2 - 5, y + 45);

            drawNode(gc, node.getRight(), newX, newY, xOffset / 2, path + " -");
        }

        // Desenha o nó
        if (node.getElement().isEmpty()) {
            // Nó vazio
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.GRAY);
        } else {
            // Nó com letra
            gc.setFill(Color.web("#2ecc71"));
            gc.setStroke(Color.web("#27ae60"));
        }

        gc.setLineWidth(3);
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.strokeOval(x - 20, y - 20, 40, 40);

        // Desenha o texto
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        String displayText = node.getElement().isEmpty() ? "" : node.getElement();
        gc.fillText(displayText, x - 7, y + 7);
    }

    private void drawEmptyTree() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gc.fillText("Árvore vazia - Insira caracteres para visualizar",
                canvas.getWidth() / 2 - 200, canvas.getHeight() / 2);
    }

    // Métodos auxiliares

    private void updateStatus(String message) {
        statusLabel.setText("Status: " + message);
        statusLabel.setTextFill(Color.GREEN);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Operação inválida");
        alert.setContentText(message);
        alert.showAndWait();

        statusLabel.setText("Erro: " + message);
        statusLabel.setTextFill(Color.RED);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
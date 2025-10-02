# 📡 Sistema de Código Morse - Árvore Binária de Busca

Projeto acadêmico de implementação de uma Árvore Binária de Busca para codificação e decodificação de Código Morse, desenvolvido com JavaFX e implementação recursiva.

## 📋 Descrição do Projeto

Este sistema implementa uma estrutura de dados de **Árvore Binária de Busca (BST)** baseada nas regras do código Morse:
- **Ponto (.)** → navegação para a **esquerda**
- **Traço (-)** → navegação para a **direita**

O programa permite inserir caracteres dinamicamente, codificar texto em Morse e decodificar Morse em texto, tudo com visualização gráfica da árvore construída.

## 🎯 Funcionalidades

### ✅ Implementadas
- ✔️ Inserção de caracteres (A-Z, 0-9) na árvore
- ✔️ Inserção individual ou em lote (alfabeto completo/números)
- ✔️ Codificação de texto para código Morse
- ✔️ Visualização gráfica da árvore binária
- ✔️ Interface JavaFX moderna e intuitiva
- ✔️ Validações de entrada e tratamento de erros
- ✔️ Todas as operações implementadas de forma **recursiva**

### 🔄 Em Desenvolvimento
- ⏳ Decodificação de código Morse para texto (em implementação)

## 📐 Padronização do Código Morse

O sistema segue a padronização internacional do código Morse:

### Separadores
- **Espaço simples** ( ) - separação entre **letras**
    - Exemplo: `.- .` representa "A E"

- **Barra com espaços** (` / `) - separação entre **palavras**
    - Exemplo: `.- / .` representa "A E" (como duas palavras diferentes)

### Tabela de Códigos Morse

| Letra | Morse | Letra | Morse | Número | Morse |
|-------|-------|-------|-------|--------|-------|
| A | `.-` | N | `-.` | 0 | `-----` |
| B | `-...` | O | `---` | 1 | `.----` |
| C | `-.-.` | P | `.--.` | 2 | `..---` |
| D | `-..` | Q | `--.-` | 3 | `...--` |
| E | `.` | R | `.-.` | 4 | `....-` |
| F | `..-.` | S | `...` | 5 | `.....` |
| G | `--.` | T | `-` | 6 | `-....` |
| H | `....` | U | `..-` | 7 | `--...` |
| I | `..` | V | `...-` | 8 | `---..` |
| J | `.---` | W | `.--` | 9 | `----.` |
| K | `-.-` | X | `-..-` | | |
| L | `.-..` | Y | `-.--` | | |
| M | `--` | Z | `--..` | | |

## 🏗️ Estrutura do Projeto

```
morse-code/
├── pom.xml                                 # Configuração Maven
├── README.md                               # Este arquivo
└── src/
    └── main/
        └── java/
            ├── myutils/
            │   └── Node.java               # Classe genérica de nó da árvore
            ├── MorseCodeBT.java            # Árvore binária com lógica Morse
            ├── MorseCodeInterface.java     # Interface JavaFX
            └── Teste.java                  # Classe de testes
```

## 🔧 Tecnologias Utilizadas

- **Java 23**
- **JavaFX 21.0.2** (interface gráfica)
- **Maven** (gerenciamento de dependências)
- **Recursão** (todas as operações da árvore)

## 🚀 Como Executar

### Pré-requisitos
- JDK 23 ou superior
- Maven 3.x
- IntelliJ IDEA (recomendado) ou outra IDE

### Passos para Execução

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd morse-code
```

2. **Compile o projeto**
```bash
mvn clean compile
```

3. **Execute a aplicação**
```bash
mvn javafx:run
```

### Executar pelo IntelliJ IDEA

1. Abra o projeto no IntelliJ
2. Aguarde o Maven sincronizar as dependências
3. No painel Maven (lateral direita), dentro de plugins:
    - Execute `clean`
    - Execute `compile`
    - Execute `javafx:run`

## 📖 Como Usar o Sistema

### 1. Inserção de Caracteres
- **Opção 1:** Clique em "Inserir Alfabeto (A-Z)" para adicionar todas as letras
- **Opção 2:** Clique em "Inserir Números (0-9)" para adicionar todos os dígitos
- **Opção 3:** Digite um caractere individual e clique em "Inserir"

### 2. Codificação (Texto → Morse)
- Digite o texto desejado no campo "Digite o texto a codificar"
- Clique em "Codificar"
- O resultado aparecerá na área de resultado
- Exemplo:
  ```
  Texto original: SOS
  Código Morse: ... --- ...
  ```

### 3. Decodificação (Morse → Texto)
- Digite o código Morse no campo correspondente
- Use espaços para separar letras
- Use ` / ` para separar palavras
- Clique em "Decodificar"
- Exemplo de entrada: `... --- ... / ... --- ...`

### 4. Visualização da Árvore
- A árvore é desenhada automaticamente após cada inserção
- **Nós verdes:** contêm letras/números
- **Nós brancos:** nós intermediários (vazios)
- **Linhas azuis:** caminho com ponto (.)
- **Linhas vermelhas:** caminho com traço (-)

## 🧪 Exemplos de Teste

### Teste 1: Mensagem Simples
```
Entrada: SOS
Saída: ... --- ...
```

### Teste 2: Frase com Espaços
```
Entrada: Eu sou o numero 1
Saída: . ..- / ... --- ..- / --- / -. ..- -- . .-. --- / .----
```

### Teste 3: Números
```
Entrada: 2024
Saída: ..--- ----- ..--- ....-
```

## ⚠️ Tratamento de Erros

O sistema implementa validações completas:

1. **Árvore vazia:** Impede codificação/decodificação se não houver caracteres inseridos
2. **Caracteres inválidos:** Aceita apenas letras (A-Z) e números (0-9)
3. **Código Morse inválido:** Valida que só contenha pontos, traços, espaços e barras
4. **Entrada vazia:** Verifica se há texto antes de processar

## 🎨 Interface Visual

A interface foi desenvolvida com JavaFX e apresenta:

- **Painel Superior:** Título e descrição do sistema
- **Painel Central:** Canvas com visualização da árvore (scrollável)
- **Painel Direito:** Controles de operação
    - Seção de inserção
    - Seção de codificação
    - Seção de decodificação
    - Área de resultados
    - Botão de limpar árvore
- **Painel Inferior:** Barra de status com feedback de operações

## 🐛 Resolução de Problemas

### Erro: "Output directory is empty"
**Solução:** Certifique-se de que os arquivos `.java` estão em `src/main/java/`

### Erro: Classe não encontrada
**Solução:** Execute `mvn clean compile` antes de `mvn javafx:run`

### Erro: JavaFX não carrega
**Solução:** Verifique se o `pom.xml` está configurado corretamente com os profiles do seu sistema operacional

## 📝 Licença

Este é um projeto acadêmico desenvolvido para fins educacionais.

## 👨‍💻 Autores

- **Fernando Alonso** - Estrutura de dados e codificação
- **Jafte Carneiro** - Decodificação
- **Renato Gouveia** - Interface JavaFX e visualização

---

**Disciplina:** Resolução de Problemas Estruturados em Computação
**Instituição:** PUCPR
**Ano:** 2025
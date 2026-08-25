# Map Card Framework

Framework orientado a objetos para criação de jogos de cartas entre dois jogadores, desenvolvido em **Java 21** como projeto final da disciplina de **Métodos Avançados de Programação (MAP)** da Universidade Estadual da Paraíba (UEPB).

O projeto utiliza uma arquitetura genérica e extensível, tendo o **Blackjack** como implementação concreta. A proposta é permitir que novos jogos de cartas sejam adicionados ao framework por meio de pontos de extensão bem definidos, sem modificar o núcleo existente.

## Objetivos

- Desenvolver um framework reutilizável para jogos de cartas.
- Aplicar conceitos de Programação Orientada a Objetos.
- Utilizar padrões de projeto GoF para estruturar as responsabilidades e facilitar a extensão.
- Aplicar princípios GRASP e SOLID.
- Utilizar generics para tornar o núcleo independente do tipo de carta.
- Separar regras, estratégias, execução de jogadas e fluxo da partida.
- Garantir encapsulamento das coleções internas.
- Implementar tratamento de estados inválidos por meio de exceções específicas.
- Disponibilizar testes automatizados com JUnit 5 e documentação Javadoc.

## Arquitetura

O projeto é dividido em dois módulos conceituais principais:

```text
br.edu.uepb.map
├── framework
│   ├── baralho
│   ├── cartas
│   ├── estrategia
│   ├── eventos
│   ├── excecoes
│   ├── jogador
│   ├── partida
│   └── regras
│
└── jogos
    └── blackjack
```

### Framework

O pacote `framework` concentra as abstrações e componentes reutilizáveis:

- `Carta` — abstração para diferentes tipos de cartas.
- `Baralho<T>` — gerenciamento genérico de cartas.
- `MaoDeCartas<T>` — representação da mão de um jogador.
- `Jogador` — abstração de jogadores.
- `JogadorHumano` e `JogadorAutomatico` — implementações reutilizáveis.
- `Partida<T>` — fluxo geral de uma partida.
- `RegraJogo` — validação de jogadas e definição do vencedor.
- `Estrategia` — comportamento de jogadores automáticos.
- `ExecutorDeJogada<T>` — aplicação dos efeitos das jogadas.
- `FabricaBaralho<T>` — criação de baralhos específicos.
- Sistema de eventos com `Evento`, `NotificadorEventos`, `OuvinteEvento` e implementações relacionadas.
- Exceções específicas para estados inválidos.

### Blackjack

O pacote `jogos.blackjack` implementa o Blackjack utilizando as abstrações do framework.

Entre seus principais componentes estão:

- `JogoBlackjack` — ponto de entrada da aplicação.
- `PartidaBlackjack` — especialização do fluxo de partida.
- `RegraBlackjack` — regras e validações específicas do jogo.
- `EstrategiaBlackjack` — estratégia utilizada pela banca.
- `ExecutorDeJogadaBlackjack` — execução das ações do Blackjack.
- `FabricaBaralhoTradicional` — criação do baralho tradicional.
- `CalculadoraDeMao` — cálculo da pontuação das mãos.
- `Pontuacao` — controle do placar entre rodadas.

## Padrões de Projeto

O projeto aplica quatro padrões de projeto GoF:

### Template Method

Utilizado em `Partida<T>`. A classe define o algoritmo geral de uma partida e disponibiliza pontos de extensão para que jogos concretos implementem apenas os comportamentos específicos.

No Blackjack, `PartidaBlackjack` especializa esse fluxo, definindo a condição de encerramento da partida.

### Strategy

Utilizado por meio da interface `Estrategia`. Permite encapsular diferentes algoritmos de decisão para jogadores automáticos.

No Blackjack, `EstrategiaBlackjack` define o comportamento da banca sem modificar `JogadorAutomatico`.

### Factory Method

Utilizado na criação de baralhos. `FabricaBaralho` define a estrutura de criação, enquanto implementações concretas determinam quais cartas compõem cada baralho.

### Observer

Implementado pelo sistema de eventos formado por `NotificadorEventos`, `OuvinteEvento` e `Evento`.

A lógica da partida pode publicar acontecimentos sem depender diretamente do console, permitindo que os eventos sejam utilizados também em testes, logs ou futuras interfaces.

## Princípios GRASP e SOLID

A arquitetura aplica princípios de projeto como:

- **Especialista na Informação** — cada classe mantém e utiliza os dados pelos quais é responsável.
- **Criador** — fábricas e classes responsáveis pela montagem da aplicação criam os objetos necessários.
- **Controlador** — `Partida<T>` coordena o fluxo de uma rodada.
- **Baixo Acoplamento** — o núcleo depende de abstrações e não de implementações específicas do Blackjack.
- **Alta Coesão** — as responsabilidades são distribuídas entre classes especializadas.
- **Polimorfismo** — diferentes regras, estratégias, executores e cartas podem implementar as mesmas abstrações.
- **Fabricação Pura** — responsabilidades auxiliares, como cálculo de pontuação e notificação, são isoladas das classes de domínio.
- **Variações Protegidas** — interfaces e classes abstratas isolam o framework das particularidades de cada jogo.

Também são aplicados os princípios **SRP, OCP, LSP, ISP e DIP** do SOLID.

## Pontos de Extensão

O framework foi estruturado para permitir a criação de novos jogos sem modificar seu núcleo.

Entre os principais pontos de extensão estão:

| Componente | Função |
|---|---|
| `Carta` | Define um novo tipo de carta |
| `FabricaBaralho<T>` | Define a composição do baralho |
| `Jogador` | Permite novas formas de tomada de decisão |
| `Estrategia` | Define estratégias para jogadores automáticos |
| `Jogada` | Representa as ações disponíveis em um jogo |
| `RegraJogo` | Define validações e critérios de vitória |
| `ExecutorDeJogada<T>` | Define os efeitos das jogadas |
| `Partida<T>` | Permite especializar condições e etapas da partida |

Com essa estrutura, jogos como **Uno ou Truco** poderiam ser implementados reutilizando o núcleo do framework.

## Encapsulamento e Tratamento de Exceções

As coleções internas de `Baralho`, `MaoDeCartas` e `Partida` são protegidas contra alterações externas indevidas. Os métodos de consulta retornam cópias das coleções, evitando que o estado interno seja modificado diretamente.

O projeto também possui exceções específicas para situações inválidas:

- `BaralhoVazioException`
- `CartasInsuficientesException`
- `JogadaInvalidaException`

Essas exceções permitem representar de forma explícita problemas relacionados ao estado atual do jogo.

## Testes

Os testes automatizados foram desenvolvidos utilizando **JUnit 5** e abrangem componentes do framework e da implementação do Blackjack.

Entre os elementos testados estão:

- Baralho e fábrica de baralho;
- Cartas;
- Mão de cartas;
- Jogadores humano e automático;
- Partida;
- Sistema de eventos;
- Estratégia do Blackjack;
- Regras do Blackjack;
- Tratamento de exceções.

## Tecnologias

- **Java 21**
- **Maven**
- **JUnit 5.11.4**
- **Javadoc**
- **Git/GitHub**
- **IntelliJ IDEA** (ambiente de desenvolvimento)

## Como Executar

### Pré-requisitos

- Java JDK 21 ou superior;
- Maven instalado e configurado no PATH.

### Compilar o projeto

```bash
mvn clean compile
```

### Executar os testes

```bash
mvn test
```

### Gerar a documentação Javadoc

```bash
mvn javadoc:javadoc
```

A documentação será gerada no diretório:

```text
docs/
```

### Executar o Blackjack

Após a compilação, o ponto de entrada da aplicação é:

```text
br.edu.uepb.map.jogos.blackjack.JogoBlackjack
```

Também é possível executar a classe `JogoBlackjack` diretamente pela IDE.

## Como Jogar

O jogo é executado no console.

Durante a vez do jogador, as principais ações são:

```text
c / comprar / 1 → comprar uma carta
p / parar / 2   → parar
```

Após o encerramento da rodada, o resultado é apresentado e o jogador pode escolher se deseja iniciar uma nova rodada.

A banca utiliza uma estratégia automática própria do Blackjack.

## Documentação

O projeto inclui documentação gerada com **Javadoc**, disponível no diretório `docs/`.

O arquivo `diagrama-classes.drawio` também apresenta o diagrama de classes utilizado na representação da arquitetura.

## Estrutura do Projeto

```text
map-card-framework/
├── docs/                         # Documentação Javadoc
├── src/
│   ├── main/java/
│   │   └── br/edu/uepb/map/
│   │       ├── framework/        # Núcleo reutilizável
│   │       └── jogos/blackjack/  # Implementação do Blackjack
│   │
│   └── test/java/                # Testes automatizados
│
├── diagrama-classes.drawio       # Diagrama de classes
├── pom.xml                       # Configuração Maven
└── README.md
```

## Equipe

Projeto desenvolvido por:

- Antonio da Silva Lins
- Jefferson Renan Pereira Santos
- João Gabriel Caetano de Aquino Silva
- Joaquim Laureano Galdino
- Pedro Henrique da Silva Sales

**Universidade Estadual da Paraíba (UEPB)**  
**Centro de Ciência e Tecnologia — Departamento de Computação**  
**Disciplina: Métodos Avançados de Programação**  
**2026**

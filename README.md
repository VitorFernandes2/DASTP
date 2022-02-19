# DASTP
Poker Game in Java

# Java version 11  

__Git behaviours:__
- Branch naming:
  - feature/name_continuation_of_name 
  - fix/name_of_fix
- Commit naming
  - "FEATURE: description" 
  - "FIX: description"

____

# Game Rules - [link_1](http://www.pokerlistings.com/poker-rules) | [link_2](https://www.pokernews.com/poker-rules/texas-holdem.htm)  | [link_3](https://pt.wikipedia.org/wiki/Texas_hold_%27em) | [link_4](https://www.cardplayer.com/poker-tools/odds-calculator/texas-holdem)

#### Important Notes:
- A graphical application with a command line that manages all user actions.
- Graphical part only reflects the state / action implemented punctually (it is __not__ necessary to present multiple interfaces on the same plane).
- The Pot money should be divided between the users with the highest score and in case of an odd quantity of pot chips, the nearest player to the dealer should receive the odd chip.

____

# Card Sequences (generic):

| Rules | Example |
| --- |  ---  |
| Royal Flush — five cards of the same suit, from ace to ten. | __[A♥] [K♥] [Q♥] [J♥] [10♥]__ |
| Straight Flush — five cards of the same suit and ranked consecutively. | __[9♣] [8♣] [7♣] [6♣] [5♣]__ |
| Four of a Kind — four cards of the same rank. | __[Q♣] [Q♥] [Q♦] [Q♠] [4♦]__ |
| Full House — three cards of the same rank and two more cards of the same rank. | __[J♣] [J♥] [J♠] [8♦] [8♥]__ |
| Flush — any five cards of the same suit. | __[A♠] [J♠] [8♠] [5♠] [2♠]__ |
| Straight — any five consecutively sorted cards. | __[Q♣] [J♦] [10♥] [9♠] [8♦]__ |
| Three of a Kind — three cards of the same rank. | __[8♣] [8♠] [8♦] [K♣] [4♥]__ |
| Two Pair — two cards of the same rank and two more cards of the same rank. | __[A♠] [A♣] [J♦] [J♣] [7♠]__ |
| One Pair — two cards of the same rank. | __[10♥] [10♣] [9♥] [4♦] [2♦]__ |
| High Card — five incomparable cards. | __[A♣] [J♦] [10♠] [5♣] [2♥]__ |

<details><summary>Implemented Rules [PT]</summary>
<p>

- [x] Royal Flush — cinco cartas do mesmo naipe, de ás a dez; Exemplo: __[A♥] [K♥] [Q♥] [J♥] [10♥]__ [DONE]
- [x] Straight Flush — cinco cartas do mesmo naipe e classificadas consecutivamente; Exemplo: __[9♣] [8♣] [7♣] [6♣] [5♣]__ [DONE]
- [x] Four of a Kind — quatro cartas do mesmo valor; Exemplo: __[Q♣] [Q♥] [Q♦] [Q♠] [4♦]__ [DONE]
- [x] Full House — três cartas do mesmo valor e mais duas cartas do mesmo valor; Exemplo: __[J♣] [J♥] [J♠] [8♦] [8♥]__ [DONE]
- [x] Flush — quaisquer cinco cartas do mesmo naipe; Exemplo: __[A♠] [J♠] [8♠] [5♠] [2♠]__ [DONE]
- [x] Straight — quaisquer cinco cartas classificadas consecutivamente; Exemplo: __[Q♣] [J♦] [10♥] [9♠] [8♦]__ [DONE]
- [x] Three of a Kind — três cartas do mesmo valor; Exemplo: __[8♣] [8♠] [8♦] [K♣] [4♥]__ [DONE]
- [x] Two Pair — duas cartas do mesmo valor e mais duas cartas do mesmo valor; Exemplo: __[A♠] [A♣] [J♦] [J♣] [7♠]__ [DONE]
- [x] One Pair — duas cartas do mesmo valor; Exemplo: __[10♥] [10♣] [9♥] [4♦] [2♦]__ [DONE]
- [x] High Card — cinco cartas incomparáveis; Exemplo: __[A♣] [J♦] [10♠] [5♣] [2♥]__ [DONE]

</p>
</details>

____

# Functionalities:

### Game Scenarios:
- [x] __CREATE_GAME__ -> Login of two players, a player creates a game, a player joins in the game and the game starts.
- [x] __CREATE_COMPETITIVE_GAME__ -> A player creates a competitive game.
- [x] __BET_IN_GAME__ -> Creates a game, joins players to the game, execute a bet (command) and undo it and redo it.
- [x] __CREATE_PLAYER__ -> Creates a new player (`lj12`).
- [x] __LOGIN_PLAYER__ -> Login of an existing player (`lj`).
- [x] __LIST_GAMES__ -> Creation of two games and listing of them.
- [x] __CREATE_GAME_WINNING__ -> Creation of a single game, join of 2 more players and the host, game starts and all players do check of their hand. (It is good to test cards scoring.)

### Games System:
- [x] ~~User tells the system that he wants to join that game~~ [DONE]
- [x] ~~Allocate the User to the selected game (only if you have enough PCs for the initial deposit)~~ [DONE]
- [x] ~~One user == one game~~ [DONE]
- [x] ~~Creating the Deck of Cards~~ [DONE]
			

### Jogadores:

  - [ ] Client (__normal user__)
    - [x] ~~User registration~~ [DONE]
    - [x] ~~Create Friendly Games~~ [DONE]
    - [ ] List of Games (only those games that the user can participate should appear)

  - [ ] Client (__paid out user__)(all normal user features + extras)
    - [x] ~~System Services~~ [DONE]

		
### Virtual Money (Poker-Chips == PCs | Poker-Chips-Jogos == PCJs):

  - [ ] __Acquisition:__
    - [x] ~~Compra no Sistema~~ [DONE]
    - [ ] Ganho em Jogos / Torneios

  - [ ] __Utilization:__
    - [x] ~~Purchase of Services (ex: paid games creation)~~ [DONE]
    - [ ] Games betting [WIP]
    - [x] ~~Cash Conversion~~ [DONE]
    - [ ] (We) Set the conversion rate in the competitive game [WIP]

	
### Games (settings depend on game scenario __(Game Type and Configuration)__):

  - [ ] (dependent on the amount of user PCs)
  - [ ] They all start with the same amount of PCJs
  - [x] ~~PCJs are obtained after paying an amount of PCs (conversion rate A)~~ [DONE]
  - [x] ~~PCJs are converted to PCs after game completion (A conversion rate)~~ [DONE]
  - [ ] Friendly Games (0 PCs == 50 PCJs) they have no return from PCJs to PCs at the end of the game
  - [x] ~~Payment / transfer methods~~ [DONE]
  - [x] ~~(5% of the purchase price of PCs is acquired by the company)~~ [DONE]
  - [x] ~~After Min players present, host can start the game~~ [DONE]
    - [ ] After the game starts, specified deposit is charged

  - [ ] Friendly Games __(not configurable)__:
    - [ ] 0 PCs == 50 PCJs
    - [ ] 3 players (Max)
    - [ ] Bets of 4 PCJs (Fixed)

  - [ ] Competitive Games __(configurable)__:
    - [ ] Host must own 50 PCs (Min)
    - [ ] Deposit of 10 PCs (Min)
    - [ ] Number of players (Min)
    - [ ] Amount of Bets (Min)
    - [ ] PC conversion rate for this game

  - [ ] Tournaments __(their format)__[^2]


### Communication between Players

  - [x] ~~The identification of players currently online~~ [DONE]
  - [x] ~~Sending messages between players~~ [DONE]
  - [x] __(Optional)__
    - [x] ~~Friend's list~~ [DONE]
    - [x] ~~Block a Player~~ [DONE]

		
### Game Creation

  - [x] ~~Min deposit~~ [DONE]
  - [ ] Initial value of PCJs (you can set the conversion rate at the start)
  - [x] ~~Number of Players (Min)~~ [DONE]
  - [x] ~~Creator with 50 PCs or more -> competitive play~~ [DONE]


### Management Operations

  - [ ] BackEnd:
    - [x] ~~CRUD, Action Log and other user features~~  [DONE]
    - [ ] Creation / Removal / Game Info [WIP]


### User Interface

  - [ ] CLI application
    - [ ] Functions to simulate user and administrator actions
    - [x] ~~Registration and Log of user actions~~ [DONE]
      - [ ] __Sintaxe:__[^1]
        - [ ] `criaTreino nomeJogo=jogo1 criador=joao`
        - [ ] `compraCreditos nome=ana valor=1000 pagamento=paypal`
        - [ ] `juntaJogo nomeJogo=jogo1 utilizador=ana`
        - [ ] `iniciaJogo nomeJogo=jogo1 utilizador=joao`
        - [ ] `aposta nomeJogo=jogo1 utilizador=joao valor=10`
        - [ ] `desiste nomeJogo=jogo1 utilizador=ana`
        - [ ] `passa nomeJogo=jogo1 utilizador=ana`
        - [ ] `passa nomeJogo=jogo1 utilizador=joao`
        - [ ] `trocaCartas nomeJogo=jogo1 utilizador=ana quais=aleatório`
        - [ ] `setCartas nomeJogo=jogo1 utilizador=mesa valor=10H,KH,KD,`
        - [ ] `setCartas nomeJogo=jogo1 utilizador=ana valor= KS,KC`
        - [ ] `setCartas nomeJogo=jogo1 utilizador=joao valor= 2S,2H`
        - [ ] `aposta nomeJogo=jogo1 utilizador=ana valor=10`
        - [x] `mensagem de=joao para=ana msg=boa!`
        - [ ] `aposta nomeJogo=jogo1 utilizador=joao valor=20`
        - [ ] `aposta nomeJogo=jogo1 utilizador=ana valor=40`
        - [ ] `desiste nomeJogo=jogo1 utilizador=joao`
        - [ ] `mostraDados`
        - [ ] `undo`
        - [ ] `mostraDados`
        - [ ] `verificaLog utilizador=joao nomeJogo=jogo1`
        - [x] `logout name`
        - [x] `cfg` -> create friendly game
        - [ ] `login` | `log` name -> login of a player
        - [ ] `register` | `reg` name -> register of a new player
        - [ ] `shutdown` | `std` -> shutdown of the game
        - [ ] `logout` | `lout` -> exemplo: `logout name=joaquim` -> logout of player joaquim
        - [ ] `undo` | `u` -> undo of command in game
        - [ ] `redo` | `r` -> redo of command in game
        - [ ] `sendMessage` | `sm` -> example: `sendMessage from=joaquim to=ana Olá, o meu nome é joaquim!` -> sending a mensage from joaquim to ana
        - [ ] `addFriend` | `af` -> example: `addFriend player=joaquim add=antonio` -> adding a player to the friends list -> joaquim added antonio to the friends list
        - [ ] `blockPlayer` | `bp` -> examplo: `blockPlayer player=joaquim block=antonio` -> adding a player to the blocked list -> joaquim added antonio to the blocked list
        - [ ] `comprarPokerChips` | `cpc` -> buy PCs with Money
        - [ ] `listarJogadores` | `lj` -> players list
        - [ ] `listFriend` | `lf` -> friends list
        - [ ] `listBlocked` | `lb` -> blocked list
        - [ ] `listFriendlyGames` | `lfg` -> list of friendly games
        - [ ] `listarJogosCompetitivos` | `ljc` -> list of competitive games
        - [ ] `listarCampeonatos` | `lc` -> list championships games
        - [ ] `listarPontuações` | `lp` -> list ranking
        - [ ] `createFriendlyGame` | `cfg` -> example: `createFriendlyGame name=jogo1 creator=lj` -> create a friendly game
        - [ ] `createCompetitiveGame` | `cjc` -> example: `cjc name=jogo1 creator=lj` -> create a competitive game
        - [ ] `startGame` | `sg` -> example: `startGame name=jogo1 player=lj`
        - [ ] `iniciarTurno` | `sg`
        - [ ] `joinGame` | `jg` -> example: `joinGame name=jogo1 player=ana`
        - [ ] `bet` | `b` -> example: `bet game=jogo1 player=lj amount=5`
        - [ ] `check` | `c` -> example: `check game=jogo1 player=lj`
        - [ ] `fold` | `f` -> example: `fold game=jogo1 player=lj`
        - [ ] `showGameInfo` | `sgi` -> example: `showGameInfo game=jogo1`
        - [ ] `criarJogador` | `cj`
        - [ ] `editarJogador` | `ej`
        - [ ] `expulsarJogador` | `exj`
        - [ ] `verificarAtividadesJogador` | `vaj`
        - [ ] `verJogo` | `vj`
        - [ ] `adicionarJogo` | `aj`
        - [ ] `removerJogo` | `rj`
        - [ ] `help` | `h` -> see all the commands

    - [x] ~~"Mockups" (ex: structure and simulation of payment service launch)~~ -> __no__ need to create visual structures or documentation [DONE]


### Implementation Restrictions[^3]
<!-- Esta parte não foi traduzida por conveniencia atual de trabalho -->
##### __Padrões Creacionais__
  - [x] ~~Fábrica de Objetos~~ -> __[CRUD do Baralho de Cartas](src/main/java/com/poker/factory/card/CardFactory.java)__ [DONE]
  - [x] ~~Fábrica de Fábricas de Objetos~~ -> __Criação dos tipos de Fábrica__ [DONE]
  - [x] ~~Padrão Protótipo~~ -> __Jogos__ [DONE]
  - [x] ~~Singleton~~ -> __Logs e Application Data__ [DONE]
  - [ ] Object Poll -> ... [WIP][^1]
  - [x] ~~Padrão Builders~~ -> __Criação de Jogos__ [DONE]

##### __Padrões de Persistência__
  - [ ] Unit of Work [^1]
  - [x] ~~Identity Map~~ -> __Application Data__ [DONE]

##### __Padrões Arquiteturais de Persistência__
  - [ ] Padrão de Registo Ativo / Active Record
  - [ ] Padrão Table / Gateway
  - [ ] Padrão Record Gateway
  - [ ] Padrão Data Mapper
  - [ ] Padrão Mapeamento

##### __Padrões de Desenho__
  - [x] ~~Estratégias~~ + ~~Padrão Máquina de Estados~~ -> __Regras do Jogo__ [WIP][^1]
  - [x] ~~Padrão de Decoradores~~ -> __Filtragem de acesso ao logs__ [DONE]
  - [x] ~~Padrão de Adaptadores~~ -> __Ligação com os Sistemas "Externos" de Pagamento__ [DONE]
  - [x] ~~Facedes~~ -> __A utilização de uma ou mais fachadas para acesso à lógica__ [DONE]
  - [x] ~~Padrão Template~~ -> __Estados, ...__ [DONE]
  - [x] ~~Padrão Comando~~ -> __Comandos da "Aplicação" CLI__ [DONE]

____

### GRASP
##### __Padrões GRASP__
  - [x] ~~Perito (de Informação)~~ -> [DONE]
  - [x] ~~Criador~~ - Filtro -> [DONE]
  - [x] ~~Baixo Acoplamento~~ -> [DONE]
  - [x] ~~Controlador~~ - Facede -> [DONE]
  - [x] ~~Alta Coesão~~ -> [DONE]
  - [x] ~~Polimorfismo~~ - Filtro + Estados + Logs + ... -> [DONE]
  - [x] ~~Invenção (Artifício)~~ - Facede -> [DONE]
  - [x] ~~Indirecção~~ - CommandManager + Facede + CommandAdapter -> [DONE]
  - [x] ~~Variação Protegida~~ - CommandManager -> [DONE]
____

[^1]: It may undergo changes.
[^2]: To be defined.
[^3]: Implementation of the Taught Standards!


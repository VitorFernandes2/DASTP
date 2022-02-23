# DASTP
Poker Game (_Texas Hold’em_) in Java

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
- A graphical application with a command line that manages all user actions;
- Graphical part only reflects the state / action implemented punctually (it is __not__ necessary to present multiple interfaces on the same plane);
- An admin need to be registered and logged in the application;
- The Pot money should be divided between the users with the highest score and in case of an odd quantity of pot chips, the nearest player to the dealer should receive the odd chip.

____

# __Important Tags__
| __Tag__ | __Meaning__ |
| --- |  ---  |
| __[DONE]__ | __Functionality is done and was tested.__ |
| __[WIP]__ | __Functionality is actualy in working in progress.__ |
| __[TBT]__ | __Functionality is implemented, but it needs to be tested.__ |

____

# __Card Sequences__ (generic):

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

<details><summary><h3>Implemented Rules [PT]</h3></summary>
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
# Machine States

![machine_states](https://user-images.githubusercontent.com/44380629/155400071-0b14a878-8033-4b5d-ad21-372d97597f3f.png)

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

  - [x] Client (__normal user__) [DONE]
    - [x] ~~User registration~~ [DONE]
    - [x] ~~Create Friendly Games~~ [DONE]
    - [x] ~~List of Games (only those games that the user can participate should appear)~~ [DONE]

  - [x] Client (__paid out user__)(all normal user features + extras) [DONE]
    - [x] ~~System Services~~ [DONE]

		
### Virtual Money (Poker-Chips == PCs | Poker-Chips-Jogos == PCJs):

  - [ ] __Acquisition:__
    - [x] ~~System Money Aquisition~~ [DONE]
    - [x] ~~Receive Pot money in Friendly Games (PCJs)~~ [DONE]
    - [x] ~~Receive Pot money in Competitive Games (PCJs and Money)~~ [DONE]
    - [ ] Receive Pot money in Championships Games (PCJs and Money) __[WIP]__

  - [x] __Utilization:__ [DONE]
    - [x] ~~Purchase of Services (ex: paid games creation)~~ [DONE]
    - [x] ~~Games betting~~ [DONE]
    - [x] ~~Cash Conversion~~ [DONE]
    - [x] ~~(We) Set the conversion rate in the competitive game~~ [DONE]

	
### Games (settings depend on game scenario __(Game Type and Configuration)__):

  - [x] ~~(dependent on the amount of user PCs)~~ [DONE]
  - [x] ~~They all start with the same amount of PCJs~~ [DONE]
  - [x] ~~PCJs are obtained after paying an amount of PCs (conversion rate A)~~ [DONE]
  - [x] ~~PCJs are converted to PCs after game completion (A conversion rate)~~ [DONE]
  - [x] ~~Friendly Games (0 PCs == 50 PCJs) they have no return from PCJs to PCs at the end of the game~~ [DONE]
  - [x] ~~Payment / transfer methods~~ [DONE]
  - [x] ~~(5% of the purchase price of PCs is acquired by the company)~~ [DONE]
  - [x] ~~After Min players present, host can start the game~~ [DONE]
    - [x] ~~After the game starts, specified deposit is charged~~ [DONE]

  - [x] Friendly Games __(not configurable)__:
    - [x] ~~0 PCs == 50 PCJs~~ [DONE]
    - [x] ~~3 players (Max)~~ [DONE]
    - [x] ~~Bets of 4 PCJs (Fixed)~~ [DONE]

  - [x] Competitive Games __(configurable)__:
    - [x] ~~Host must own 50 PCs (Min)~~ [DONE]
    - [x] ~~Deposit of 10 PCs (Min)~~ [DONE]
    - [x] ~~Number of players (Min)~~ [DONE]
    - [x] ~~Amount of Bets (Min)~~ [DONE]
    - [x] ~~PC conversion rate for this game~~ [DONE]

  - [x] ~~Tournaments __(their format)__~~ [DONE]


### Communication between Players

  - [x] ~~The identification of players currently online~~ [DONE]
  - [x] ~~Sending messages between players~~ [DONE]
  - [x] __(Optional)__
    - [x] ~~Friend's list~~ [DONE]
    - [x] ~~Block a Player~~ [DONE]

		
### Game Creation

  - [x] ~~Min deposit~~ [DONE]
  - [x] ~~Initial value of PCJs (you can set the conversion rate at the start, only on competitive games)~~ [DONE]
  - [x] ~~Number of Players (Min)~~ [DONE]
  - [x] ~~Creator with 50 PCs or more -> competitive play~~ [DONE]


### Management Operations

  - [x] BackEnd:
    - [x] ~~CRUD, Action Log and other user features~~  [DONE]
    - [x] ~~Creation / Removal / Game Info~~ [DONE]
    - [x] ~~Players Ranking by Games~~ [DONE]


### User Interface

  - [x] CLI application
    - [x] ~~Functions to simulate user and administrator actions~~ [DONE]
    - [x] ~~Registration and Log of user actions~~ [DONE]
    - [x] ~~"Mockups" (ex: structure and simulation of payment service launch)~~ -> __no__ need to create visual structures or documentation [DONE]

____

### Commands
#### COMMON COMMANDS
- [x] `login` | `log` name -> example: `login name=lj` -> login of a player
- [x] `register` | `reg` name -> `register name=lj` || `register name=lj amount=1000` -> register of a new player || with some initial money on the wallet to buy in game currency
- [x] `shutdown` | `std` -> shutdown of the game and commit of the database
- [x] `logout` | `lout` -> exemplo: `logout name=joaquim` -> logout of player joaquim
- [x] `undo` | `u` -> undo of command in game
- [x] `redo` | `r` -> redo of command in game

#### USER COMMANDS
- [x] `sendMessage` | `sm` -> example: `sendMessage from=lj to=ana Hello, my name is lj!` -> sending a mensage from joaquim to ana
- [x] `addFriend` | `af` -> example: `addFriend player=joaquim add=antonio` -> adding a player to the friends list -> joaquim added antonio to the friends list
- [x] `blockPlayer` | `bp` -> example: `blockPlayer player=joaquim block=antonio` -> adding a player to the blocked list -> joaquim added antonio to the blocked list
- [x] `transferMoney` | `tm` -> example: `transferMoney name=lj value=20` -> add real money to the wallet of the player
- [x] `buyPokerChips` | `bpc` -> example: `buyPokerChips name=lj value=60 payment=Paypal` -> buy PCs with a value of 60 of the player's money using a specific payment service
- [x] `listPlayers` | `lp` -> example: `listPlayers` -> players list
- [x] `listFriend` | `lf` -> example: `listFriend name=lj` -> friends list of a player
- [x] `listBlocked` | `lb` -> example: `listBlocked name=lj` -> blocked list of a player
- [x] `listFriendlyGames` | `lfg` -> list of friendly games
- [x] `listCompetitiveGames` | `lcg` -> list of competitive games
- [x] `listTour` | `lt` -> list championships games
- [x] `listRankings` | `lr` -> list of players rankings
- [x] `showPlayersDetails` | `spd` -> show list of all online players details
- [x] `createFriendlyGame` | `cfg` -> example: `createFriendlyGame name=game1 creator=lj` -> create a friendly game
- [x] `createCompetitiveGame` | `ccg` -> example: `createCompetitiveGame name=game1 creator=lj fee=2 bigBlind=4 increment=2` -> create a competitive game (set its fee and bigBlind)
- [x] `startGame` | `sg` -> example: `startGame name=game1 player=lj` -> the host start the game he has created
- [x] `joinGame` | `jg` -> example: `joinGame name=game1 player=ana` -> join a player to a specific game
- [x] `leaveGame` | `lg` -> example: `leaveGame name=game1 player=ana` -> takeout a player from a specific game
- [x] `createTour` | `ct` -> example: `createTournament name=tour1 player=ana` -> a player creates a tournament
- [x] `joinTour` | `jt` -> example: `joinTour name=tour1 player=ana` -> a player joins in a free space of the tournament
- [x] `startTour` | `sto` `startTour name=tour1 player=ana` -> the creator of the tournament starts all the initial qualification games
- [x] `startTourFinal` | `stf` -> example: `startTourFinal name=tour1` -> the creator of the tournament starts the final showdown

#### DEBUG COMMANDS
- [x] `setCards` | `st` -> example: `setCards player=lj game=game1 c1=KS c2=KC` -> set player hand cards for debugging
- [x] `setNewRanking` | `snr` -> example: `setNewRanking player=lj wins=5` -> set player's ranking for debugging
- [x] `setTableCards` | `stc` -> example: `setTableCards game=game1 c1=AS c2=AC c3=AD c4=AH c5=KS` -> set table cards for debugging (`c4` and `c5` can be ommited deppending on the number of cards at the moment in the table)
- [x] `removeRanking` | `rr` -> example: `removeRanking player=lj` -> remove a player's ranking for debugging

#### IN-GAME COMMANDS
- [x] `bet` | `b` -> example: `bet game=game1 player=lj amount=5` -> a player makes a bet
- [x] `check` | `c` -> example: `check game=game1 player=lj` -> a player give check order
- [x] `fold` | `f` -> example: `fold game=game1 player=lj` -> a player gives up this round
- [x] `showGameInfo` | `sgi` -> example: `showGameInfo game=game1` -> show all the games information (players, cards, hands cards, big blind, small blind, next player to play, ...)

#### ADMIN COMMANDS
- [x] `createPlayer` | `cj` -> example: `createPlayer name=vitor` -> create a new player
- [x] `editPlayer` | `ej` -> example: `editPlayer name=vitor newName=vh` -> edit an existing player
- [x] `kick` | `k` -> example: `kick name=vitor` -> kick a player
- [x] `kickFromGame` | `kfg` -> example: `kickFromGame name=lj game=game1` -> kick a player from a game
- [x] `checkActivities` | `ca` -> example: `checkActivities name=vitor` -> show activity logs from this player
- [x] `seeGame` | `vg` -> example: `seeGame game=game1` -> see the game info of a specific game
- [x] `addGame` | `ag` -> example: `addGame name=game1` -> create a new game
- [x] `removeGame` | `rg` -> example: `removeGame name=game1` -> remove an existing game

- [x] `help` | `h` -> see all the commands / shortcuts / descriptions

____

### Implementation Restrictions[^3]
<!-- Esta parte não foi traduzida por conveniencia atual de trabalho -->
##### __Padrões Creacionais__
  - [x] ~~Fábrica de Objetos~~ -> __[CRUD do Baralho de Cartas](src/main/java/com/poker/factory/card/CardFactory.java)__ [DONE]
  - [x] ~~Fábrica de Fábricas de Objetos~~ -> __Criação dos tipos de Fábrica__ [DONE]
  - [x] ~~Padrão Protótipo~~ -> __Jogos__ [DONE]
  - [x] ~~Singleton~~ -> __Logs e Application Data__ [DONE]
  - [x] ~~Padrão Builders~~ -> __Criação de Jogos__ [DONE]

##### __Padrões de Persistência__
  - [x] ~~Unit of Work~~ -> __Rankings__ [DONE]
  - [x] ~~Identity Map~~ -> __Application Data__ [DONE]

##### __Padrões Arquiteturais de Persistência__
  - [x] ~~Padrão de Registo Ativo / Active Record~~ [DONE]
  - [ ] Padrão Table / Gateway
  - [ ] Padrão Record Gateway
  - [ ] Padrão Data Mapper
  - [ ] Padrão Mapeamento

##### __Padrões de Desenho__
  - [x] ~~Estratégias~~ + ~~Padrão Máquina de Estados~~ -> __Regras do Jogo__ [DONE]
  - [x] ~~Padrão de Decoradores~~ -> __Filtragem de acesso ao logs__ [DONE]
  - [x] ~~Padrão de Adaptadores~~ -> __Ligação com os Sistemas "Externos" de Pagamento__ [DONE]
  - [x] ~~Facedes~~ -> __A utilização de uma ou mais fachadas para acesso à lógica__ [DONE]
  - [x] ~~Padrão Template~~ -> __Torneios__ [DONE]
  - [x] ~~Padrão Comando~~ -> __Comandos da "Aplicação" CLI__ [DONE]

____

### GRASP
##### __Padrões GRASP__
  - [x] ~~Perito (de Informação)~~ -> [DONE]
  - [x] ~~Criador~~ -> Filtro [DONE]
  - [x] ~~Baixo Acoplamento~~ -> [DONE]
  - [x] ~~Controlador~~ -> Facede [DONE]
  - [x] ~~Alta Coesão~~ -> [DONE]
  - [x] ~~Polimorfismo~~ -> Filtro + Estados + Logs + ... [DONE]
  - [x] ~~Invenção (Artifício)~~ -> Facede [DONE]
  - [x] ~~Indirecção~~ -> CommandManager + Facede + CommandAdapter [DONE]
  - [x] ~~Variação Protegida~~ -> CommandManager [DONE]
____

[^1]: It may undergo changes.
[^2]: To be defined.
[^3]: Implementation of the Taught Standards!


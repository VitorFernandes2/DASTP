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

# Game Rules - [link_1](http://www.pokerlistings.com/poker-rules) | [link_2](https://www.pokernews.com/poker-rules/texas-holdem.htm)  | [link_3](https://pt.wikipedia.org/wiki/Texas_hold_%27em)

#### Important Notes:
- A graphical application with a command line that manages all user actions.
- Graphical part only reflects the state / action implemented punctually (it is __not__ necessary to present multiple interfaces on the same plane).

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

- [ ] Royal Flush — cinco cartas do mesmo naipe, de ás a dez; Exemplo: __[A♥] [K♥] [Q♥] [J♥] [10♥]__
- [ ] Straight Flush — cinco cartas do mesmo naipe e classificadas consecutivamente; Exemplo: __[9♣] [8♣] [7♣] [6♣] [5♣]__
- [ ] Four of a Kind — quatro cartas do mesmo valor; Exemplo: __[Q♣] [Q♥] [Q♦] [Q♠] [4♦]__
- [ ] Full House — três cartas do mesmo valor e mais duas cartas do mesmo valor; Exemplo: __[J♣] [J♥] [J♠] [8♦] [8♥]__
- [ ] Flush — quaisquer cinco cartas do mesmo naipe; Exemplo: __[A♠] [J♠] [8♠] [5♠] [2♠]__
- [ ] Straight — quaisquer cinco cartas classificadas consecutivamente; Exemplo: __[Q♣] [J♦] [10♥] [9♠] [8♦]__
- [ ] Three of a Kind — três cartas do mesmo valor; Exemplo: __[8♣] [8♠] [8♦] [K♣] [4♥]__
- [ ] Two Pair — duas cartas do mesmo valor e mais duas cartas do mesmo valor; Exemplo: __[A♠] [A♣] [J♦] [J♣] [7♠]__
- [ ] One Pair — duas cartas do mesmo valor; Exemplo: __[10♥] [10♣] [9♥] [4♦] [2♦]__
- [ ] High Card — cinco cartas incomparáveis; Exemplo: __[A♣] [J♦] [10♠] [5♣] [2♥]__

</p>
</details>

____

# Functionalities:

### Games System:
- [ ] User tells the system that he wants to join that game
- [ ] Allocate the User to the selected game (only if you have enough PCs for the initial deposit)
- [ ] One user == one game
- [x] ~~Creating the Deck of Cards~~
			

### Jogadores:

  - [ ] Client (__normal user__)
    - [ ] User registration
    - [ ] Create Friendly Games
    - [ ] List of Games (only those games that the user can participate should appear)

  - [ ] Client (__paid out user__)(all normal user features + extras)
    - [ ] System Services [^2]

		
### Virtual Money (Poker-Chips == PCs | Poker-Chips-Jogos == PCJs):

  - [ ] __Acquisition:__
    - [ ] Compra no Sistema
    - [ ] Ganho em Jogos / Torneios

  - [ ] __Utilization:__
    - [ ] Purchase of Services (ex: paid games creation)
    - [ ] Games betting
    - [x] ~~Cash Conversion~~
    - [ ] (We) Set the conversion rate in the competitive game

	
### Games (settings depend on game scenario __(Game Type and Configuration)__):

  - [ ] (dependent on the amount of user PCs)
  - [ ] They all start with the same amount of PCJs
  - [x] ~~PCJs are obtained after paying an amount of PCs (conversion rate A)~~
  - [x] ~~PCJs are converted to PCs after game completion (A conversion rate)~~
  - [ ] Friendly Games (0 PCs == 50 PCJs) they have no return from PCJs to PCs at the end of the game
  - [x] ~~Payment / transfer methods~~
  - [x] ~~(5% of the purchase price of PCs is acquired by the company)~~
  - [ ] After Min players present, host can start the game
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

  - [ ] The identification of players currently online
  - [ ] Sending messages between players
  - [ ] __(Optional)__
    - [ ] Friend's list
    - [ ] Block a Player

		
### Game Creation

  - [ ] Min and Max deposit
  - [ ] Initial value of PCJs (you can set the conversion rate at the start)
  - [ ] Number of Players (Min)
  - [ ] Creator with 50 PCs or more -> competitive play


### Management Operations

  - [ ] BackEnd:
    - [ ] CRUD, Action Log and other user features
    - [ ] Creation / Removal / Game Info


### User Interface

  - [ ] CLI application
    - [ ] Functions to simulate user and administrator actions
    - [ ] Registration and Log of user actions
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
        - [ ] `setCartas nomeJogo=jogo1 utilizador=mesa valor=10H, KH,KD,`
        - [ ] `setCartas nomeJogo=jogo1 utilizador=ana valor= KS,KC`
        - [ ] `setCartas nomeJogo=jogo1 utilizador=joao valor= 2S,2H`
        - [ ] `aposta nomeJogo=jogo1 utilizador=ana valor=10`
        - [ ] `mensagem de=joao para=ana msg=boa!`
        - [ ] `aposta nomeJogo=jogo1 utilizador=joao valor=20`
        - [ ] `aposta nomeJogo=jogo1 utilizador=ana valor=40`
        - [ ] `desiste nomeJogo=jogo1 utilizador=joao`
        - [ ] `mostraDados`
        - [ ] `undo`
        - [ ] `mostraDados`
        - [ ] `verificaLog utilizador=joao nomeJogo=jogo1`

    - [x] ~~"Mockups" (ex: structure and simulation of payment service launch)~~ -> __no__ need to create visual structures or documentation


### Implementation Restrictions[^3]
<!-- Esta parte não foi traduzida por conveniencia atual de trabalho -->
##### __Padrão Comando__
  - [x] ~~Padrão Comando~~ -> __Comandos da "Aplicação" CLI__

##### __Padrões Creacionais__
  - [x] ~~Fábrica de Objetos~~ -> __[CRUD do Baralho de Cartas](src/main/java/com/poker/factory/card/CardFactory.java)__
  - [ ] Padrão Protótipo -> fazer clonning
  - [x] ~~Singleton~~ -> Logs e Application Data
  - [ ] Object Poll -> ...
  - [x] ~~Padrão Builders~~ -> __Criação de Jogos__

##### __Padrões de Persistência__
  - [ ] Padrão de Registo Ativo
  - [ ] Unit of Work
  - [ ] Identity Map

##### __Padrões de Desenho__
  - [ ] ~~Estratégias~~ + Padrão Máquina de Estados -> __Regras do Jogo__ [WIP]
  - [x] ~~Padrão de Decoradores~~ -> __Filtragem de acesso ao logs__
  - [x] ~~Padrão de Adaptadores~~ -> __Ligação com os Sistemas "Externos" de Pagamento__
  - [x] ~~Facedes~~ -> __A utilização de uma ou mais fachadas para acesso à lógica__
  - [ ] Padrão Template -> __Implementação de Torneios__

____

### GRASP
##### __Padrões GRASP__
  - [x] ~~Perito (de Informação)~~
  - [x] ~~Criador~~ - Filtro
  - [x] ~~Baixo Acoplamento~~
  - [x] ~~Controlador~~ - Facede
  - [x] ~~Alta Coesão~~
  - [x] ~~Polimorfismo~~ - Filtro + Estados + Logs + ...
  - [x] ~~Invenção (Artifício)~~ - Facede
  - [x] ~~Indirecção~~ - CommandManager + Facede + CommandAdapter
  - [x] ~~Variação Protegida~~ - CommandManager
____

[^1]: It may undergo changes.
[^2]: To be defined.
[^3]: Implementation of the Taught Standards!


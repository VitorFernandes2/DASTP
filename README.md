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

# Regras de Jogo - [link_1](http://www.pokerlistings.com/poker-rules) | [link_2](https://www.pokernews.com/poker-rules/texas-holdem.htm)

#### Notas Importantes:
- Uma aplicação gráfica com uma linha de comandos que gere todas as ações dos utilizadores
- Parte gráfica apenas reflete o estado / ação concretizada pontualmente (__não__ é necessário a apresentação de múltiplas interfaces no mesmo plano)

____

# Sequências de Cartas (genéricas):

| Regras | Exemplo |
| --- |  ---  |
| Royal Flush — cinco cartas do mesmo naipe, de ás a dez. | __[A♥] [K♥] [Q♥] [J♥] [10♥]__ |
| Straight Flush — cinco cartas do mesmo naipe e classificadas consecutivamente. | __[9♣] [8♣] [7♣] [6♣] [5♣]__ |
| Four of a Kind — quatro cartas do mesmo valor. | __[Q♣] [Q♥] [Q♦] [Q♠] [4♦]__ |
| Full House — três cartas do mesmo valor e mais duas cartas do mesmo valor. | __[J♣] [J♥] [J♠] [8♦] [8♥]__ |
| Flush — quaisquer cinco cartas do mesmo naipe. | __[A♠] [J♠] [8♠] [5♠] [2♠]__ |
| Straight — quaisquer cinco cartas classificadas consecutivamente. | __[Q♣] [J♦] [10♥] [9♠] [8♦]__ |
| Three of a Kind — três cartas do mesmo valor. | __[8♣] [8♠] [8♦] [K♣] [4♥]__ |
| Two Pair — duas cartas do mesmo valor e mais duas cartas do mesmo valor. | __[A♠] [A♣] [J♦] [J♣] [7♠]__ |
| One Pair — duas cartas do mesmo valor. | __[10♥] [10♣] [9♥] [4♦] [2♦]__ |
| High Card — cinco cartas incomparáveis. | __[A♣] [J♦] [10♠] [5♣] [2♥]__ |

<details><summary>Regras Implementadas</summary>
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

# Funcionalidades:

### Sistema de Jogos:
- [ ] Utilizador indica ao sistema que quer juntar-se àquele jogo
- [ ] Aloca o Utilizador ao jogo selecionado (só se possuir PCs suficientes para o depósito inicial)
- [ ] Um utilizador == um jogo
- [ ] Criação do Baralho de Cartas
			

### Jogadores:

  - [ ] Cliente (__normal__)
    - [ ] Registo do utilizador
    - [ ] Criar Jogos Amigáveis
    - [ ] Lista de Jogos (só aparecem aqueles que consegue participar)

  - [ ] Cliente (__pago__)(todas as funcionalidades do normal + extras)
    - [ ] Serviços do Sistema [^2]

		
### Dinheiro Virtual (Poker-Chips == PCs | Poker-Chips-Jogos == PCJs):

  - [ ] Aquisição:
    - [ ] Compra no Sistema
    - [ ] Ganho em Jogos / Torneios

  - [ ] Utilização:
    - [ ] Aquisição de Serviços (ex: criação de jogos)
    - [ ] Apostas em Jogos
    - [ ] Conversão em Dinheiro
    - [ ] (Nós) Definimos o rate de conversão

	
### Jogos (definições dependem do cenário do jogo __(Tipo de Jogo e Configuração)__):

  - [ ] (dependente do montante de PCs do utilizador)
  - [ ] Começam todos com o mesmo montante de PCJs
  - [ ] PCJs são obtidos após o pagamento de um montante de PCs (taxa de conversão A)
  - [ ] PCJs são convertidos em PCs após a finalização do jogo (taxa de conversão A)
  - [ ] Amigáveis (0 PCs == 50 PCJs) não têm retorno de PCJs
  - [ ] Métodos de pagamento / transferência
  - [ ] (5% do valor de compra das PCs são adquiridas pela empresa)
  - [ ] Após Min jogadores presentes, host pode dar inicio ao jogo
    - [ ] Após inicio do jogo, depósito especificado é cobrado

  - [ ] Jogos Amigáveis __(Não configuráveis)__:
    - [ ] 0 PCs == 50 PCJs
    - [ ] 3 jogadores (Max)
    - [ ] Apostas de 4 PCJs (Fixo)

  - [ ] Jogos Competitivos __(Configuráveis)__:
    - [ ] Host tem de possuir 50 PCs (Min)
    - [ ] Depósito de 10 PCs (Min)
    - [ ] Número de jogadores (Min)
    - [ ] Valor de Apostas (Min)
    - [ ] Rate de conversão dos PCs

  - [ ] Torneios __(O seu formato)__[^2]


### Comunicação entre Jogadores

  - [ ] A identificação dos jogadores actualmente online
  - [ ] Envio de mensagens entre jogadores
  - [ ] __(Opcional)__
    - [ ] Lista de Amigos
    - [ ] Bloquear de Jogador

		
### Criação de Jogos

  - [ ] Depósito Min e Max
  - [ ] Valor inicial de PCJs (poderá definir o rate de conversão)
  - [ ] Número de Jogadores (Min)
  - [ ] Criador com 50 PCs ou mais -> jogo competitivo


### Operações de Gestão

  - [ ] BackEnd:
    - [ ] CRUD, Log de ações e outras funcionalidades de utilizadores
    - [ ] Criação / Remoção / Info de jogos


### Interface de Utilizador

  - [ ] Aplicação CLI
    - [ ] Funcionalidades de simulação de ações de utilizadores e administradores
    - [ ] Registo e Log das ações dos utilizadores
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

    - [ ] "Mockups" (ex: estrutura e simulação do lançamento do serviço de pagamento) -> __não__ é necessário a criação de estruturas visuais nem documentação


### Restrições de Implementação[^3]

  - [ ] Padrão de Adaptadores -> __Ligação com os Sistemas "Externos" de Pagamento__
  - [ ] Padrão Comando -> __Comandos da "Aplicação" CLI__
  - [x] ~~Fábrica de Objetos~~ -> __[CRUD do Baralho de Cartas](src/main/java/com/poker/factory/card/CardFactory.java)__
  - [ ] Padrão Builders -> __Criação de Jogos__
  - [ ] Estratégias + Padrão Máquina de Estados -> __Regras do Jogo__
  - [ ] A utilização de uma ou mais fachadas para acesso à lógica -> __Facedes__
  - [ ] __Utilização de Padrões de Persistência:__
    - [ ] Padrão de Registo Ativo
  - [ ] Padrão de Decoradores -> __Filtragem de acesso ao logs || Políticas de Criação de Jogos__
  - [ ] Padrão Template -> __Implementação de Torneios__

____

[^1]: Poderá sofrer mudanças.
[^2]: Por definir.
[^3]: Implementação dos Padrões Lecionados!


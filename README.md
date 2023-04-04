# Arimaa

Game __arimaa__.


### Todo

1. Threads --> real time hours interactin with game
2. Add Game Logic
3. Save Game --> write state of board to file and use function to recreate
4. Player to Player/ PC to Player options

### Rules

Pravidla. Ty nejzákladnější jsou (není to úplný výčet):
- [ ] goal je dostat rabbit na druhou stranu desky
- [ ] Hrací deska 8x8
- [ ] 6 figurek - elephant 1x, camel 1x, horse 2x, dog 2x, cat 2x, rabbit 8x
- [ ] figurky jsou v prvních dvou řadách, ale nemají fixní pozici
- [ ] figurky se pohybují forward, backwards, right, left až na rabbit ten nesmí backwards
- [ ] Je možné použít 1 až 4 tahy za kolo (mohu pohnout více figurkami -> celkem 4 posuny), alespoň jeden krok
- [ ] Silnější figurky mohou tlačit figurky protivníka (počítá se jako jeden tah) a jít na jejich políčko
- [ ] Silnější figurky mohou táhnout figurky protivníka (počítá se jako jeden tah) na políčko, kde byli
- [ ] Mizení figurek na polích “past” - ty jsou 4, figurka na něm zmizí poku se na něm nachází a nemá kolem sebe
  přátelskou figurku
- [ ] Zmražení figurky (v závislosti na síle figurky), figurka zlata je blízko silnější figurky stříbrné, tak je frozen
  pokud vedle sebe nemá přátelskou figurku
- [ ] Ukončení hry při přechodu králíka přes celé pole
- [ ] Ukončení hry při zablokování jednoho hráče
- [ ] Každý hráč na začátku hry může figurky rozestavit libovolně dle pravidel
- [ ] Implementace hracích hodin - kolik času každý jeden hráč strávil přemýšlením
- [ ] Je možné hru uložit, nahrát a odkrokovat po jednotlivých tazích. K tomu bude použita oficiální Arima notace
- [ ] Je možné hrát uživatel proti uživateli na stejném počítači
- [ ] Je možné hrát hru proti počítači. Zde se nepředpokládá nějaká sofistikovaná umělá inteligence. Naprosto postačuje generátor 1 až 4 náhodných příspustných tahů. Do těchto tahů patří i tažení a tlačení soupeřových figurek.



# Arimaa

Game __arimaa__.


### Todo

1. Threads --> real time hours interaction with game
2. Save Game --> write state of board to file and use function to recreate
3. Player to Player/ PC to Player options

### Rules

Pravidla. Ty nejzákladnější jsou (není to úplný výčet):
- [x] goal je dostat rabbit na druhou stranu desky
- [x] Hrací deska 8x8
- [x] 6 typu figurek - elephant 1x, camel 1x, horse 2x, dog 2x, cat 2x, rabbit 8x
- [x] figurky se pohybují forward, backwards, right, left až na rabbit ten nesmí backwards
- [x] Je možné použít 1 až 4 tahy za kolo (mohu pohnout více figurkami -> celkem 4 posuny), alespoň jeden krok
- [x] Silnější figurky mohou tlačit figurky protivníka (počítá se jako jeden tah) a jít na jejich políčko
- [x] Silnější figurky mohou táhnout figurky protivníka (počítá se jako jeden tah) na políčko, kde byli
- [x] Mizení figurek na polích “past” - ty jsou 4, figurka na něm zmizí pokud se na něm nachází a nemá kolem sebe
  přátelskou figurku
- [x] Zmražení figurky (v závislosti na síle figurky), figurka zlata je blízko silnější figurky stříbrné, tak je frozen
  pokud vedle sebe nemá přátelskou figurku
- [x] Ukončení hry při přechodu králíka přes celé pole
- [x] Ukončení hry při zablokování jednoho hráče


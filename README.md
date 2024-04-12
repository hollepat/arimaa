# Description

This project implements a game called Arimaa (https://en.wikipedia.org/wiki/Arimaa). The game is a chess variant that is
designed to be difficult for computers to play well. The game is played on an 8x8 board with 6 types of pieces: elephant,
camel, horse, dog, cat, and rabbit. The goal of the game is to get the rabbit to the other side of the board. The game
is played in turns, with each player making 1-4 moves per turn. The game is won by either getting the rabbit to the
other side of the board or by blocking the opponent from making a move.

## Pieces Constellation

At the beginning of the game, the pieces are arranged by player in first two rows as they want.

## Rules
The rules of the game are as follows:
1. The game is played on an 8x8 board with 6 types of pieces: elephant, camel, horse, dog, cat, and rabbit.
2. The game is played in turns, with each player making 1-4 moves per turn.
3. The game is won by either getting the rabbit to the other side of the board or by blocking the opponent from making a move.
4. The pieces move forward, backward, left, or right, but not diagonally. Except for the rabbit which cannot move backward.
5. Stronger pieces can push or pull weaker pieces. "rabbit < cat < dog < horse < camel < elephant"
6. Pieces can be killed on 4 squared on the board called "traps". If Piece is pushed/pulled this spot and NO FRIEND piece
is next -> Piece is killed.
7. If piece has no friend piece next to itself, and it stays next to stronger enemy piece it is "frozen" and cannot move.


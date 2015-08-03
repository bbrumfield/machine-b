Overview
========
MachineB is a UCI-compatible chess engine. It is currently coded in a fashion that favors readability over efficiency (at the moment, efficiency is fairly awful). This will likely always be the case, though as time goes on the gap between the two will decrease (and the quality of both will increase).




UCI commands that have been implemented
---------------------------------------
* uci
* isready
* ucinewgame
* position [fen (fenstring) | startpos] [moves move1 (move2 move3 ...)]
* go
* quit




Additional commands that have been implemented
----------------------------------------------
* divide [ply depth]
* perft [ply depth]




UCI commands that are not yet implemented
-----------------------------------------
* debug
* setoption
* register
* stop
* ponderhit
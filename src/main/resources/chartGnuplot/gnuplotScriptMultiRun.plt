set style data lines
set xlabel "Iteracje"
set ylabel "Średnia najlepsza wartosc funkcji"
#set key at 95,80
#set label "b=1.4, k=6" at 5,110
plot 'data.txt' using 1:2:3 with yerrorbars lc 4 title "odchylenie standardowe",\
 'data.txt' using 1:2 with lines lc 4 lw 3 title "Średnia najlepsza wartości",\

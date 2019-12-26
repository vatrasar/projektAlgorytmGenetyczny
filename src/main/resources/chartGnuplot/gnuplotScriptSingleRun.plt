set style data lines
set xlabel "iteration"
set ylabel "agents [%]"
#set key at 95,80
#set label "b=1.4, k=6" at 5,110
set label "num of experiments=50" at 200,110
plot  'data.txt' using 1:2 with lines lc 4 lw 3 title "avg num of all-C"

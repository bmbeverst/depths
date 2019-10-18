#!/bin/bash
# Adding line number using a script

i=0;
fire=0;
while read lines
  do
  #echo $fire
  if [[ $lines = *"fireball"* ]] && [[ $fire -eq 0 ]]; then
    fire=1;
  fi
  if [[ ! $lines =~ ^$ ]]
    then
    if [[ $lines = *"index"* ]] && [[ $fire -eq 1 ]]; then
      echo "index: $i"
      ((i++))
    else
      echo "$lines"
    fi
  else
    echo $lines
  fi
done < $1
exit


# n=0; for f in *.png; do mv "$f" "fireball_$((n++)).png"; done

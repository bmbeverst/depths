#!/bin/bash
# Adding line number using a script not used any more

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

# Fireball processing
# n=0; for f in *.png; do mv "$f" "fireball_`printf %03d $n`.png"; ((n++)); done
# mogrify -modulate 90,100,10 fireball*
# mogrify -channel rgba -alpha set -fuzz 4% -fill none -opaque "#000000" fireball*

# Torch processing
# n=0; for f in *.png; do mv "$f" "torchlight_`printf %03d $n`.png"; ((n++)); done
# mogrify -channel rgba -alpha set -fuzz 12% -fill none -opaque "#000000" torchlight*


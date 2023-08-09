# Firstly, Run this command to make it executable - chmod +x bash_learning.sh
# sharp ban #\! - then location of the bash env - #\! /usr/bin/bash
# to run the file - ./file_name.sh
# ECHO COMMAND
# echo Hello World!

# VARIABLES
# UPPERCASE by convention
# contains letters, numbers, underscores
# NAME="Jack"
# echo "My name is $NAME"
# echo "My name is ${NAME}"

# USER INPUT
# read -p "Enter your name: " NAME
# echo "Hello $NAME, nice to see you!"

# SIMPLE IF STATEMENT
# Replaced == with = for string comparison. Both are valid, but = is more portable.
# if  [ "$NAME" = "John" ]
# then
#     echo "Your name is John"
# fi
# to end the if statement

# IF-ELSE
# if  [ "$NAME" = "John" ]
# then
#     echo "Your name is John"
# else
#     echo "Your name is other"
# fi

# ELSE-IF (elif)
# if  [ "$NAME" = "John" ]
# then
#     echo "Your name is John"
# elif [ "$NAME" = "Jack" ]
# then
#     echo "Your name is Doe"
# else
#     echo "Your name is other"
# fi

# COMPARISON
# NUM1=3
# NUM2=5
# if [ "$NUM1" -gt "$NUM2" ]
# then
#     echo "$NUM1 is greater than $NUM2"
# else
#     echo "$NUM1 is less than $NUM2"
# fi

########
# val1 -eq val2 Returns true if the values are equal
# val1 -ne val2 Returns true if the values are not equal
# val1 -gt val2 Returns true if val1 is greater than val2
# val1 -ge val2 Returns true if val1 is greater than or equal to val2
# val1 -lt val2 Returns true if val1 is less than val2
# val1 -le val2 Returns true if val1 is less than or equal to val2
########

# FILE CONDITIONS
# FILE="test.txt"
# if [ -e "$FILE" ]
# then
#     echo "$FILE exists"
# else
#     echo "$FILE does not exists"
# fi

########
# -d file   True if the file is a directory
# -e file   True if the file exists (note that this is not particularly portable, thus -f is generally used)
# -f file   True if the provided string is a file
# -g file   True if the group id is set on a file
# -r file   True if the file is readable
# -s file   True if the file has a non-zero size
# -u    True if the user id is set on a file
# -w    True if the file is writable
# -x    True if the file is an executable
########

# #CASE STATEMENT
# read -p "Are you 21 or over? Y/N " ANSWER
# case "$ANSWER" in
#     [yY] | [yY][eE][sS]) # means if user inputs y in any case or yes in any case
#         echo "You can have a driving license"
#         ;; # ending it with a double semi colon
#     [nN] | [nN][oO])
#         echo "Sorry, you're not eligible"
#         ;;
#     \*)
#         echo "Please enter y/yes or n/no"
#         ;;
# esac # ending the case

# # SIMPLE FOR LOOP
# NAMES="John Doe Alice Bob"
# for NAME in $NAMES
#     do
#         echo "hello, $NAME"
# done

# # FOR LOOP TO RENAME FILES
# FILES=$(ls *.txt)
# NEW="new"
# for FILE in $FILES
#     do
#         echo "Remaining $FILE to new-$FILE"
#         mv $FILE $NEW-$FILE
# done

# # WHILE LOOP - READ THROUGH A FILE LINE BY LINE
# # LINE=1
# while read -r CURRENT_LINE
#   do
#     echo "$LINE: $CURRENT_LINE"
#     ((LINE++))
# done < "./new-1.txt"

# FUNCTION - below will work in bash shell
# function sayHello() {
#   echo "Hello World"
# }
# # sayHello
#  sayHello() { # will work in zsh shell
#   echo "Hello World"
# }
# sayHello

# # FUNCTION WITH PARAMS
# greet() {
#   echo "Hello, I am $1 and I am $2"
# }

# greet "Brad" "36"

# # CREATE FOLDER AND WRITE TO A FILE
# mkdir hello
# touch "hello/world.txt"
# echo "Hello World" >> "hello/world.txt"
# echo "Created hello/world.txt"
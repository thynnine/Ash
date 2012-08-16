This is the readme for the ASH package (ash.jar) containing the Atomic Structure Handler program.
ASH is a utility for creating and modifying atomic structures and visualizing the built geometries as you go. 


Dependencies
------------

The program is written in Java 1.6. It uses the jline package by Marc Prud'hommeaux for emulating a shell. You can get jline at http://jline.sourceforge.net/ . To run the ash.jar program, type "java -jar ash.jar" in the command prompt while in the directory where you have the package.


Usage
-----

The user can operate on the structures via the command line, and the window displaying the structure should update automatically when required. Commands include operations such as 'shift', 'rotate', 'scale', etc. Currently the program recognizes about 30 different commands. There is no real manual, but to view a list of available commands, type "list command" within the program. (You could also just hit tab and see what commands the completor suggests.) A short description of the syntax of each command can be seen by typing "man X" where X is the name of a command.

The viewpoint can be adjusted by dragging with the mouse over the graphical window, or by pressing the arrow keys. Pressing and holding the 's' key actives mouse shift mode, where atoms can be moved by dragging with the mouse. Likewise holding 'r' activates mouse rotate mode, where clusters (not atoms!) can be rotated in place by dragging.
The commands 'mouse info' and 'mouse pick' also activate mouse features.

Frames can be browsed using the 'n' and 'p' keys (next and previous, respectively). 

The program can be scripted. A script is read using the command "open script X" where X is a file containing the script. In addition to all the normal commands the program recognizes, scripts may also contain "if-else" and "while" logic structures. For instance:

define nx 0
while nx < 10
   define nx nx+1
   print nx
endwhile


Formats
-------

Currently, the program can only read and write the XYZ format and the POSCAR format used by VASP. It can also write a Python script which can be read in ASE. I'll add support for other formats later.

Contact
-------

Please report bugs to tjhynnin at gmail.com.



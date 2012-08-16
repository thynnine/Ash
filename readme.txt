This is a short readme for the ASH package (ash.jar) containing the Atomic Structure Handler program:

The program is written in Java 1.6. It uses the jline package by Marc Prud'hommeaux for emulating a shell. You can get jline at http://jline.sourceforge.net/ . To run the ash.jar program, type "java -jar ash.jar" in the command prompt while in the directory where you have the package.

ASH is a utility for creating and modifying atomic structures and visualizing the built geometries as you go. The user can operate on the structures via the command line, and the window displaying the structure should update automatically when required. The viewpoint can be adjusted by dragging with the mouse over the graphical window, or by pressing the arrow keys. Frames can be browsed using the 'n' and 'p' keys (next and previous, respectively). Currently the program recognizes about 30 different commands. It is still missing some obvious ones, but in general it can already do quite a lot. There is no real manual, but to view a list of available commands, type "list command" within the program. (You could also just hit tab and see what commands the completor suggests.) A short description of the syntax of each command can be seen by typing "man X" where X is the name of a command. Hopefully, the man pages will get a proper description of all the commands in the future. Meanwhile, you can try and see what they all do.

The program can be scripted. A script is read using the command "open script X" where X is a file containing the script. In addition to all the normal commands the program recognizes, scripts may also contain "if-else" and "while" logic structures. See the examples for proper syntax.

Currently, the program can only read and write the XYZ format and the POSCAR format used by VASP. It can also write a Python script which can be read in ASE. I'll add support for other formats later.

Please report bugs to tjhynnin@gmail.com.



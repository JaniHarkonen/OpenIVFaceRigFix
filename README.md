# OpenIV Face Rig Fix

This is a small program I built for fixing faulty face rigs in OpenIV animation files. **Warning!** This program is untested and may contain bugs.

## How to

1. Download the built JAR-file, `OpenIVFaceRigFix/build/OpenIVFaceRigFix.jar`, as well as the batch file, `OpenIVFaceRigFix/build/OpenIVFaceRigFix.bat`, from the respository.
Place these files into the same folder and run the batch file. The batch file runs the JAR in console ensuring that the program doesn't execute silently (not showing up in console) nor
terminate prematurely.

2. The program will prompt you to enter the paths of the `ONIM` files that you want to fix. After entering a path, press ENTER and either enter the next path or press ENTER again to
fix the files.

3. The program will list the paths of the files being fixed and finally show a summary of the number of files that were fixed, if the fix was successful. If not, an error will be shown.

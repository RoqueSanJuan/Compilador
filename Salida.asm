.386
.model flat, stdcall
.stack 200h ;Tamanio de la pila
option casemap :none
include C:\masm32\include\windows.inc
include C:\masm32\include\kernel32.inc
include C:\masm32\include\user32.inc
include C:\masm32\include\masm32.inc
includelib C:\masm32\lib\kernel32.lib
includelib C:\masm32\lib\user32.lib
includelib C:\masm32\lib\masm32.lib
.data
_1 DW 1
_10 DW 10
_0 DW 0
_x DW ?
_y DW ?
funciona_do_until DB  'funciona do until' , 0
msjDivision_Cero DB 'Error: Divisor igual a cero' , 0
msjPerdida_Info DB 'Error: Perdida de informacion en la conversion' , 0
.code
start:
MOV AX, _10
MOV _y, AX
MOV AX, _0
MOV _x, AX
Label_2:
MOV AX, _x
ADD AX, _1
MOV _x, AX
MOV AX, _x
CMP AX, _10
JE Label_8
JMP Label_2
Label_8:
MOV AX, _x
CMP AX, _y
JNE Label_12
invoke MessageBox, NULL, addr funciona_do_until, addr funciona_do_until, MB_OK
Label_12:
JMP LABEL_END
LABEL_Division_Cero:
invoke MessageBox, NULL, addr msjDivision_Cero, addr msjDivision_Cero, MB_OK
JMP LABEL_END
LABEL_Perdida_de_Info:
invoke MessageBox, NULL, addr msjPerdida_Info, addr msjPerdida_Info, MB_OK
JMP LABEL_END
LABEL_END:
invoke ExitProcess, 0
end start

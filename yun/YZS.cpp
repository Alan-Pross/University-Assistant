#include "pch.h"
#include <iostream>
#include <windows.h>

int main()
{
	//c++控制台小程序，用来方便启动服务器
	HWND hwnd = ::GetConsoleWindow();
	SendMessage(hwnd, WM_SYSCOMMAND, SC_MAXIMIZE, 0);
	system("java -jar ./YZS-2.1.2.RELEASE.jar > log.txt");
}

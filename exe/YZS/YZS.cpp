#include <iostream>
#include <windows.h>
#include <string>
#include <chrono>

const std::string getCurrentSystemTime()
{
	auto tt = std::chrono::system_clock::to_time_t
	(std::chrono::system_clock::now());
	struct tm* ptm = localtime(&tt);
	char date[60] = { 0 };
	sprintf(date, "%d%02d%02d%02d%02d%02d",
		(int)ptm->tm_year + 1900, (int)ptm->tm_mon + 1, (int)ptm->tm_mday,
		(int)ptm->tm_hour, (int)ptm->tm_min, (int)ptm->tm_sec);
	return std::string(date);
}

int main()
{
	//c++控制台小程序，用来方便启动服务器
	HWND hwnd = ::GetConsoleWindow();
	SendMessage(hwnd, WM_SYSCOMMAND, SC_MAXIMIZE, 0);
	system("copy /y .\\tee.exe C:\\Windows\\System32\\");

	std::string log = getCurrentSystemTime() + ".txt";
	std::string cmd = "java -jar ./YZS-2.1.2.RELEASE.jar | tee " + log;
	
	system(cmd.c_str());
}

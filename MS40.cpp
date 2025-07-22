


#include <iostream>
#include <string>
#include <cmath>
#include <conio.h>
using namespace std;

struct Time
{
    int hours;
    int minutes;
    int seconds;
};

int main()
{
    Time s_time;
    Time e_time;
    Time el_time;

    cout<<"Enter the starting time of the race :";
    cin>>s_time.hours>>s_time.minutes>>s_time.seconds;
    cout<<"\nEnter the ending time of the race : ";
    cin>>e_time.hours>>e_time.minutes>>e_time.seconds;

    int s_seconds = s_time.hours*3600 + s_time.minutes*60 + s_time.seconds;
    int e_seconds = e_time.hours*3600 + e_time.minutes*60 + e_time.seconds;
    int t_seconds = e_seconds - s_seconds;

    el_time.hours = floor(t_seconds/3600);
    t_seconds -= el_time.hours*3600;
    el_time.minutes = floor(t_seconds/60);
    t_seconds -= el_time.minutes*60;
    el_time.seconds = t_seconds;

    cout<<"\n\nThe Elaspsed Time  = ";
    cout<<el_time.hours<<" : "<<el_time.minutes<<" : "<<el_time.seconds;

    getch();
    return 0;   
}


#include <iostream>
#include <stdio.h>
#include <string>
#include <cstring>
#include <cmath>
#include <conio.h>
#include <stdlib.h>
using namespace std;
int main()
{
    string m_name;
    string f_name;
    string l_name;
    cout<<"Enter your First Name : ";
    getline(cin,f_name);
    cout<<"Enter your Last Name : ";
    cin>>l_name;
    cout<<"\n\n";
    string fl_name = f_name + " " + l_name;
    cout<<"Your Full Name Is : "<<fl_name;
    cout<<"\n\n";
    cout<<"The Length of Your Name is : "<<(fl_name.length()-2);
    cout<<"\n\n";
    cout<<"The First Character of Your Name Is : "<<fl_name[0];
    cout<<"\n\n";
    cout<<"The Last Character of Your Name Is : "<<fl_name[(fl_name.length()-1)];
    cout<<"\n\n";
    cout<<"Your Initials Are : "<<f_name[0];
    size_t firstSpacePos = f_name.find(' '); 
    if (firstSpacePos != string::npos) 
        {
            size_t secondWordStart = f_name.find_first_not_of(' ', firstSpacePos + 1);
            if (secondWordStart != string::npos) 
                { 
                    cout<<f_name[secondWordStart];
                }
            else
                { 
                    cout<<" ";
                }
       }
     else 
       { 
           cout<<" ";
       }
    cout<<l_name[0];
    getch();
    return 0;
}


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
    int quiz,mid_term,final_term,total;
    cout<<"\nEnter you Quiz Marks out of 20 : ";
    cin>>quiz;
    cout<<"\nEnter Your Mid Term Marks out of 30 : ";
    cin>>mid_term;
    cout<<"\nEnter Your Final Term marks out of 50 : ";
    cin>>final_term;
    total = quiz + mid_term + final_term;
    cout<<"\n\n Your Total Marks Are : "<<total;
    if((quiz + mid_term + final_term) >= 90)
    {
        cout<<"\n\n\tYour Grade Is A+ ";
    }
    else if((quiz + mid_term + final_term) >= 85)
    {
        cout<<"\n\n\tYour Grade Is A ";
    }
    else if((quiz + mid_term + final_term) >= 75)
    {
        cout<<"\n\n\tYour Grade Is B+ ";
    }
    else if((quiz + mid_term + final_term) >= 70)
    {
        cout<<"\n\n\tYour Grade Is B ";
    }
    else if((quiz + mid_term + final_term) >= 60)
    {
        cout<<"\n\n\tYour Grade Is C ";
    }
    else if((quiz + mid_term + final_term) >= 50)
    {
        cout<<"\n\n\tYour Grade Is D ";
    }
    else
    {
        cout<<"\n\n\tYou Are FAIL ";
    }
    getch();
    return 0;
}
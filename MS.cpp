

#include <iostream>
#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include <string>
#include <cmath>
#include <cstring>
using namespace std;
float area_r(float width , float length)
{
    float area;
    area = width*length;
    cout<<area;
}
float area_c(float radius)
{
    float PIE = 3.1415;
    float area;
    area = PIE*pow(radius,2);
    cout<<area;
}
float area_t(float base , float height)
{
    float area;
    area = 0.5*base*height;
    cout<<area;
}
int main()
{
    float a,b;
    char input;
    cout<<"Enter the shape of which you want to find area of : ";
    cout<<"\n 1.Rectangle :\n 2.Circle :\n 3.Triangle :\n\n";
    cin>>input;
    switch (input)
    {
        case  1 : cout<<"the are of "<<area_r(a,b) ; break;
        case  2  : cout<<area_c(a) ; break;
        case  3  : cout<<area_t(a,b) ; break; 
    }
    getch();
    return 0;
}

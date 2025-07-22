

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
    int i,j,k,l,m,n;
    for(i=1;i<=5;i++)
    {
        for(j=4;j>=i;j--)
        {
            cout<<" ";
        }
        for(k=1;k<=(i*2)-1;k++)
        {
            cout<<"*";
        }
        cout<<"\n";
    }
    for(l=4;l>=1;l--)
    {
        for(m=5;m>l;m--)
        {
            cout<<" ";
        }
        for(n=(l*2)-1;n>=1;n--)
        {
            cout<<"*";
        }
        cout<<"\n";
    }
    getch();
    return 0;
}
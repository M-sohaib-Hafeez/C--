//lab 6 code 3

#include<iostream>
#include<conio.h>
#include<cmath>
using namespace std;
class circle
{
    public:
        double radius;
        double compute_area()
        {
            double PIE = 3.14;
            return PIE * pow(radius,2);
        }
};
int main()
{
    circle obj;
    cout<<"Enter the Radius Of Your Circle : ";
    cin>>obj.radius;
    cout<<"\nThe Value Of Radius Is : "<<obj.radius;
    cout<<"\nThe Calculated Area Of Your Circle Is : "<<obj.compute_area();
    getch();
    return 0;
}
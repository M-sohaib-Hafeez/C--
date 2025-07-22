//Lab 6 code 2

#include<iostream>
#include<conio.h>
using namespace std;
class point
{
    private:
        int x,y;
    public:
        point(int x1,int y1)
        {
            x = x1;
            y = y1;
            cout<<"\nThe Value of X is : "<<x;
            cout<<"\nThe Value of Y is : "<<y;
        }
        ~point()
        {
            cout<<"\nNow I Am Become Death The Destroyer Of Worlds ";
        }
};
int main()
{
    int a,n;
    cout<<"Enter the Number Of Times You Want To Run The Programs : ";
    cin>>n;
    for(a=0;a<n;a++)
    {
        point p(20,30);
        cout<<"\nThe Counter Value Is : "<<a+1;   
    }
    getch();
    return 0;
}
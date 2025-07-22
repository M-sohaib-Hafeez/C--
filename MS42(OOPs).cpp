

#include<iostream>
#include<string>
#include<conio.h>
using namespace std;
class test
{
    private:
       int arr[5];
    public:
    void show();
    void take();
    int max();
    int min();
};
void test :: take()
{
    cout<<"\nEnter the NUmbers in Array ";
    for(int i =0;i<5;i++)
    {
        cout<<"\nEnter Array ["<<i<<"] ";
        cin>>arr[i];
    }
}
void test :: show()
{
    cout<<"\nThe values of Arrays are \n";
    for(int i = 0 ; i<5 ; i++)
    {
        cout<<arr[i]<<"\n";
    }
}
int test :: max()
{
    int m=arr[0];
    for (int i = 0; i < 5; i++)
    {
        if(m<arr[i])
        {
            m=arr[i];
        }
    }
    return m;
}
int test :: min()
{
    int m = arr[0];
    for (int i = 0; i < 5; i++)
    {
        if(m>arr[i])
        {
            m = arr[i];
        }
    }
    return m;
}
int main ()
{
    test a;
    a.take();
    cout<<"\nYou entered the following values \n";
    a.show();
    cout<<"\nThe Minimum value is "<<a.min();
    cout<<"\nThe Maximum value is "<<a.max();
    getch();
    return 0;
} 
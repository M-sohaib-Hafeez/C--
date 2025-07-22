//lab 6 code 1

#include<iostream>
#include<conio.h>
using namespace std;
class construct
{
    public:
        int salary = 0 , benefits = 0;
        /*construct()
        {
            salary = 2000;
            cout<<"I am constructer";
        }*/
};
int main()
{
    int total_salary;
    construct hola;
    cout<<"Enter the salary :";
    cin>>hola.salary;
    cout<<"Enter the Benefits : ";
    cin>>hola.benefits;
    total_salary = hola.salary + hola.benefits;
    cout<<"\n\nThe Total Salary is : "<<total_salary;
    getch();
    return 0;
}
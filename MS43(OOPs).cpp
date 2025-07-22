

#include <iostream>
#include<string>
using namespace std;
class car
{
  private:
    double price = 200000;
  public:
    string name;
    string model;
    double hp;
    // Default Constructor
    car()
    {
       name = "Unknown";
       model = "Unknown";
       hp = 0.0;
      cout  << "hello i am constructor of car"<< endl;
    }
  ~car ()
  {
    cout << "hello i am destructor of car"<< endl;
  }
    // parametric constructor
    car(string name, string model,double hp,double price)
    {
      this->name = name;
      this->model = model;
      this->hp = hp;
      this->price = price;
    }
    //custom copy constructor
    car(car &obj)
    {
      cout << "i am copy constuctor of car";
      this->name = name;
      this->model = model;
      this->hp = hp;
    }
    //setter
    void setprice(double p)
    {
      price = p;
    }
    //getter
    double getprice()
    {
      return price;
    }
    };
class bike : public car
{
  private : 
    
  public:
    int average;
    bike ()
    {
      cout<< "hello i am constructor of bike"<< endl;
    };
    bike(string name, string model,double hp,double price, float average) : car( name, model, hp, price)
    {
      this->average = average;
    }
    ~bike ()
    {
      cout << "hello i am destructor of bike"<< endl;
    }
};
class shape
{
  public:
    virtual void draw() =0;//pure cirtual function(it make class automatically abstraact class)
    int r = 4;
};
int main ()
{
  car c1;
  c1.name = "civic";
  c1.setprice(250000);
  cout<<c1.getprice()<<endl;
  cout<<c1.name<<endl;
  car c2(c1);
  cout<<c2.getprice()<<endl;
  car c3;
  c3.setprice(300000);
  cout<<c3.getprice()<<endl;
  car c4("Honda","clo-03",7400,70000);
  cout << c4.name<<endl;
  cout << c4.model<<endl;
  cout << c4.hp<<endl;
  cout<<c4.getprice()<<endl;
  cout<<"hello world"<< endl;
  bike b1;
  b1.name="unique";
  b1.setprice(20000);
  cout << b1.getprice()<<endl;
  cout << b1.name<<endl;
  bike b2("super star","h-232",6000,25000,5.9);
  cout << b2.name<<endl;
  cout << b2.model<<endl;
  cout << b2.hp<<endl;
  cout<<b2.getprice()<<endl;
  cout<<b2.average<<endl;
  //shape s1;
}
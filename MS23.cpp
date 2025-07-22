


#include<iostream>
#include<stdio.h>
#include<conio.h>
#include<cmath>
#include<string>
#include<cstring>
using namespace std;
struct BankAccount 
{
    string accNum;
    string name;
    double balance;
};

void createAccount(BankAccount &account)
{
    cout << "Enter account number: ";
    cin >> account.accNum;
    cout << "Enter name: ";
   cin.ignore(); // Clear the input buffer
    getline(std::cin, account.name);
   cout << "Enter initial deposit: ";
   cin >> account.balance;
   cout << "Account created successfully!\n";
}

void depositMoney(BankAccount &account) 
{
    double amount;
    cout << "Enter amount to deposit: ";
   cin >> amount;
    account.balance += amount;
   cout << "Deposited successfully. New balance: rupees" << account.balance << "\n";
}

void checkBalance(const BankAccount &account) 
{
    cout << "Account Number: " << account.accNum << "\n";
   cout << "Name: " << account.name << "\n";
    cout << "Current Balance: rupees" << account.balance << "\n";
}

int main() 
{
    BankAccount userAccount;
    int choice;

    do {
        cout << "\nBanking System Menu:\n";
        cout << "1. Create Account\n";
        cout << "2. Deposit Money\n";
        cout << "3. Check Balance\n";
        cout << "4. Exit\n";
        cout << "Enter your choice: ";
       cin >> choice;

       if (choice == 1) {
            createAccount(userAccount);
        } else if (choice == 2) {
            depositMoney(userAccount);
        } else if (choice == 3) {
            checkBalance(userAccount);
        } else if (choice == 4) {
            cout << "Exiting the system. Goodbye!\n";
        } else {
            cout << "Invalid option. Please try again.\n";
        }
    } while (choice != 4);
    getch();
    return 0;
}
 
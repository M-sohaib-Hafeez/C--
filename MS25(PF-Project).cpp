


#include <iostream>
#include <cstring>
#include <stdio.h>
#include <conio.h>
#include <string>
#include <cmath>
#include <fstream>
using namespace std;

struct Task 
{
    string _task_;
    bool mark;
};

Task tasks[200];
int taskno = 0;
void addtask();
void edittask();
void Deletetask();
void markcomplete();
void displaytask();
void savetask();
void loadtask();

int main() 
{
    int choice;
    loadtask();
    do
    {
        cout<<endl<<"To-Do List Application\n";
        displaytask();
        cout<<"1. Add Task\n";
        cout<<"2. edit Task\n";
        cout<<"3. Delete Task\n";
        cout<<"4. Mark as Complete\n";
        cout<<"5. Exit\n";
        cout<<"Enter your choice: ";
        cin>>choice;

        switch(choice)
        {
            case 1:
                addtask();
                break;
            case 2:
                edittask();
                break;
            case 3:
                Deletetask();
                break;
            case 4:
                markcomplete();
                break;
            case 5:
                cout<<"Exitng From The Application......BYE \n";
                break;
            default:
                cout<<"Invalid choice.\n";
        }
    }
    while(choice != 5);
    getch();
    return 0;
}

void addtask() 
{
    cout<<"Enter task : ";
    getline(cin,tasks[taskno]._task_);//first line is skipped by compiler IDK why
    getline(cin,tasks[taskno]._task_);
    tasks[taskno].mark=false;
    taskno++;
    savetask();

    cout << "Task added successfully!\n";
}

void displaytask() 
{
    if (taskno == 0) 
    {
        cout<<"No tasks found.\n";
    }
    else
    {
        cout<<"Tasks are:"<<endl;
        for(int i = 0; i < taskno; i++) 
        {
            cout<<i + 1 << ". ";
            if(tasks[i].mark)
            {
                cout<<"[Task Completed] ";
            }
            else
            {
                cout<<"[Still On Pending ] ";
            }
            cout<<tasks[i]._task_<<endl;
        }
    }
    cout<<"\t\t/  /  /  /  /  /  /"<<endl;
}
void edittask()
{
    int index;
    cout<<"Enter task number to update: ";
    cin>>index;
    if(index >= 1 && index <= taskno) 
    {
        cout<<"Enter new Task: ";
        getline(cin,tasks[index - 1]._task_);//first line is skipped by compiler IDK why
        getline(cin,tasks[index - 1]._task_);
        cout<<"Task updated successfully"<<endl;
        savetask();
    }
    else
    {
        cout<<"Invalid task number.\n";
    }
}

void Deletetask() 
{
    int index;
    cout<<"Enter task number to delete: ";
    cin>>index;
    if (index>=1 && index <= taskno) 
    {
        for (int i=index-1;i<taskno-1;i++) 
        {
            tasks[i]=tasks[i+1];
        }
        taskno--;
        cout<<"Task deleted successfully!\n";
        savetask();
    }
    else
    {
        cout<<"Invalid task number.\n";
    }
}
void markcomplete()
{
    int index;
    cout<<"Enter task number to mark as complete: ";
    cin>>index;
    if(index >= 1 && index <= taskno)
    {
        tasks[index - 1].mark = true;
        cout<<"Task mark as complete!"<<endl;
        savetask();
    }
    else
    {
        cout<<"Invalid task number."<<endl;
    }
}

void savetask()
{
    ofstream Ofile("PF_Group_Project.txt");
    if(Ofile.is_open())
    {
        for(int i = 0; i < taskno; i++) 
        {
            Ofile<<tasks[i]._task_<<endl;
            Ofile<<tasks[i].mark<<endl;
        }
        Ofile.close();
    }
    else
    {
        cout<<"Error saving tasks.\n";
    }
}

void loadtask() 
{
    ifstream IFile("PF_Group_Project.txt");
    if(IFile.is_open()) 
    {
        string line;
        taskno = 0;
        while(getline(IFile, line) && taskno < 200) 
        {
            tasks[taskno]._task_=line;
            getline(IFile, line);
            tasks[taskno].mark=(line == "1");
            taskno++;
        }
        IFile.close();
    }
}
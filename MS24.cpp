

#include <iostream>
#include <vector>
#include <fstream>
#include <string>
#include <cstring>
#include <conio.h>
#include <stdio.h>
#include <cmath>
const int MAX_TASKS = 200;
using namespace std;

struct Task 
{
    string description;
    bool completed;
};

void printTasks(const Task tasks[], int taskCount)
{
    cout << "To-Do List:\n";
    for (size_t i = 0; i < taskCount; ++i) 
    {
        cout << i + 1 << ". " << tasks[i].description
                  << " [" << (tasks[i].completed ? "Completed" : "Pending") << "]\n\n";
    }
}

void addTask(Task tasks[], int& taskCount) 
{
    if(taskCount >= MAX_TASKS)
    {
        cout<<" The Task List Is Full, You can't Add another task \n\n";
        return;
    }
    cout << "Enter task description: ";
    getline(cin, tasks [taskCount].description);
    tasks[taskCount].completed = false;
    taskCount++;
    cout << "Task Added Succesfully\n\n";
}

void editTask(Task tasks[], int& taskCount) 
{
    printTasks(tasks, taskCount);
    int index;
    cout << "Enter the task number that you want to edit: ";
    cin >> index;
    cin.ignore(); // Ignore the newline character
    if (index > 0 && index <= taskCount) 
    {
        cout << "Enter new description: ";
        getline(cin, tasks[index - 1].description);
        cout << "Task updated.\n\n";
    } 
    else 
    {
        cout << "Invalid task number.\n\n";
    }
}

void deleteTask(Task tasks[], int& taskCount) 
{
    printTasks(tasks, taskCount);
    int index;
    cout << "Enter the task number to delete: ";
    cin >> index;
    cin.ignore(); // Ignore the newline character
    if (index > 0 && index <= taskCount) 
    {
        for(int i = index-1; i< taskCount-1; ++i)
        {
            tasks[i] = tasks[i + 1];
        }
        taskCount--;
        cout << "Task deleted.\n\n";
    } 
    else 
    {
        cout << "Invalid task number.\n\n";
    }
}

void markTaskComplete(Task tasks[], int& taskCount) 
{
    printTasks(tasks, taskCount);
    int index;
    cout << "Enter the task number to mark as complete: ";
    cin >> index;
    cin.ignore(); // Ignore the newline character
    if (index > 0 && index <= taskCount) 
    {
        tasks[index - 1].completed = true;
        cout << "Task marked as complete.\n\n";
    } 
    else 
    {
        cout << "Invalid task number.\n\n";
    }
}

void saveTasks(const Task tasks[], int taskCount, const string & filename) 
{
    ofstream file(filename);
    for (int i = 0; i< taskCount; ++i) 
    {
        file << tasks[i].description << "\n" << tasks[i].completed << "\n";
    }
    cout << "Tasks saved to file.\n\n";
}

void loadTasks( Task tasks[], int taskCount, const string & filename) 
{
    ifstream file(filename);
    taskCount = 0;
    while (taskCount < MAX_TASKS && getline(file, tasks[taskCount].description))
    {
        file >> tasks[taskCount].completed;
        file.ignore(); // Ignore the newline character
        taskCount ++;
    }
    cout << "Tasks loaded from file.\n\n";
}

int main() 
{
    Task tasks[MAX_TASKS];
    int taskCount = 0;
    string filename = "PF Project File.txt";

    loadTasks(tasks, taskCount, filename);

    while (true) 
    {
        cout << "\n1. View Tasks\n2. Add Task\n3. Edit Task\n4. Delete Task\n5. Mark Task as Complete\n6. Save and Exit\n";
        int choice;
        cin >> choice;
        cin.ignore(); // Ignore the newline character

        switch (choice) 
        {
            case 1:
                printTasks(tasks, taskCount);
                break;
            case 2:
                addTask(tasks, taskCount);
                break;
            case 3:
                editTask(tasks, taskCount);
                break;
            case 4:
                deleteTask(tasks, taskCount);
                break;
            case 5:
                markTaskComplete(tasks, taskCount);
                break;
            case 6:
                saveTasks(tasks, taskCount, filename);
                return 0;
            default:
                cout << "Invalid choice. Please enter again.\n\n";
        }
    }

    getch();
    return 0;
}
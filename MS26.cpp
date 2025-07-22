

#include <iostream>
#include <string>
#include <conio.h>
using namespace std;

struct Question {
    string questionText;
    string options[4];
    char correctOption; 
};

int conductExam(Question questions[], int count) {
    char answer;
    int score = 0;

    for (int i = 0; i < count; i++) {
        cout << "\nQ" << (i + 1) << ": " << questions[i].questionText << endl;
        cout << "a. " << questions[i].options[0] << endl;
        cout << "b. " << questions[i].options[1] << endl;
        cout << "c. " << questions[i].options[2] << endl;
        cout << "d. " << questions[i].options[3] << endl;
        cout << "Enter your answer (a/b/c/d): ";
        cin >> answer;

        if (answer == questions[i].correctOption) {
            score++;
        }
    }
    return score;
}

int main() {
    Question questions[] = {
        {
            "What is the powerhouse of the cell?", 
            {"Nucleus", "Mitochondria", "Ribosome", "Chloroplast"}, 
            'b'
        },
        {
            "Which gas is most abundant in Earth's atmosphere?", 
            {"Oxygen", "Nitrogen", "Carbon dioxide", "Argon"}, 
            'b'
        },
        {
            "What is the chemical formula of water?", 
            {"H2O", "CO2", "O2", "H2O2"}, 
            'a'
        },
        {
            "What is the SI unit of force?", 
            {"Newton", "Pascal", "Joule", "Watt"}, 
            'a'
        },
        {
            "Which planet is known as the Red Planet?", 
            {"Earth", "Mars", "Venus", "Jupiter"}, 
            'b'
        },
        {
            "What is the speed of light in a vacuum?", 
            {"3 × 10^8 m/s", "3 × 10^6 m/s", "3 × 10^4 m/s", "3 × 10^2 m/s"}, 
            'a'
        },
        {
            "Which element is represented by the symbol 'Na'?", 
            {"Nitrogen", "Sodium", "Neon", "Nickel"}, 
            'b'
        },
        {
            "What is the smallest unit of life?", 
            {"Atom", "Molecule", "Cell", "Organ"}, 
            'c'
        },
        {
            "What is the chemical symbol for gold?", 
            {"Au", "Ag", "Pb", "Hg"}, 
            'a'
        },
        {
            "Who formulated the laws of motion?", 
            {"Albert Einstein", "Isaac Newton", "Galileo Galilei", "Nikola Tesla"}, 
            'b'
        }
    };

    int questionCount = sizeof(questions) / sizeof(questions[0]);

    cout << "Welcome to the Online Examination System!" << endl;

    string name;
    cout << "Enter your name: ";
    cin.ignore();
    getline(cin, name);

    int score = conductExam(questions, questionCount);

    cout << "\nExam Completed!" << endl;
    cout << "Your score: " << score << "/" << questionCount << endl;
    getch();
    return 0;
}
#include <iostream>
#include "animal.h"
#include "childAnimal.h"

using namespace std;

int main()
{

    animal *Zoo[10];
    animal *animalPtr;
    string name;
    COLOR c;
    string owner;

    int n = 0;

    int id = 0;

    while(id != -1){
        cout << "Select the animal to send to Zoo: " << endl;
        cout << "(1) Dog (2) Cat (3) Lion (4) Move all animals (-1) Quit" << endl;
        cin >> id;

        switch(id){
        case 1:
            cout << "Enter name and owner" << endl;
            cin >> name;
            cin >> owner;
            animalPtr = new Dog(name, White, owner);
            animalPtr->move();
            animalPtr->speak();
            Zoo[n++] = animalPtr;
            break;
        case 2:
            cin >> name;
            cin >> owner;
            animalPtr = new Cat(name, Black, owner);
            animalPtr->move();
            animalPtr->speak();
            Zoo[n++] = animalPtr;
            break;
        case 3:
            cout << "Enter name" << endl;
            cin >> name;
            animalPtr = new Lion(name, Blue);
            animalPtr->move();
            animalPtr->speak();
            Zoo[n++] = animalPtr;
            break;
        case 4:
            for(int i=0; i<n; i++){
                animalPtr = Zoo[i];
                animalPtr->move();
                animalPtr->speak();
                animalPtr->eat();
            }
        default:
            break;
        }

    }

    return 0;
}

#include "animal.h"
#include <string>
#include <iostream>

using namespace std;


animal::animal(): _name("Unknown")
{
    //cout << "constructing Animal object "<< _name << endl ;
}

animal::animal(string n, COLOR c):_name(n), _color(c){
    string clr[] = {"Green", "Blue", "White", "Black", "Brown"};
    //cout << "Animal name " << _name << " with color " << clr[_color] << endl;
}

animal::~animal()
{
    cout << "destructing Animal object "<< _name << endl ;
}

void animal::speak() const{
    cout << "Speak" << endl;
}

void animal::move() const{
    cout << "Moving... " << endl;
}

void animal::eat() const{
    cout << "Eating " << endl;
}

Mammal::Mammal(string n, COLOR c):animal(n, c){
    //cout << "Mammal object created " << endl;
}

void Mammal::eat() const{
    cout << "Mammal eat" << endl;
}

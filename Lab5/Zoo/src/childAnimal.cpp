#include "childAnimal.h"

Cat::Cat(string n, COLOR c, string owner): Mammal(n, c), _owner(owner)
{
    cout << "Owner is " << _owner << endl;
    //cout << "Cat object created" << endl;

}

Cat::~Cat()
{
    cout << "Cat object destroyed" << endl;
}

void Cat::speak() const{
    cout << "Cat Meow" << endl;
}

void Cat::move() const{
    cout << "Cat Move" << endl;
}

Dog::Dog(string n, COLOR c, string owner): Mammal(n, c), _owner(owner)
{
    //cout << "Owner is " << _owner << endl;
    //cout << "Dog object created" << endl;
}

Dog::~Dog()
{
    cout << "Dog object destroyed" << endl;
}

void Dog::speak() const{
    cout << "Dog Woof" << endl;
}

void Dog::move() const{
    cout << "Dog moves" << endl;
}


Lion::Lion(string n, COLOR c): Mammal(n, c)
{
    //cout << "Lion object created" << endl;
}

Lion::~Lion()
{
    cout << "Cat object destroyed" << endl;
}

void Lion::speak() const{
    cout << "Lion Roar" << endl;
}

void Lion::move() const{
    cout << "Lion Moves" << endl;
}

void Lion::eat() const{
    cout << "Lion Eats" << endl;
}

void Cat::eat() const{
    cout << "Cat Eats" << endl;
}

void Dog::eat() const{
    cout << "Dog Eats" << endl;
}

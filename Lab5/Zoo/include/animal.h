#ifndef ANIMAL_H
#define ANIMAL_H
#include <string>
#include <iostream>

using namespace std;

class animal;

enum COLOR {Green, Blue, White, Black, Brown};

class animal
{
    public:
        animal();
        animal(string n, COLOR c);
        virtual ~animal();
        virtual void speak() const;
        virtual void move() const;
        virtual void eat() const;

    private:
        string _name;
        COLOR _color;
};

class Mammal:public animal{
    public:
        Mammal(string n, COLOR c);
        void eat() const;
};


#endif // ANIMAL_H

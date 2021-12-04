#ifndef CHILDANIMAL_H
#define CHILDANIMAL_H
#include "animal.h"
#include <string>

class Cat;


class Cat:public Mammal
{
    public:
        Cat(string n, COLOR c, string owner);
        virtual ~Cat();
        void speak() const;
        void move() const;
        void eat() const;

    private:
        string _owner;
};

class Dog:public Mammal
{
    public:
        Dog(string n, COLOR c, string owner);
        virtual ~Dog();
        void speak() const;
        void move() const;
        void eat() const;

    private:
        string _owner;
};

class Lion:public Mammal
{
    public:
        Lion(string n, COLOR c);
        virtual ~Lion();
        void speak() const;
        void move() const;
        void eat() const;

    private:
        string _owner;
};

#endif // CHILDANIMAL_H

package com.example.intelliguess
class Person(val pets: MutableList<Pet> = mutableListOf())

class Pet(owner: Person, var name: String = "") {
    init {
        owner.pets.add(this) // adds this pet to the list of its owner's pets
    }
}

fun main() {
    val person = Person()
    val pet1 = Pet(person, "BRANDON")
    val pet2 = Pet(person)
    pet2.name = "brandon"
    pet1.name = "jewel"

    println("Person has ${person.pets.size} pets.")
    person.pets.forEachIndexed { index, pet ->
        println("Pet ${index + 1}: ${pet.name}")
    }
}

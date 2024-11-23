package com.harshjoshi.aroundthecorner.apiRequest.domain

import com.harshjoshi.aroundthecorner.apiRequest.data.Person

class PersonRepository {
    fun setName(person: Person, value: String): Person{
        return person.copy(name = value)
    }
    fun setGender(person: Person, value: String): Person{
        return person.copy(gender = value)
    }
    fun setRegion(person: Person, value: String): Person{
        return person.copy(region = value)
    }
    fun setAge(person: Person, value: String): Person{
        return person.copy(age = value)
    }
}
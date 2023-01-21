package com.example.tkppl_yes_248

class Signup_model() {
    lateinit var username: String
    lateinit var password: String
    lateinit var id: String

    constructor(username: String, password: String, id: String) : this() {
        this.username = username
        this.password = password
        this.id = id
    }
}
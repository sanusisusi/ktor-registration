package com.example

import com.example.plugins.configureTemplating
import configureRouting
import io.ktor.server.application.*

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.request.*
//import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*


//import kotlinx.html.*
//import kotlinx.html.stream.*

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}


fun Application.module() {
    configureRouting()
    configureTemplating()
//    install(DefaultHeaders())
//    install(CallLogging)
    install(Routing) {
        route("/") {
            static("static") {
                resources("static")
            }
            get("/") {
                call.respondText("Welcome to the homepage!")
            }
            get("/homepage") {
                call.respondFile("static/homepage.html")
            }
            get("/registration") {
                call.respondFile("static/registration.html")
            }
            post("/register") {
                val params = call.receiveParameters()
                val firstname = params["firstname"]
                val lastname = params["lastname"]
                val username = params["username"]
                val password = params["password"]

                if (firstname.isNullOrEmpty() || lastname.isNullOrEmpty() || username.isNullOrEmpty() || password.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "All fields are required.")
                } else {
                    call.respondText("Registration successful! Welcome, $firstname $lastname")
                }
            }
            get("/login") {
                call.respondFile("static/login.html")
            }
            post("/authenticate") {
                val params = call.receiveParameters()
                val username = params["username"]
                val password = params["password"]

                if (username == "admin" && password == "admin") {
                    call.respondRedirect("/homepage")
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid username or password.")
                }
            }
            get("/logout") {
                call.respondFile("static/logout.html")
            }
        }
    }
}

private fun Any.respondFile(file: String) {

}

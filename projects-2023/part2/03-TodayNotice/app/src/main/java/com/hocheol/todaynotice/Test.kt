package com.hocheol.todaynotice

import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import kotlin.concurrent.thread

fun main() {
    thread {
        try {
            val port = 8080
            ServerSocket(port).use { server ->
                while (true) {
                    server.accept().use { socket ->
                        socket.getInputStream().bufferedReader().use { reader ->
                            PrintWriter(socket.getOutputStream()).use { printer ->
                                var input: String? = "-1"
                                while (input.isNullOrEmpty().not()) {
                                    input = reader.readLine()
                                }

                                println("READ DATA : $input")

                                printer.println("HTTP/1.1 200 OK")
                                printer.println("Content-Type: text/html\r\n")

                                printer.println("<h1>Hello World</h1>")
                                printer.println("\r\n")

                                printer.flush()
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
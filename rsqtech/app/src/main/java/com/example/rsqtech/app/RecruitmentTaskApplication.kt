package com.example.rsqtech.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.rsqtech"])
class RecruitmentTaskApplication

fun main(args: Array<String>) {
    runApplication<RecruitmentTaskApplication>(*args)
}
package com.example.rsqtech.infrastructure.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.context.support.ReloadableResourceBundleMessageSource

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean


@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, ex.bindingResult.globalError?.defaultMessage, headers, status, request)
    }

    @Bean
    fun messageSource(): MessageSource? {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    fun getValidator(): LocalValidatorFactoryBean? {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource()!!)
        return bean
    }
}
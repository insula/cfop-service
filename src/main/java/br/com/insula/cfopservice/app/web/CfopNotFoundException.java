package br.com.insula.cfopservice.app.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "CFOP n\u00e3o encontrado")
public class CfopNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
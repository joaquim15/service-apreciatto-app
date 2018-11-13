package com.nelioalves.cursomc.resources;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.SessionService;

@RestController
@RequestMapping(value = "/session")
public class SessionIdPagSeguroResource {

	// private static String email = "joaquim.moura@interaconsultoria.com.br";

	// private static String token = "DA30A072D2F845228E75F0BCC0A12518";

	String sessionId;

	private static final Gson gson = new Gson();

	private static final Logger logger = Logger.getLogger(SessionIdPagSeguroResource.class.getName());

	@GetMapping
	public ResponseEntity<String> getSession() {
		try {

			final AccountCredentials accountCredentials = PagSeguroConfig.getAccountCredentials();

			sessionId = SessionService.createSession(accountCredentials);

			System.out.println("Session ID: " + sessionId);
		} catch (PagSeguroServiceException e) {
			System.err.println(e.getMessage());
		}
		return ResponseEntity.ok().body(gson.toJson(sessionId));
	}

}

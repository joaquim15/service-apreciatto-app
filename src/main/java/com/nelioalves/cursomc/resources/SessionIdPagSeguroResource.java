package com.nelioalves.cursomc.resources;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.session.CreatedSession;

@RestController
@RequestMapping(value = "/session")
public class SessionIdPagSeguroResource {

	private static String email = "joaquim.moura@interaconsultoria.com.br";

	private static String token = "DA30A072D2F845228E75F0BCC0A12518";
	
	private static final Logger logger = Logger.getLogger(SessionIdPagSeguroResource.class.getName());

	@GetMapping
	public ResponseEntity<CreatedSession> getSession() {
		CreatedSession createdSessionApplication = null;
		try {

			final PagSeguro pagSeguro = PagSeguro.instance(Credential.sellerCredential(getEmail(), getToken()),
					PagSeguroEnv.SANDBOX);
			
			// Cenario: Criar sessao
			// Dado que esteja autenticado na api do pagseguro
			// Quando crio uma sessao
			// Entao e retornado o codigo da sessao
			
			if (pagSeguro != null) {
				createdSessionApplication = pagSeguro.sessions().create();
				logger.info("Id sessão: " + createdSessionApplication.getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(createdSessionApplication);
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		SessionIdPagSeguroResource.email = email;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		SessionIdPagSeguroResource.token = token;
	}

}

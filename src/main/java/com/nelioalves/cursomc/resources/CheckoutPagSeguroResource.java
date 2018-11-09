package com.nelioalves.cursomc.resources;

import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.dto.PaymentDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;

import br.com.uol.pagseguro.api.direct.preapproval.Transaction;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.direct.checkout.CreditCardCheckout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.PaymentMode;

@RestController
@RequestMapping(value = "/checkout-pag-seguro")
public class CheckoutPagSeguroResource {

	private static final Logger logger = Logger.getLogger(CheckoutPagSeguroResource.class.getName());

	// @Autowired
	// private CheckoutService service;
	private Cliente cliente = null;
	private Endereco endereco;
	private Transaction transaction = null;
	private Gson gson = new GsonBuilder().create();

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Transaction> payment(@Valid @RequestBody String obj) throws Exception {

		logger.info("line - 1: " + obj);
		PaymentDTO dadosPayment = this.gson.fromJson(obj, PaymentDTO.class);
		logger.info("line - 2 " + dadosPayment);

		CreditCardCheckout request = new CreditCardCheckout();

		request.setPaymentMode(PaymentMode.GATEWAY);
		request.setReceiverEmail("joaquim.moura@interaconsultoria.com.br");
		request.setCurrency(Currency.BRL);
		request.setReference("REF1234");

		// dados do comprador

		cliente = new Cliente();

		Optional<Cliente> objCli = clienteRepository.findById(dadosPayment.getPedido().getCliente().getId());
		
		Sender send = new Sender();

		return ResponseEntity.ok().body(transaction);

	}

}

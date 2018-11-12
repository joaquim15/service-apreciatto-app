package com.nelioalves.cursomc.resources;

import java.math.BigDecimal;
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
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.SenderDocument;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.domain.direct.checkout.OnlineDebitCheckout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.DocumentType;
import br.com.uol.pagseguro.enums.PaymentMode;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.TransactionService;

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

		final OnlineDebitCheckout request = new OnlineDebitCheckout();

		request.setPaymentMode(PaymentMode.DEFAULT);

		request.setReceiverEmail("backoffice@lojamodelo.com.br");

		request.setCurrency(Currency.BRL);

		request.setReference("REF1234");

		request.setSender(new Sender("João Comprador", //
				"comprador@uol.com.br", //
				new Phone("11", "56273440"), //
				new SenderDocument(DocumentType.CPF, "000.000.001-91")));

		request.setShippingAddress(new Address("BRA", //
				"SP", //
				"Sao Paulo", //
				"Jardim Paulistano", //
				"01452002", //
				"Av. Brig. Faria Lima", //
				"1384", //
				"5º andar"));

		request.setShippingType(ShippingType.SEDEX);

		request.setShippingCost(new BigDecimal("5.00"));

		request.addItem(new Item("1", //
				"Notebook Prata", //
				Integer.valueOf(1), //
				new BigDecimal("500.00")));

		request.addItem(new Item("2", //
				"Notebook Rosa", //
				Integer.valueOf(1), //
				new BigDecimal("500.00")));

		request.setBankName("BRADESCO");

		try {

			/*
			 * If you use application credential you don't need to set
			 * request.setReceiverEmail(); Set your account credentials on
			 * src/pagseguro-config.properties You can create an payment using an
			 * application credential and set an authorizationCode ApplicationCredentials
			 * applicationCredentials = PagSeguroConfig.getApplicationCredentials();
			 * applicationCredentials.setAuthorizationCode("your_authorizationCode");
			 *
			 */

			final AccountCredentials accountCredentials = PagSeguroConfig.getAccountCredentials();


			if (transaction != null) {
				System.out.println("Transaction Code - Default Mode: " + transaction.getCode());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}

package com.nelioalves.cursomc.resources;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Document;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.SenderDocument;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.domain.direct.Holder;
import br.com.uol.pagseguro.domain.direct.Installment;
import br.com.uol.pagseguro.domain.direct.checkout.CreditCardCheckout;
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

	public static void main(String[] args) {
		// default mode
		createTransactionUsingDefaultMode();

		// gateway mode
		createTransactionUsingGatewayMode();
	}

	public static void createTransactionUsingDefaultMode() {
		final CreditCardCheckout request = new CreditCardCheckout();

		request.setPaymentMode(PaymentMode.DEFAULT);

		request.setReceiverEmail("backoffice@lojamodelo.com.br");

		request.setCurrency(Currency.BRL);

		request.setNotificationURL("http://www.meusite.com.br/notification");

		request.setReference("REF1234");

		request.setSender(new Sender("João Comprador", //
				"c54794630389511462720@sandbox.pagseguro.com.br", //
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
				new BigDecimal("2500.00")));

		request.addItem(new Item("2", //
				"Notebook Rosa", //
				Integer.valueOf(1), //
				new BigDecimal("2500.00")));

		request.setCreditCardToken("286ff355747941f58b2093608cd6b7a2");

		request.setInstallment(new Installment(1, new BigDecimal("5005.00")));

		request.setHolder(new Holder("João Comprador", //
				new Phone("11", "56273440"), //
				new Document(DocumentType.CPF, "000.000.001-91"), //
				"07/05/1981"));

		request.setBillingAddress(new Address("BRA", //
				"SP", //
				"Sao Paulo", //
				"Jardim Paulistano", //
				"01452002", //
				"Av. Brig. Faria Lima", //
				"1384", //
				"5º andar"));

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

			final Transaction transaction = TransactionService.createTransaction(accountCredentials, //
					request);

			if (transaction != null) {
				System.out.println("Transaction Code - Default Mode: " + transaction.getCode());
			}
		} catch (PagSeguroServiceException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void createTransactionUsingGatewayMode() {
		final CreditCardCheckout request = new CreditCardCheckout();

		request.setPaymentMode(PaymentMode.GATEWAY);

		request.setReceiverEmail("backoffice@lojamodelo.com.br");

		request.setCurrency(Currency.BRL);

		request.setNotificationURL("http://www.meusite.com.br/notification");

		request.setReference("REF1234");

		request.setSender(new Sender("João Comprador", "comprador@uol.com.br"));

		request.setSenderHash("0db5776271490042a3b89f7f54d7e54244cf74d469695aa67c49e11c8a56c2c4");

		request.addItem(new Item("1", "Notebook Prata", Integer.valueOf(1), new BigDecimal("2500.00")));
		request.addItem(new Item("2", "Notebook Rosa", Integer.valueOf(1), new BigDecimal("2500.00")));

		request.setCreditCardToken("286ff355747941f58b2093608cd6b7a2");

		request.setInstallment(new Installment(1, new BigDecimal("5000.00")));

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

			final Transaction transaction = TransactionService.createTransaction(accountCredentials, //
					request);

			if (transaction != null) {
				System.out.println("Transaction Code - Gateway Mode: " + transaction.getCode());
			}
		} catch (PagSeguroServiceException e) {
			System.err.println(e.getMessage());
		}
	}
}

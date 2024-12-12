package Excepciones;

import Entidades.Cuenta;

public class ClienteExcedeCantCuentas extends Exception {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		String MensajeDeError;
		
		
		public ClienteExcedeCantCuentas(String mensajeError) {
			super(mensajeError);
			MensajeDeError = mensajeError;
		}

		public String getMensajeError() {
			return MensajeDeError;
		}

		public String mensajePersonalizado(Cuenta cuenta) {
			return "La cuenta con DNI " + cuenta.getIdCliente() + " posee ya 3 cuentas, es el tope maximo permitido";
		}

		@Override
		public String toString() {
			 return "ClienteExcedeCantCuentas [MensajeDeError=" + getMessage() + "]";
		}
		
		
}

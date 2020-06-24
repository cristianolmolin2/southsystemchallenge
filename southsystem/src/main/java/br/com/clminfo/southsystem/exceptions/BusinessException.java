package br.com.clminfo.southsystem.exceptions;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 2048930134399061092L;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(final String message) {
		super(message);
	}
	
	public BusinessException(final Throwable cause) {
		super(cause);
	}
	
	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
}

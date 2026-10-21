package br.com.miguelalves.order.exception;

public class ExternalServiceException extends RuntimeException {

    private final String code;

    public ExternalServiceException(String serviceName, Throwable cause) {
        super("Não foi possível consultar o serviço " + serviceName, cause);
        this.code = "EXTERNAL_SERVICE_UNAVAILABLE";
    }

    public String getCode() {
        return code;
    }
}

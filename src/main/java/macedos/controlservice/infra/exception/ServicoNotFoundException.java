package macedos.controlservice.infra.exception;

public class ServicoNotFoundException extends RuntimeException {
    public ServicoNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ServicoNotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

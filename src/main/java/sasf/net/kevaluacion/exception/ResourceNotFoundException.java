package sasf.net.kevaluacion.exception;

public class ResourceNotFoundException extends BusinessException {
    
    public ResourceNotFoundException(String resource, Long id) {
        super("RESOURCE_NOT_FOUND", String.format("%s with id %d not found", resource, id));
    }
    
    public ResourceNotFoundException(String resource, String field, String value) {
        super("RESOURCE_NOT_FOUND", String.format("%s with %s %s not found", resource, field, value));
    }
    
    public ResourceNotFoundException(String code, String message) {
        super(code, message);
    }
}

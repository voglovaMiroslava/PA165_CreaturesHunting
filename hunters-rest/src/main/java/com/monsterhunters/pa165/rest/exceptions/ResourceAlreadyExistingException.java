package com.monsterhunters.pa165.rest.exceptions;

/**
 * Created by babcang
 *
 * @author Babcan G
 */
//The @ResponseStatus annotation is not needed if we use GlobalExceptionController
//@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason="The resource already exists")
public class ResourceAlreadyExistingException extends RuntimeException {
}

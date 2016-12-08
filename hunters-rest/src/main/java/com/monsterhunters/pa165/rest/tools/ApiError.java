package com.monsterhunters.pa165.rest.tools;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by babcang
 *
 * @author Babcan G
 */

@XmlRootElement
public class ApiError {
    private List<String> errors;

    public ApiError() {
    }

    public ApiError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

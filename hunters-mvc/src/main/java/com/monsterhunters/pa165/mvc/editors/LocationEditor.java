package com.monsterhunters.pa165.mvc.editors;

import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.facade.LocationFacade;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author Miroslava Voglova
 */
public class LocationEditor extends PropertyEditorSupport {
    
    private LocationFacade locationFacade;
    
    
    public LocationEditor(LocationFacade locationFacade){
        this.locationFacade = locationFacade;
    }
    
    public void setAsText(String text) {
        Long id = Long.parseLong(text);
        LocationDTO location = locationFacade.getLocationById(id);
        setValue(location);
    }

    public String getAsText() {
       LocationDTO location = (LocationDTO) this.getValue();
        if (location != null)
            return location.getId().toString();
        return null;
    }    
}

package de.test.backend.entities.ressources.comparators;

import de.test.backend.entities.ressources.ResourceLog;

import java.util.Comparator;
//TODO remove not used
public class LogComparator implements Comparator<ResourceLog> {
    @Override
    public int compare(ResourceLog r1, ResourceLog r2) {

        if(r1.getResourceId().equals(r2.getResourceId())){
            if(r1.getCreated().after(r2.getCreated()))
                return 1;
            if(r1.getCreated().before(r2.getCreated()))
                return -1;
            return 0;
        }else
            return r1.getResourceId().compareTo(r2.getResourceId());
    }
}

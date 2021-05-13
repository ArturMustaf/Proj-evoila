package de.test.backend.entities.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@MappedSuperclass
/**
 * Basis Entity to save with created and lastModifiedDate
 * usefull for protocolling entries like RessourceLog
 */
public abstract class BaseTimeEntity extends BaseEntity {
    @Setter
    @CreatedDate
    Date created;
    @LastModifiedDate
    Date date;
    // TODO Add again throws error currently since no authorization exisits
    /**
     * Possible field for lastEditedUser to save who created / modified entry
     */
//    @LastModifiedBy
//    @ManyToOne
//    Agent agent;
}

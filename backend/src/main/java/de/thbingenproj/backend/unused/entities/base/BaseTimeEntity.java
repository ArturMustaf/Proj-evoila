package de.thbingenproj.backend.unused.entities.base;

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
}

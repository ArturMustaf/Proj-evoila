package de.thbingenproj.backend.entities.prices;

import de.thbingenproj.backend.entities.base.BaseTimeEntity;
import de.thbingenproj.backend.entities.ressources.ResourceLog;
import lombok.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Indexed
/**
 * Database entity of projects
 *
 */
public class Project extends BaseTimeEntity {
    /**
     * Basic project name
     */
    @Field
    String name;
    /**
     * Description of the project
     */
    @Field
    @Column(length = 4000)
    String description;
    /**
     * Logs belonging to the project
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "project")
    List<ResourceLog> logs;
    /**
     * @Deprecated List of the bills
     */
    @IndexedEmbedded
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Bill> bills;
    /**
     * List of ProjectEvents => not implemented yet
     * idea: Project.created / Project.stopped / Project.renamed
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProjectEvent> events;

    /**
     * Only use for non persistent changes / not send logs => otherwise use addLogs Function removing of logs is not supported
     *
     * @param logs
     */
    public void setLogs(List<ResourceLog> logs) {
        this.logs = logs;
    }

    /**
     * Adds a log to the currently exisiting logs.
     *
     * @param log
     */
    public void addLog(ResourceLog log) {
        if (logs == null) {
            logs = new ArrayList<>();
        }
        log.setProject(this);
        this.logs.add(log);
    }


}

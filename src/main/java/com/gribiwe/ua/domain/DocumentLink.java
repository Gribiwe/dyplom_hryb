package com.gribiwe.ua.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Document.
 */
@Entity
@Table(name = "document_link")
public class DocumentLink extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "file_name")
    private Long entered;

    @Column(name = "employee_id")
    private Long author;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Long getAuthor() {
        return author;
    }


    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getEntered() {
        return entered;
    }

    public void setEntered(Long entered) {
        this.entered = entered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentLink)) {
            return false;
        }
        return id != null && id.equals(((DocumentLink) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentLink{" +
                "id=" + id +
                ", documentId=" + documentId +
                ", entered=" + entered +
                ", author=" + author +
                '}';
    }
}

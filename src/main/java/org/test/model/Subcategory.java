package org.test.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static org.test.utils.ApplicationStrings.*;

@Entity
@Table(name = SUBCATEGORY)
@NamedQueries({
    // NATURAL KEY
    @NamedQuery(name = SUBCATEGORY_FIND_FILTER_BY_NAME_CATEGORY,
                query = "SELECT s FROM Subcategory s " +
                        "WHERE s.name = :name " +
                        "AND s.category.id = :id"),
	// ORDER BY NAME
	@NamedQuery(name = SUBCATEGORY_FIND_ALL_ORDER_BY_NAME_ASC,
                query = "SELECT s FROM Subcategory s " +
                        "ORDER BY s.name ASC"),
    @NamedQuery(name = SUBCATEGORY_FIND_ALL_LIKE_ORDER_BY_NAME_ASC,
                query = "SELECT s FROM Subcategory s " +
                        "WHERE s.name LIKE :filter " +
                        "ORDER BY s.name ASC"),
})
public class Subcategory extends Domain {

    private static Log logger = LogFactory.getLog(Subcategory.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, columnDefinition = "BIGINT(10) UNSIGNED", unique = true, nullable = false)
    private Long id;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Size(min=1, max=100)
    @Column(name = NAME, columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    @NotNull
    @Valid
    @Embedded
    private Record record = new Record();
    public Record getRecord() {
        return record;
    }
    public void setRecord(Record record) {
        this.record = record;
    }

	// bi-directional many-to-one association to CATEGORY
    @NotNull
	@ManyToOne
	@JoinColumn(name = CATEGORY, nullable = false)
	private Category category;
    public Category getCategory() {
        return this.category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

	public Subcategory() {}

    public Subcategory(String name, Category category) {
        logger.info(CREATING + Subcategory.class);
        this.name = (name == null)? null : name.toUpperCase();
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subcategory that = (Subcategory) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", record=" + record +
                '}';
    }
}

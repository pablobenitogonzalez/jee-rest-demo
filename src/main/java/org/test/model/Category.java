package org.test.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static org.test.utils.ApplicationStrings.*;

@Entity
@Table(name = CATEGORY)
@NamedQueries({
    // NATURAL KEY
    @NamedQuery(name = CATEGORY_FIND_FILTER_BY_NAME,
                query = "SELECT c FROM Category c " +
                        "WHERE c.name = :name"),
    // REFERENTIAL INTEGRITY
    @NamedQuery(name = CATEGORY_FIND_SUBCATEGORIES_REFERENCING_THIS,
                query = "SELECT s FROM Subcategory s " +
                        "WHERE s.category.id = :id"),
	// ORDER BY NAME
    @NamedQuery(name = CATEGORY_FIND_ALL_ORDER_BY_NAME_ASC,
                query = "SELECT c FROM Category c " +
                        "ORDER BY c.name ASC"),
	@NamedQuery(name = CATEGORY_FIND_ALL_LIKE_ORDER_BY_NAME_ASC,
                query = "SELECT c FROM Category c " +
                        "WHERE c.name LIKE :filter " +
                        "ORDER BY c.name ASC"),
})
public class Category extends Domain {

    private static Log logger = LogFactory.getLog(Category.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID, columnDefinition = "BIGINT(10) UNSIGNED", unique = true, nullable = false)
	private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Size(min=1, max=100)
    @Column(name = NAME, columnDefinition = "VARCHAR(100)", unique = true, nullable = false)
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

    // bi-directional many-to-one association to SUBCATEGORY
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private Set<Subcategory> subcategories = new HashSet<Subcategory>();
    public Set<Subcategory> getSubcategories() {
        return this.subcategories;
    }
    public void setSubcategories(Set<Subcategory> subcategorias) {
        this.subcategories = subcategories;
    }
    public Subcategory addSubcategories(Subcategory subcategory) {
        this.getSubcategories().add(subcategory);
        subcategory.setCategory(this);
        return subcategory;
    }
    public Subcategory removeSubcategory(Subcategory subcategory) {
        this.getSubcategories().remove(subcategory);
        subcategory.setCategory(null);
        return subcategory;
    }

    public Category() {}

	public Category(String name) {
        logger.info(CREATING + Category.class);
		this.name = (name == null)? null : name.toUpperCase();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (name != null ? !name.equals(category.name) : category.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", record=" + record +
                '}';
    }
}

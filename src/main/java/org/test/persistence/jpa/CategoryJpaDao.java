package org.test.persistence.jpa;


import org.test.model.Category;
import org.test.persistence.interfaces.CategoryDao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.test.utils.ApplicationStrings.*;

@Jpa
public class CategoryJpaDao extends JpaDao<Category, Long> implements CategoryDao {

    @SuppressWarnings("unused")
    public CategoryJpaDao() {
    }

    public CategoryJpaDao(EntityManager em) {
        super(em);
    }

    private Query getAllOrderByName(String filter) {
        EntityManager em = this.getEntityManager();
        Query query;
        if (filter == null || "".equals(filter)) {
            query = em.createNamedQuery(CATEGORY_FIND_ALL_ORDER_BY_NAME_ASC);
        } else {
            query = em.createNamedQuery(CATEGORY_FIND_ALL_LIKE_ORDER_BY_NAME_ASC)
                            .setParameter(FILTER, "%" + filter.trim().toUpperCase() + "%");
        }
        return query;
    }

    @Override
    public Category find(Long id) {
        EntityManager em = this.getEntityManager();
        return em.find(Category.class, id);
    }

    public Boolean existsNaturalKey(String name) {
        EntityManager em = this.getEntityManager();
        return (em.createNamedQuery(CATEGORY_FIND_FILTER_BY_NAME)
                .setParameter(NAME, name.trim().toUpperCase())
                .getResultList().size() != 0);
    }

    public Boolean hasSubcategories(Long id) {
        EntityManager em = this.getEntityManager();
        return (em.createNamedQuery(CATEGORY_FIND_SUBCATEGORIES_REFERENCING_THIS)
                .setParameter(ID, id)
                .getResultList().size() != 0);
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAllOrderByName() {
        return this.getAllOrderByName(null).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAllOrderByName(String filter) {
        return this.getAllOrderByName(filter).getResultList();
    }
}
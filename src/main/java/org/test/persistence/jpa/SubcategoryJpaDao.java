package org.test.persistence.jpa;

import org.test.model.Category;
import org.test.model.Subcategory;
import org.test.persistence.interfaces.SubcategoryDao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.test.utils.ApplicationStrings.*;

@Jpa
public class SubcategoryJpaDao extends JpaDao<Subcategory, Long> implements SubcategoryDao {

    @SuppressWarnings("unused")
    public SubcategoryJpaDao() {}

    public SubcategoryJpaDao(EntityManager em) {
        super(em);
    }

    private Query getAllOrderByName(String filter) {
        EntityManager em = this.getEntityManager();
        Query query;
        if (filter == null || "".equals(filter)) {
            query = em.createNamedQuery(SUBCATEGORY_FIND_ALL_ORDER_BY_NAME_ASC);
        } else {
            query = em.createNamedQuery(SUBCATEGORY_FIND_ALL_LIKE_ORDER_BY_NAME_ASC)
                    .setParameter(FILTER, "%" + filter.trim().toUpperCase() + "%");
        }
        return query;
    }

    @Override
    public Subcategory find(Long id) {
        EntityManager em = this.getEntityManager();
        return em.find(Subcategory.class, id);
    }

    public Boolean existsNaturalKey(String name, Category category) {
        EntityManager em = this.getEntityManager();
        return (em.createNamedQuery(SUBCATEGORY_FIND_FILTER_BY_NAME_CATEGORY)
                .setParameter(NAME, name.trim().toUpperCase())
                .setParameter(ID, category.getId())
                .getResultList().size() != 0);
    }

    @SuppressWarnings("unchecked")
    public List<Subcategory> findAllOrderByName() {
        return this.getAllOrderByName(null).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Subcategory> findAllOrderByName(String filter) {
        return this.getAllOrderByName(filter).getResultList();
    }
}

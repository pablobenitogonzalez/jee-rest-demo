package org.test.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.test.error.beans.ErrorDetailService;
import org.test.model.Category;
import org.test.persistence.interfaces.CategoryDao;
import org.test.persistence.jpa.CategoryJpaDao;
import org.test.persistence.jpa.JpaDao;
import org.test.service.beans.CategoryServiceBean;
import org.test.service.interfaces.CategoryService;
import org.test.utils.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.test.utils.ApplicationStringsTest.LOGGER_END;
import static org.test.utils.ApplicationStringsTest.LOGGER_START;

@RunWith(Arquillian.class)
@Transactional
public class CategoryServiceTest {

    private static Log logger = LogFactory.getLog(CategoryServiceTest.class);

    @EJB
    private CategoryService categoryService;

    @Inject
    private RoleAdmin admin;

    @Deployment
    public static Archive<?> deployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(ApplicationException.class.getPackage())
                .addPackage(ErrorDetailService.class.getPackage())
                .addPackage(Category.class.getPackage())
                .addPackage(JpaDao.class.getPackage())
                .addPackage(CategoryDao.class.getPackage())
                .addPackage(CategoryJpaDao.class.getPackage())
                .addPackage(CategoryService.class.getPackage())
                .addPackage(CategoryServiceBean.class.getPackage())
                .addPackage(RoleAdmin.class.getPackage())
                .addAsResource("persistence-datasource.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getCategoryTakeCategoryWithSubcategoriesTest() throws Exception {
        admin.call(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                logger.info(LOGGER_START + "getCategoryTakeCategoryWithSubcategoriesTest");
                Category category = categoryService.getCategory(1L);
                assertNotNull(category);
                assertTrue(category.getId() == 1L);
                assertTrue(category.getSubcategories().size() == 8L);
                logger.info(LOGGER_END);
                return null;
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void getCategoryNotFoundTest() throws Exception {
        admin.call(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                logger.info(LOGGER_START + "getCategoryNotFoundTest");
                try {
                    categoryService.getCategory(99L);
                } catch (EJBException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
                return null;
            }
        });
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void createCategoryTest() throws Exception {
        admin.call(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                logger.info(LOGGER_START + "createCategoryTest");
                Category category = new Category("PRUEBA");
                Long id = categoryService.createCategory(category).getId();
                Category categoryCreated = categoryService.getCategory(id);
                assertNotNull(categoryCreated);
                assertTrue(categoryCreated.getName().equals("PRUEBA"));
                return null;
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void createCategoryRepeatedNameTest() throws Exception {
        admin.call(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                logger.info(LOGGER_START + "createCategoryRepeatedNameTest");
                try {
                    Category category = new Category("bioquímica");
                    categoryService.createCategory(category);
                } catch (EJBException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
                return null;
            }
        });
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void updateCategoryTest() throws Exception {
        admin.call(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                logger.info(LOGGER_START + "updateCategoryTest");
                Category oldCategory = categoryService.getCategory(1L);
                assertNotNull(oldCategory);
                Category category = new Category("@CHANGED@");
                category.setId(oldCategory.getId());
                categoryService.updateCategory(category);
                Category updatedCategory = categoryService.getCategory(1L);
                assertNotNull(updatedCategory);
                assertTrue(updatedCategory.getName().equals("@CHANGED@"));
                assertTrue(updatedCategory.getRecord().getCreated().equals(oldCategory.getRecord().getCreated()));
                logger.info(LOGGER_END);
                return null;
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void updateCategoryRepeatedNameTest() throws Exception {
        admin.call(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                logger.info(LOGGER_START + "updateCategoryRepeatedNameTest");
                try {
                    Category category = new Category("orina");
                    category.setId(1L);
                    categoryService.updateCategory(category);
                } catch (EJBException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
                return null;
            }
        });
    }

    @Test(expected = EJBTransactionRolledbackException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteCategoryTest() throws Exception {
        admin.call(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                logger.info(LOGGER_START + "deleteCategoryTest");
                Category category = new Category("PRUEBA");
                Long id = categoryService.createCategory(category).getId();
                categoryService.deleteCategory(id);
                try {
                    categoryService.getCategory(id);
                } catch (EJBTransactionRolledbackException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
                return null;
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteCategoryReferentialIntegrityTest() throws Exception {
        admin.call(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                logger.info(LOGGER_START + "deleteCategoryReferentialIntegrityTest");
                try {
                    categoryService.deleteCategory(1L);
                } catch (EJBException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
                return null;
            }
        });
    }

    @Test(expected = EJBAccessException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void unauthenticatedTest() throws Exception {
        try {
            logger.info(LOGGER_START + "unauthenticatedTest");
            Category category = new Category("PRUEBA");
            categoryService.createCategory(category);
        } catch (EJBAccessException e) {
            logger.info(e.getMessage());
            throw e;
        }
    }
}
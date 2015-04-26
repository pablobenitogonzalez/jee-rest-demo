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
import org.test.model.Subcategory;
import org.test.persistence.interfaces.CategoryDao;
import org.test.persistence.interfaces.SubcategoryDao;
import org.test.persistence.jpa.CategoryJpaDao;
import org.test.persistence.jpa.JpaDao;
import org.test.persistence.jpa.SubcategoryJpaDao;
import org.test.service.beans.CategoryServiceBean;
import org.test.service.beans.SubcategoryServiceBean;
import org.test.service.interfaces.CategoryService;
import org.test.service.interfaces.SubcategoryService;
import org.test.utils.ApplicationException;

import javax.ejb.EJB;
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
public class SubcategoryServiceTest {

    private static Log logger = LogFactory.getLog(SubcategoryServiceTest.class);

    @EJB
    private CategoryService categoryService;

    @EJB
    private SubcategoryService subcategoryService;

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
                .addPackage(Subcategory.class.getPackage())
                .addPackage(SubcategoryDao.class.getPackage())
                .addPackage(SubcategoryJpaDao.class.getPackage())
                .addPackage(SubcategoryService.class.getPackage())
                .addPackage(SubcategoryServiceBean.class.getPackage())
                .addPackage(RoleAdmin.class.getPackage())
                .addAsResource("persistence-datasource.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getSubcategoryTest() throws Exception {
        admin.call(new Callable<Subcategory>() {
            @Override
            public Subcategory call() throws Exception {
                logger.info(LOGGER_START + "getSubcategoryTest");
                Subcategory subcategory = subcategoryService.getSubcategory(1L);
                assertNotNull(subcategory);
                assertTrue(subcategory.getId() == 1L);
                logger.info(LOGGER_END);
                return null;
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void getSubcategoryNotFoundTest() throws Exception {
        admin.call(new Callable<Subcategory>() {
            @Override
            public Subcategory call() throws Exception {
                logger.info(LOGGER_START + "getSubcategoryNotFoundTest");
                try {
                    subcategoryService.getSubcategory(99L);
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
    public void createSubcategoryTest() throws Exception {
        admin.call(new Callable<Subcategory>() {
            @Override
            public Subcategory call() throws Exception {
                logger.info(LOGGER_START + "createSubcategoryTest");
                Category category = categoryService.getCategory(1L);
                logger.info(category);
                Subcategory subcategory = new Subcategory("PRUEBA", category);
                logger.info(subcategory);
                Subcategory subcategoryCreated = subcategoryService.createSubcategory(subcategory);
                assertNotNull(subcategoryCreated);
                assertTrue(subcategoryCreated.getName().equals("PRUEBA"));
                logger.info(LOGGER_END);
                return null;
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void createSubcategoryRepeatedNaturalKeyTest() throws Exception {
        admin.call(new Callable<Subcategory>() {
            @Override
            public Subcategory call() throws Exception {
                logger.info(LOGGER_START + "createSubcategoryRepeatedNaturalKeyTest");
                try {
                    Category category = categoryService.getCategory(1L);
                    Subcategory subcategory = new Subcategory("IONES", category);
                    subcategoryService.createSubcategory(subcategory);
                    return null;
                } catch (EJBException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
            }
        });
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void updateSubcategoryTest() throws Exception {
        admin.call(new Callable<Subcategory>() {
            @Override
            public Subcategory call() throws Exception {
                logger.info(LOGGER_START + "updateSubcategoryTest");
                Subcategory oldSubcategory = subcategoryService.getSubcategory(1L);
                assertNotNull(oldSubcategory);
                Subcategory subcategory = new Subcategory("@CHANGED@", oldSubcategory.getCategory());
                subcategory.setId(oldSubcategory.getId());
                subcategoryService.updateSubcategory(subcategory);
                Subcategory updatedSubcategory = subcategoryService.getSubcategory(1L);
                assertNotNull(updatedSubcategory);
                assertTrue(updatedSubcategory.getName().equals("@CHANGED@"));
                assertTrue(updatedSubcategory.getRecord().getCreated().equals(oldSubcategory.getRecord().getCreated()));
                logger.info(LOGGER_END);
                return null;
            }
        });
    }

    @Test(expected = EJBException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void updateSubcategoryRepeatedNaturalKeyTest() throws Exception {
        admin.call(new Callable<Subcategory>() {
            @Override
            public Subcategory call() throws Exception {
                logger.info(LOGGER_START + "updateSubcategoryRepeatedNaturalKeyTest");
                try {
                    Category category = categoryService.getCategory(1L);
                    Subcategory subcategory = new Subcategory("IONES", category);
                    subcategory.setId(1L);
                    subcategoryService.updateSubcategory(subcategory);
                    return null;
                } catch (EJBException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
            }
        });
    }

    @Test(expected = EJBTransactionRolledbackException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteSubcategoryTest() throws Exception {
        admin.call(new Callable<Subcategory>() {
            @Override
            public Subcategory call() throws Exception {
                logger.info(LOGGER_START + "deleteSubcategoryTest");
                Category category = categoryService.getCategory(1L);
                Long id = subcategoryService.createSubcategory(new Subcategory("PRUEBA", category)).getId();
                subcategoryService.deleteSubcategory(id);
                try {
                    subcategoryService.getSubcategory(id);
                    return null;
                } catch (EJBTransactionRolledbackException e) {
                    ApplicationException exception = (ApplicationException) e.getCausedByException();
                    logger.info(exception.getErrorWrapper().toString());
                    logger.info(LOGGER_END);
                    throw e;
                }
            }
        });
    }
}
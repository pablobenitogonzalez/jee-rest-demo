package org.test.utils;

public class ApplicationStrings {

    // Aux
	public static final String PERSISTENCE_UNIT_NAME    = "demo";
    public static final String SECURITY_DOMAIN          = "jee-auth-basic-domain";
    public static final String CREATING                 = "New object ";
    // Roles
    public static final String ROLE_ADMIN               = "ADMIN";
    public static final String ROLE_USER                = "USER";
    // Tables
    public static final String CATEGORY                 = "category";
    public static final String SUBCATEGORY              = "subcategory";
    public static final String LOGIN                    = "login";
    // Fields
    public static final String ID                       = "id";
    public static final String CREATED                  = "created";
    public static final String LAST_UPDATE              = "last_update";
    public static final String NAME                     = "name";
    public static final String EMAIL                    = "email";
    public static final String PASSWORD                 = "password";
    public static final String ROLE                     = "role";
    // Natural keys
    public static final String NK_CATEGORY              = "name";
    public static final String NK_SUBCATEGORY           = "name; subcategory";
    public static final String NK_LOGIN                 = "email";
    // Aux
    public static final String FILTER                   = "filter";
    public static final String RECORD                   = "record";
    public static final String AUTH_ACCESS              = "auth_access";
    public static final String AUTH_LOGIN               = "auth_login";
    // Queries CATEGORY
    public static final String CATEGORY_FIND_FILTER_BY_NAME                       = "category.001";
    public static final String CATEGORY_FIND_SUBCATEGORIES_REFERENCING_THIS       = "category.002";
    public static final String CATEGORY_FIND_ALL_ORDER_BY_NAME_ASC                = "category.003";
    public static final String CATEGORY_FIND_ALL_LIKE_ORDER_BY_NAME_ASC           = "category.004";
    // Queries SUBCATEGORY
    public static final String SUBCATEGORY_FIND_FILTER_BY_NAME_CATEGORY           = "subcategory.001";
    public static final String SUBCATEGORY_FIND_ALL_ORDER_BY_NAME_ASC             = "subcategory.002";
    public static final String SUBCATEGORY_FIND_ALL_LIKE_ORDER_BY_NAME_ASC        = "subcategory.003";
    // Queries LOGIN
    public static final String LOGIN_FIND_FILTER_BY_EMAIL                         = "login.001";
    public static final String LOGIN_FIND_BY_EMAIL_AND_PASSWORD                   = "login.002";
    public static final String LOGIN_FIND_ALL_ORDER_BY_EMAIL_ASC                  = "login.003";
    // Errors
    public static final String ERROR_NOT_FOUND                     = "not found";
    public static final String ERROR_UNIQUE_KEY                    = "unique key";
    public static final String ERROR_REFERENTIAL_INTEGRITY         = "referential integrity";
    public static final String ERROR_VALIDATION                    = "constraint violation";
    public static final String ERROR_INTERNAL_SERVER               = "internal server";
    public static final String ERROR_ROLE_ACCESS                   = "unauthorized";
    public static final String ERROR_LOGIN_ACCESS                  = "unauthenticated";
    public static final String ERROR_USER_CONTEXT                  = "different user";
    public static final String ERROR_BAD_PARAM                     = "bad paramether";
    // Errors : message
    public static final String MESSAGE_NOT_FOUND                   = "entidad no encontrada";
    public static final String MESSAGE_UNIQUE_KEY                  = "error de clave única";
    public static final String MESSAGE_REFERENTIAL_INTEGRITY       = "error de integridad referencial";
    public static final String MESSAGE_VALIDATION                  = "error de validación";
    public static final String MESSAGE_INTERNAL_SERVER             = "error interno del servidor";
    public static final String MESSAGE_ROLE_ACCESS                 = "no tiene autorización para el recurso";
    public static final String MESSAGE_LOGIN_ACCESS                = "el usuario no se ha podido autenticar";
    public static final String MESSAGE_USER_CONTEXT                = "el usuario no se corresponde con el autenticado";
    public static final String MESSAGE_BAD_PARAM                   = "parametro mal informado";
    // Errors : detail message
    public static final String DETAIL_CATEGORY_NOT_FOUND                  = "la categoría no existe";
    public static final String DETAIL_CATEGORY_UNIQUE_KEY                 = "ya existe una categoría con ese nombre";
    public static final String DETAIL_CATEGORY_REFERENTIAL_INTEGRITY      = "existen subcategorías que hacen referencia a la categoría";
    public static final String DETAIL_CATEGORY_CREATE_AUTH                = "permisos insuficientes para crear la categoría";
    public static final String DETAIL_CATEGORY_UPDATE_AUTH                = "permisos insuficientes para actualizar la categoría";
    public static final String DETAIL_CATEGORY_DELETE_AUTH                = "permisos insuficientes para eliminar la categoría";
    public static final String DETAIL_CATEGORY_GET_AUTH                   = "permisos insuficientes para listar la/s categoría/s";
    public static final String DETAIL_SUBCATEGORY_NOT_FOUND               = "la subcategoria no existe";
    public static final String DETAIL_SUBCATEGORY_UNIQUE_KEY              = "para esa categoría ya existe una subcategoría con ese nombre";
    public static final String DETAIL_SUBCATEGORY_CREATE_AUTH             = "permisos insuficientes para crear la subcategoría";
    public static final String DETAIL_SUBCATEGORY_UPDATE_AUTH             = "permisos insuficientes para actualizar la subcategoría";
    public static final String DETAIL_SUBCATEGORY_DELETE_AUTH             = "permisos insuficientes para eliminar la subcategoría";
    public static final String DETAIL_SUBCATEGORY_GET_AUTH                = "permisos insuficientes para listar la/s subcategoría/s";
    public static final String DETAIL_LOGIN_NOT_FOUND                     = "el login no existe";
    public static final String DETAIL_LOGIN_UNIQUE_KEY                    = "ya existe un login con ese email";
    public static final String DETAIL_LOGIN_UPDATE_AUTH                   = "permisos insuficientes para actualizar el login";
    public static final String DETAIL_LOGIN_DELETE_AUTH                   = "permisos insuficientes para eliminar el login";
    public static final String DETAIL_BAD_AUTH                            = "el email y/o password son incorrectos";
    public static final String DETAIL_NOT_THE_SAME_USER_CONTEXT           = "el usuario no se corresponde con el autenticado";

    // REST URI method
    public static final String GET_CATEGORY           = "getCategory";
    public static final String GET_SUBCATEGORY        = "getSubcategory";
    public static final String DELETE_LOGIN           = "deleteLogin";

	private ApplicationStrings() {
		throw new AssertionError();
	}
}

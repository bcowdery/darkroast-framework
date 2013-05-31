/*
 * Doula Company Client Manager
 * Copyright (c) 2012 Brian Cowdery
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 */

package com.darkroast.cdi;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Internal class for fetching the CDI BeanManager from the JNDI context.
 *
 * @author Brian Cowdery
 * @since 11-Oct-2012
 */
class BeanManagerLookup {

    /** The key under which the BeanManager can be found according to CDI API docs */
    public static final String CDI_JNDIKEY_BEANMANAGER_COMP = "java:comp/BeanManager";
    /** The key under which the BeanManager can be found according to JBoss Weld docs */
    public static final String CDI_JNDIKEY_BEANMANAGER_APP = "java:app/BeanManager";
	/** The key under which the BeanManager can be found in pure Servlet containers according to JBoss Weld docs. */
	public static final String CDI_JNDIKEY_BEANMANAGER_COMP_ENV = "java:comp/env/BeanManager";

    private BeanManagerLookup() {}

    /**
     * Try to find the CDI BeanManager from the JNDI Context. This method will look in several common locations
     * as outlined in the CDI API and JBoss Weld docs.
     *
   	 * @return the BeanManager, if found. <tt>null</tt> otherwise.
   	 */
   	public static BeanManager findBeanManager() {
   		BeanManager bm = null;
   		try {
   			Context initialContext = new InitialContext();
   			if (bm == null) bm = lookup(initialContext, CDI_JNDIKEY_BEANMANAGER_COMP);
   			if (bm == null) bm = lookup(initialContext, CDI_JNDIKEY_BEANMANAGER_APP);
   			if (bm == null) bm = lookup(initialContext, CDI_JNDIKEY_BEANMANAGER_COMP_ENV);
   		} catch ( NamingException e ) {
   			/* noop */
   		}
   		return bm;
   	}

    private static BeanManager lookup(Context context, String jndiKey) {
   		BeanManager result = null;
   		try {
   			result = (BeanManager) context.lookup(jndiKey);
   		} catch (NamingException e) {
            /* noop */
   		}
   		return result;
   	}

}

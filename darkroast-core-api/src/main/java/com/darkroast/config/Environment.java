/*
 * Doula Company Client Manager
 * Copyright (c) 2013 Brian Cowdery
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 */

package com.darkroast.config;

/**
 * The applications current runtime environment.
 *
 * @see ApplicationConfig#getEnvironment();
 *
 * @author Brian Cowdery
 * @since 06-03-2013
 */
public enum Environment {
    DEV, TEST, PRODUCTION
}

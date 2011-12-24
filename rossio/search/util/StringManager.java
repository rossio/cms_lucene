package com.rossio.search.util;

import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class StringManager {
	/**
	 * log message recorder
	 */
	private static Logger log = Logger.getLogger(StringManager.class);
	
	private static Map managers = new HashMap();

	private ResourceBundle bundle = null;
	
	private Locale locale = null;
	
	private String managerPackName = null;

	private String resourceFilePrefixName = null;

	private StringManager() {

	}

	private StringManager(String packName, String resourceFilePrefixName) {
		this.managerPackName = packName;
		this.resourceFilePrefixName = resourceFilePrefixName;
		this.initialize();
	}

	private StringManager(String packName, String resourceFilePrefixName,
			Locale locale) {
		this.managerPackName = packName;
		this.locale = locale;
		this.resourceFilePrefixName = resourceFilePrefixName;
		this.initialize();
	}

	private void initialize() {
		String baseName = this.managerPackName + "." + this.resourceFilePrefixName;
		if (this.locale == null) {
			this.locale = Locale.getDefault();
		}
		try {
			bundle = ResourceBundle.getBundle(baseName, locale);
		} catch (MissingResourceException e) {
			log.error(e);
			// if occur exception try another method for getting.
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			if (loader != null) {
				try {
					bundle = ResourceBundle.getBundle(baseName, locale, loader);
					return;
				} catch (MissingResourceException e2) {
					log.equals(e2);
				}
			} else {
				// Record the reason of the failure
				loader = this.getClass().getClassLoader();
				if (log.isDebugEnabled())
					log.debug("Can't find resource " + baseName + " " + loader);
				if (loader instanceof URLClassLoader) {
					if (log.isDebugEnabled())
						log.debug(((URLClassLoader) loader).getURLs());
				}
			}

		}

	}


	public static synchronized StringManager getStringManager(String packName,
			String resourceFilePrefixName) {
		if (StringHelper.isBlank(packName)
				|| StringHelper.isBlank(resourceFilePrefixName)) {
			throw new IllegalArgumentException(
					"Error:The specified packName can not be null or blank!");
		}
		StringManager sm = (StringManager) managers.get(packName);
		if (sm == null) {
			sm = new StringManager(packName, resourceFilePrefixName);
			managers.put(packName, sm);
		}
		return sm;
	}

	public static synchronized StringManager getStringManager(String packName,
			String resourceFilePrefixName, Locale locale) {
		if (StringHelper.isBlank(packName)
				|| StringHelper.isBlank(resourceFilePrefixName)) {
			throw new IllegalArgumentException(
					"Error:The specified packName can not be null or blank!");
		}
		StringManager sm = (StringManager) managers.get(packName);
		if (sm == null) {
			sm = new StringManager(packName, resourceFilePrefixName, locale);
			managers.put(packName, sm);
		}
		return sm;
	}

	public static synchronized StringManager getStringManager(String packName,
			String resourceFilePrefixName, Locale locale,
			boolean isRefreshLocale) {
		if (StringHelper.isBlank(packName)
				|| StringHelper.isBlank(resourceFilePrefixName)) {
			throw new IllegalArgumentException(
					"Error:The specified packName can not be null or blank!");
		}
		StringManager sm = (StringManager) managers.get(packName);
		if (isRefreshLocale || sm == null) {
			sm = new StringManager(packName, resourceFilePrefixName, locale);
			managers.put(packName, sm);
		}
		return sm;
	}

	private String getStringInternal(String key) {
		if (StringHelper.isBlank(key)) {
			throw new IllegalArgumentException(
					"Error:The key can not be null or blank!");
		}
		String value = "";
		try {
			value = bundle.getString(key);
		} catch (NullPointerException e) {
			value = "java.lang.NullPointerException";
		} catch (MissingResourceException e) {
			value = e.getMessage();
		}
		return value;
	}

	public String getString(String key) {
		return getString(key, (Object[]) null);
	}

	public String getString(String key, Object[] objects) {
		String result = null;
		String value = getStringInternal(key);
		if (objects != null) {
			try {
				// ensure the objects are not null
				Object nonNullArgs[] = objects;
				for (int i = 0; i < objects.length; i++) {
					if (objects[i] == null) {
						if (nonNullArgs == objects)
							nonNullArgs = (Object[]) objects.clone();
						nonNullArgs[i] = "null";
					}
				}
				result = MessageFormat.format(value, nonNullArgs);
			} catch (IllegalArgumentException e) {
				StringBuffer buf = new StringBuffer();
				buf.append(e.getMessage());
				buf.append("\t");
				buf.append(value);
				buf.append(":");
				for (int i = 0; i < objects.length; i++) {
					buf.append(" arg[" + i + "]=" + objects[i]);
				}
				result = buf.toString();
			}
		} else {
			result = value;
		}
		return result;
	}
}

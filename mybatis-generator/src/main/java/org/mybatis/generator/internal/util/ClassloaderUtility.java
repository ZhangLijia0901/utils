/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.mybatis.generator.internal.util;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.utils.DataBaseType;

/**
 * This class holds methods useful for constructing custom classloaders.
 * 
 * @author Jeff Butler
 * 
 */
public class ClassloaderUtility {

	/**
	 * Utility Class - No Instances
	 */
	private ClassloaderUtility() {
	}

	public static Map<DataBaseType, ClassLoader> getCustomClassloader(Map<DataBaseType, String> entries) {

		Map<DataBaseType, ClassLoader> ucls = new HashMap<DataBaseType, ClassLoader>();
		File file;
		ClassLoader parent = Thread.currentThread().getContextClassLoader();
		if (entries != null) {
			for (Map.Entry<DataBaseType, String> entry : entries.entrySet()) {
				if (ObjectFactory.getExternalClassLoader(entry.getKey()) != null)
					continue;
				file = new File(entry.getValue());
				if (!file.exists()) {
					throw new RuntimeException(getString("RuntimeError.9", entry.getValue()));
				}

				try {
					URLClassLoader ucl = new URLClassLoader(new URL[] { file.toURI().toURL() }, parent);
					ucls.put(entry.getKey(), ucl);
				} catch (MalformedURLException e) {
					// this shouldn't happen, but just in case...
					throw new RuntimeException(getString("RuntimeError.9", entry.getValue()));
				}
			}
		}

		return ucls;
	}
}

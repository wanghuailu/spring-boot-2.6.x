/*
 * Copyright 2012-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.integration;

import javax.sql.DataSource;

import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Initializer for Spring Integration schema.
 *
 * @author Vedran Pavic
 * @since 2.0.0
 * @deprecated since 2.6.0 for removal in 3.0.0 in favor of
 * {@link IntegrationDataSourceScriptDatabaseInitializer}
 */
@Deprecated
public class IntegrationDataSourceInitializer extends org.springframework.boot.jdbc.AbstractDataSourceInitializer {

	private final IntegrationProperties.Jdbc properties;

	public IntegrationDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader,
			IntegrationProperties properties) {
		super(dataSource, resourceLoader);
		Assert.notNull(properties, "IntegrationProperties must not be null");
		this.properties = properties.getJdbc();
	}

	@Override
	protected org.springframework.boot.jdbc.DataSourceInitializationMode getMode() {
		DatabaseInitializationMode mode = this.properties.getInitializeSchema();
		switch (mode) {
		case ALWAYS:
			return org.springframework.boot.jdbc.DataSourceInitializationMode.ALWAYS;
		case EMBEDDED:
			return org.springframework.boot.jdbc.DataSourceInitializationMode.EMBEDDED;
		case NEVER:
		default:
			return org.springframework.boot.jdbc.DataSourceInitializationMode.NEVER;
		}
	}

	@Override
	protected String getSchemaLocation() {
		return this.properties.getSchema();
	}

	@Override
	protected String getDatabaseName() {
		String platform = this.properties.getPlatform();
		if (StringUtils.hasText(platform)) {
			return platform;
		}
		return super.getDatabaseName();
	}

}

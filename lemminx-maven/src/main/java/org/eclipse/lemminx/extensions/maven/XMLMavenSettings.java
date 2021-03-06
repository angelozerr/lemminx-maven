/*******************************************************************************
 * Copyright (c) 2021 Red Hat Inc. and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.lemminx.extensions.maven;

import java.util.Objects;

public class XMLMavenSettings {

	private XMLMavenIndexSettings index;

	private XMLMavenRepoSettings repo;

	private String globalSettings;

	private String userSettings;

	private String indexLocation;

	public XMLMavenSettings() {
		setIndex(new XMLMavenIndexSettings());
		setRepo(new XMLMavenRepoSettings());
	}

	public XMLMavenIndexSettings getIndex() {
		return index;
	}

	public void setIndex(XMLMavenIndexSettings index) {
		this.index = index;
	}

	public XMLMavenRepoSettings getRepo() {
		return repo;
	}

	public void setRepo(XMLMavenRepoSettings repo) {
		this.repo = repo;
	}

	public String getUserSettings() {
		return userSettings;
	}

	public void setUserSettings(String userSettings) {
		this.userSettings = userSettings;
	}

	public String getGlobalSettings() {
		return globalSettings;
	}

	public void setGlobalSettings(String globalSettings) {
		this.globalSettings = globalSettings;
	}

	public String getIndexLocation() {
		return indexLocation;
	}

	public void setIndexLocation(String indexLocation) {
		this.indexLocation = indexLocation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(globalSettings, index, indexLocation, repo, userSettings);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLMavenSettings other = (XMLMavenSettings) obj;
		return Objects.equals(globalSettings, other.globalSettings) && Objects.equals(index, other.index)
				&& Objects.equals(indexLocation, other.indexLocation) && Objects.equals(repo, other.repo)
				&& Objects.equals(userSettings, other.userSettings);
	}

}

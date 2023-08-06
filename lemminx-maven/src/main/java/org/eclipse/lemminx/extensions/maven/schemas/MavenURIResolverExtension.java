/*******************************************************************************
 * Copyright (c) 2023 Red Hat Inc. and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.lemminx.extensions.maven.schemas;

import java.io.IOException;

import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.eclipse.lemminx.dom.DOMDocument;
import org.eclipse.lemminx.services.extensions.XMLExtensionsRegistry;
import org.eclipse.lemminx.uriresolver.CacheResourcesManager;
import org.eclipse.lemminx.uriresolver.CacheResourcesManager.ResourceToDeploy;
import org.eclipse.lemminx.uriresolver.URIResolverExtension;

/**
 * Resolve the XML Schema of maven for pom.xml which have no schema declared
 *
 */
public class MavenURIResolverExtension implements URIResolverExtension {

	private static final String POM_NAMESPACE_URI = "http://maven.apache.org/POM/4.0.0"; //$NON-NLS-1$

	private static final String POM_SYSTEM = "http://maven.apache.org/xsd/maven-4.0.0.xsd";
	private static final ResourceToDeploy POM_RESOURCE = new ResourceToDeploy(POM_SYSTEM,
			"schemas/maven-4.0.0.xsd");
	private final XMLExtensionsRegistry extensionsRegistry;

	@Override
	public String getName() {
		return "embedded maven.xsd";
	}

	public MavenURIResolverExtension(XMLExtensionsRegistry extensionsRegistry) {
		this.extensionsRegistry = extensionsRegistry;
	}

	@Override
	public String resolve(String baseLocation, String publicId, String systemId) {
		/*if (!POM_NAMESPACE_URI.equals(publicId)) {
			return null;
		}*/
		if (!isPomWithoutSchema(baseLocation)) {
			return null;
		}
		try {
			return CacheResourcesManager.getResourceCachePath(POM_RESOURCE).toFile().toURI().toString();
		} catch (Exception e) {
			// Do nothing?
		}
		return POM_SYSTEM;
	}

	@Override
	public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier) throws XNIException, IOException {
		if (!isPomWithoutSchema(resourceIdentifier.getBaseSystemId())) {
			return null;
		}
		String publicId = resourceIdentifier.getNamespace();
		if (POM_NAMESPACE_URI.equals(publicId)) {
			String baseLocation = resourceIdentifier.getBaseSystemId();
			String catalogFilePath = resolve(baseLocation, publicId, null);
			if (catalogFilePath != null) {
				return new XMLInputSource(publicId, catalogFilePath, catalogFilePath);
			}
		}
		return null;
	}

	private boolean isPomWithoutSchema(String uri) {
		DOMDocument document = extensionsRegistry.getDocumentProvider().getDocument(uri);
		if (document == null) {
			return false;
		}
		if (document.hasDTD() || document.hasSchemaLocation() || document.hasNoNamespaceSchemaLocation()) {
			return false;
		}
		return uri.endsWith("pom.xml");
	}
}

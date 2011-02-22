/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.har2arq.adapters;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.testharness.impl.packaging.TCKArtifact;

/**
 * Abstract packaging adapter.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class AbstractPackagingAdapter<T extends TCKArtifact, U extends Archive> implements PackagingAdapter<T, U>
{
   private Class<T> artifactClass;
   private Class<U> archiveClass;

   protected AbstractPackagingAdapter(Class<T> artifactClass,  Class<U> archiveClass)
   {
      if (artifactClass == null)
         throw new IllegalArgumentException("Null artifact class");
      if (archiveClass == null)
         throw new IllegalArgumentException("Null archive class");
      this.artifactClass = artifactClass;
      this.archiveClass = archiveClass;
   }

   public Class<U> archiveClass()
   {
      return archiveClass;
   }

   public void validate(TCKArtifact artifact)
   {
      if (artifactClass.isInstance(artifact) == false)
         throw new IllegalArgumentException("Invalid artifact type: " + artifact + " != " + artifactClass);
   }

   /**
    * Create archive.
    *
    * @param artifact the artifact
    * @return new archive instance
    */
   protected U createArchive(TCKArtifact artifact)
   {
      U archive = ShrinkWrap.create(archiveClass, artifact.getDefaultName());
      fillBasic(artifact, archive);
      return archive;
   }

   /**
    * Fill basic config.
    *
    * @param artifact the artifact
    * @param archive the archive
    */
   protected void fillBasic(TCKArtifact artifact, Archive archive)
   {
      // TODO
   }
}
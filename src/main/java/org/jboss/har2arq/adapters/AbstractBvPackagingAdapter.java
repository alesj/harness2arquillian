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
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.testharness.impl.packaging.jsr303.JSR303ArtifactDescriptor;

/**
 * Abstract BV packaging adapter.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class AbstractBvPackagingAdapter<U extends Archive> extends AbstractPackagingAdapter<JSR303ArtifactDescriptor, U>
{
   public AbstractBvPackagingAdapter(Class<U> archiveClass)
   {
      super(JSR303ArtifactDescriptor.class, archiveClass);
   }

   public U adapt(JSR303ArtifactDescriptor artifact)
   {
      U archive = createArchive(artifact);
      return archive;
   }
}
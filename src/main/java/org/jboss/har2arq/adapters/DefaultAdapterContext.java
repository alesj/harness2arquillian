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

import org.jboss.arquillian.spi.TestClass;
import org.jboss.har2arq.types.JSR;
import org.jboss.har2arq.types.Packaging;
import org.jboss.testharness.api.Configuration;
import org.jboss.testharness.impl.ConfigurationFactory;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.ArtifactGenerator;
import org.jboss.testharness.impl.packaging.ArtifactType;
import org.jboss.testharness.impl.packaging.PackagingType;
import org.jboss.testharness.impl.packaging.TCKArtifact;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class DefaultAdapterContext implements AdapterContext
{
   private Packaging defaultPackaging = Packaging.WAR;
   private TCKArtifact artifact;
   private PackagingAdapter adapter;

   private TestClass testClass;
   private Packaging packaging;
   private JSR jsr;

   public DefaultAdapterContext(TestClass testClass)
   {
      if (testClass == null)
         throw new IllegalArgumentException("Null test class");
      this.testClass = testClass;
   }

   public Packaging packaging()
   {
      if (packaging == null)
      {
         org.jboss.testharness.impl.packaging.Packaging p = testClass().getAnnotation(org.jboss.testharness.impl.packaging.Packaging.class);
         if (p != null)
         {
            PackagingType pt = p.value();
            if (pt == PackagingType.WAR)
               packaging = Packaging.WAR;
            else if (pt == PackagingType.EAR)
               packaging = Packaging.EAR;
         }
         else
         {
            packaging = defaultPackaging;
         }
      }
      return packaging;
   }

   public TCKArtifact initialArtifact()
   {
      if (artifact == null)
      {
         Configuration configuration = ConfigurationFactory.get();
         ArtifactGenerator artifactGenerator = new ArtifactGenerator(configuration);
         artifact = artifactGenerator.createArtifact(testClass());
      }
      return artifact;
   }

   public PackagingAdapter packagingAdapter()
   {
      if (adapter == null)
         adapter = PackagingAdapters.get(packaging(), jsr());

      return adapter;
   }

   protected Class<?> testClass()
   {
      return testClass.getJavaClass();
   }

   protected JSR jsr()
   {
      if (jsr == null)
      {
         Artifact a = testClass().getAnnotation(Artifact.class);
         if (a != null)
         {
            ArtifactType at = a.artifactType();
            if (at == ArtifactType.JSR299)
               jsr = JSR.CDI;
            else if (at == ArtifactType.JSR303)
               jsr = JSR.BV;
         }
         if (jsr == null)
            jsr = JSR.NONE;
      }
      return jsr;
   }

   public void setDefaultPackaging(Packaging defaultPackaging)
   {
      if (defaultPackaging == null)
         throw new IllegalArgumentException("Null default packaging");
      this.defaultPackaging = defaultPackaging;
   }
}

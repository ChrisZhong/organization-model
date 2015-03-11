/*
 * Attribute.java
 *
 * Created on Oct 15, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.Organization;
import org.models.organization.id.Identifiable;

/**
 * The {@linkplain Attribute} interface defines the attribute entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 5.0
 */
public interface Attribute extends Identifiable<Attribute> {
	/**
	 * The {@linkplain Type}> enumerates the various types of {@linkplain Attribute}s.
	 *
	 * @author Christopher Zhong
	 * @since 5.0
	 */
	enum Type {
		/**
		 * Indicates a quantity-type {@linkplain Attribute} (whose range is from <code>0.0</code> to <code>&#8734;</code>), and that higher values are better.
		 */
		POSITIVE_QUANTITY,
		/**
		 * Indicates a quantity-type {@linkplain Attribute} (whose range is from <code>0.0</code> to <code>&#8734;</code>), and that lower values are better.
		 */
		NEGATIVE_QUANTITY,
		/**
		 * Indicates a quantity-type {@linkplain Attribute} (whose range is from <code>0.0</code> to <code>1.0</code>), and that higher values are better.
		 */
		POSITIVE_QUALITY,
		/**
		 * Indicates a quantity-type {@linkplain Attribute} (whose range is from <code>0.0</code> to <code>1.0</code>), and that lower values are better.
		 */
		NEGATIVE_QUALITY,
		/**
		 * Indicates an unbounded-type {@linkplain Attribute} (whose range is from <code>-&#8734;</code> to <code>+&#8734;</code>), and that higher values are
		 * better.
		 */
		POSITIVE_UNBOUNDED,
		/**
		 * Indicates an unbounded-type {@linkplain Attribute} (whose range is from <code>-&#8734;</code> to <code>+&#8734;</code>), and that lower values are
		 * better.
		 */
		NEGATIVE_UNBOUNDED;
	}

	/**
	 * Returns the {@linkplain Attribute.Type} of this {@linkplain Attribute}.
	 *
	 * @return the {@linkplain Attribute.Type} of this {@linkplain Attribute}.
	 */
	Type getType();
}

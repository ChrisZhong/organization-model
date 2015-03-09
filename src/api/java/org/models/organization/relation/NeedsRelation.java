/**
 * NeedsRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.entity.Attribute;
import org.models.organization.entity.Role;

/**
 * Represents the needs relation, where a {@link Role} needs an {@link Attribute}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public class NeedsRelation {
	/**
	 * Internal <code>String</code> for the format of the <code>IllegalArgumentException</code>.
	 */
	private static final String EXCEPTION_FORMAT = "Parameters (role: %s, attribute: %s) cannot be null";

	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The <code>Role</code> of this <code>NeedsRelation</code>.
	 */
	private final Role role;

	/**
	 * The <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	private final Attribute attribute;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>NeedsRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>NeedsRelation</code>.
	 * @param attribute
	 *            the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	public NeedsRelation(final Role role, final Attribute attribute) {
		if (role == null || attribute == null) {
			throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, role, attribute));
		}
		this.role = role;
		this.attribute = attribute;
	}

	/**
	 * Returns the <code>Role</code> of this <code>NeedsRelation</code>.
	 *
	 * @return the <code>Role</code> of this <code>NeedsRelation</code>.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 *
	 * @return the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof NeedsRelation) {
			final NeedsRelation needsRelation = (NeedsRelation) object;
			return getRole().equals(needsRelation.getRole()) && getAttribute().equals(needsRelation.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getAttribute().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getId(), getAttribute().getIdentifier());
		}
		return toString;
	}
}

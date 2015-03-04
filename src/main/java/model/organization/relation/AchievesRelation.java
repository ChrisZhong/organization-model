/**
 * AchievesRelation.java
 * 
 * Created on Mar 3, 2005
 * 
 * See License.txt file the license agreement.
 */
package model.organization.relation;

import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;

/**
 * Represents the achieves relation, where a {@link Role} can achieve a
 * {@link SpecificationGoal}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.4.2.2 $, $Date: 2010/07/30 19:19:35 $
 * @see Role
 * @see SpecificationGoal
 * @since 1.0
 */
@SuppressWarnings("all")
public class AchievesRelation {

	/**
	 * Internal <code>String</code> for the format of the
	 * <code>IllegalArgumentException</code>.
	 */
	private static final String EXCEPTION_FORMAT = "Parameters (role : %s, goal : %s) cannot be null";

	/**
	 * Internal <code>String</code> for the format of the <code>toString</code>
	 * method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The <code>Role</code> of this <code>AchievesRelation</code>.
	 */
	private final Role role;

	/**
	 * The <code>SpecificationGoal</code> of this <code>AchievesRelation</code>.
	 */
	private final SpecificationGoal specificationGoal;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>AchievesRelation</code>.
	 * 
	 * @param role
	 *            the <code>Role</code> of this <code>AchievesRelation</code>.
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> of this
	 *            <code>AchievesRelation</code>.
	 */
	public AchievesRelation(final Role role,
			final SpecificationGoal specificationGoal) {
		if (role == null || specificationGoal == null) {
			throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT,
					role, specificationGoal));
		}
		this.role = role;
		this.specificationGoal = specificationGoal;
	}

	/**
	 * Returns the <code>Role</code> of this <code>AchievesRelation</code>.
	 * 
	 * @return the <code>Role</code> of this <code>AchievesRelation</code>.
	 */
	public Role getRole() {
		return this.role;
	}

	/**
	 * Returns the <code>SpecificationGoal</code> of this
	 * <code>AchievesRelation</code>.
	 * 
	 * @return the <code>SpecificationGoal</code> of this
	 *         <code>AchievesRelation</code>.
	 */
	public SpecificationGoal getSpecificationGoal() {
		return this.specificationGoal;
	}

	public boolean equals(final Object object) {
		if (object instanceof AchievesRelation) {
			AchievesRelation achievesRelation = (AchievesRelation) object;
			return getRole().equals(achievesRelation.getRole())
					&& getSpecificationGoal().equals(
							achievesRelation.getSpecificationGoal());

		}
		return false;
	}

	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16
					| getSpecificationGoal().hashCode();
		}
		return hashCode;
	}

	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getIdentifier(),
					getSpecificationGoal().getIdentifier());
		}
		return toString;
	}
}

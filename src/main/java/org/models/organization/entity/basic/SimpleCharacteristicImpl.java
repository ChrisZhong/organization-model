/*
 * SimpleAgentImpl.java
 *
 * Created on Mar 6, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity.basic;

import java.util.HashMap;
import java.util.Map;

import org.models.organization.identifier.UniqueId;

/**
 * The <code>SimpleCharacteristicImpl</code> class implements the {@link SimpleCharacteristic} interface.
 *
 * @author Christopher Zhong
 * @see SimpleAttribute
 * @since 3.2
 */
public class SimpleCharacteristicImpl implements SimpleCharacteristic {

	/**
	 * A mapping for <code>UniqueIdentifier</code> that maps to <code>SimpleCharacteristic</code> to ensure the uniqueness of <code>SimpleCharacteristic</code>.
	 */
	private static final Map<UniqueId, SimpleCharacteristic> uniqueCharacteristics = new HashMap<>();

	/**
	 * The <code>UniqueIdentifier</code> representing the <code>SimpleCharacteristic</code>.
	 */
	private final UniqueId identifier;

	/**
	 * Constructs a new instance of <code>SimpleCharacteristicImpl</code>.
	 *
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> representing the <code>SimpleCharacteristic</code>.
	 */
	protected SimpleCharacteristicImpl(final UniqueId identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException("Parameter (identifier) cannot be null");
		}
		this.identifier = identifier;
	}

	@Override
	public final UniqueId getIdentifier() {
		return identifier;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof SimpleCharacteristic) {
			final SimpleCharacteristic simpleCharacteristic = (SimpleCharacteristic) object;
			return getIdentifier().equals(simpleCharacteristic.getIdentifier());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public String toString() {
		return getIdentifier().toString();
	}

	/**
	 * Returns a <code>SimpleCharacteristic</code> instance representing the given <code>UniqueIdentifier</code>.
	 * <p>
	 * If a new <>SimpleCharacteristic</code> instance is not required, this method returns the existing <code>SimpleCharacteristic</code> instance.
	 *
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> representing the <code>SimpleCharacteristic</code> instance.
	 * @return a <code>SimpleCharacteristic</code> instance representing the given <code>UniqueIdentifier</code>.
	 */
	public static final SimpleCharacteristic createSimpleCharacteristic(final UniqueId identifier) {
		SimpleCharacteristic simpleCharacteristic = uniqueCharacteristics.get(identifier);
		if (simpleCharacteristic == null) {
			simpleCharacteristic = new SimpleCharacteristicImpl(identifier);
			uniqueCharacteristics.put(identifier, simpleCharacteristic);
		}
		return simpleCharacteristic;
	}

}
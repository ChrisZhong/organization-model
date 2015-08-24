package io.github.chriszhong.model.organization.entity;

import io.github.chriszhong.model.organization.id.UniqueId;

/**
 * The {@linkplain AttributeFactory} interface defines the APIs for constructing {@linkplain Attribute}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AttributeFactory {

	/**
	 * Constructs an {@linkplain Attribute}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
	 * @param type
	 *            the {@linkplain Attribute.Type} of the {@linkplain Attribute}.
	 * @return an {@linkplain Attribute}.
	 */
	Attribute buildAttribute(UniqueId<Attribute> id, Attribute.Type type);

}
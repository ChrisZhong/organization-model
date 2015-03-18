package model.organization.event;

/**
 * The {@linkplain UpdateCategory} enumerates the three category of updates that can occur: something was added, something was removed, something was changed.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public enum UpdateCategory {

	/**
	 * Indicates that something was added.
	 */
	ADDED,
	/**
	 * Indicates that something was removed.
	 */
	REMOVED,
	/**
	 * Indicates that something was changed.
	 */
	CHANGED

}

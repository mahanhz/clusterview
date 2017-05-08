package org.amhzing.clusterview.adapter.web.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Immutable implementation of {@link ClusterDto}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableClusterDto.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code ImmutableClusterDto.of()}.
 */
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@Generated({"Immutables.generator", "ClusterDto"})
@Immutable
public final class ImmutableClusterDto implements ClusterDto {
  private final String id;

  private ImmutableClusterDto(String id) {
    this.id = Preconditions.checkNotNull(id, "id");
  }

  private ImmutableClusterDto(ImmutableClusterDto original, String id) {
    this.id = id;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public String id() {
    return id;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ClusterDto#id() id} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableClusterDto withId(String value) {
    if (this.id.equals(value)) return this;
    String newValue = Preconditions.checkNotNull(value, "id");
    return validate(new ImmutableClusterDto(this, newValue));
  }

  /**
   * This instance is equal to all instances of {@code ImmutableClusterDto} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableClusterDto
        && equalTo((ImmutableClusterDto) another);
  }

  private boolean equalTo(ImmutableClusterDto another) {
    return id.equals(another.id);
  }

  /**
   * Computes a hash code from attributes: {@code id}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + id.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ClusterDto} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ClusterDto")
        .omitNullValues()
        .add("id", id)
        .toString();
  }

  /**
   * Construct a new immutable {@code ClusterDto} instance.
   * @param id The value for the {@code id} attribute
   * @return An immutable ClusterDto instance
   */
  public static ImmutableClusterDto of(String id) {
    return validate(new ImmutableClusterDto(id));
  }


  private static ImmutableClusterDto validate(ImmutableClusterDto instance) {
    instance.check();
    return instance;
  }

  /**
   * Creates an immutable copy of a {@link ClusterDto} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ClusterDto instance
   */
  public static ImmutableClusterDto copyOf(ClusterDto instance) {
    if (instance instanceof ImmutableClusterDto) {
      return (ImmutableClusterDto) instance;
    }
    return ImmutableClusterDto.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableClusterDto ImmutableClusterDto}.
   * @return A new ImmutableClusterDto builder
   */
  public static ImmutableClusterDto.Builder builder() {
    return new ImmutableClusterDto.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableClusterDto ImmutableClusterDto}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String id;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ClusterDto} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ClusterDto instance) {
      Preconditions.checkNotNull(instance, "instance");
      id(instance.id());
      return this;
    }

    /**
     * Initializes the value for the {@link ClusterDto#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(String id) {
      this.id = Preconditions.checkNotNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Builds a new {@link ImmutableClusterDto ImmutableClusterDto}.
     * @return An immutable instance of ClusterDto
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableClusterDto build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return ImmutableClusterDto.validate(new ImmutableClusterDto(null, id));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = Lists.newArrayList();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      return "Cannot build ClusterDto, some of required attributes are not set " + attributes;
    }
  }
}

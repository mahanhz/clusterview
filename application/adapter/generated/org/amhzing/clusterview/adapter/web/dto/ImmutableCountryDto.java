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
 * Immutable implementation of {@link CountryDto}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableCountryDto.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code ImmutableCountryDto.of()}.
 */
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@Generated({"Immutables.generator", "CountryDto"})
@Immutable
public final class ImmutableCountryDto implements CountryDto {
  private final String value;

  private ImmutableCountryDto(String value) {
    this.value = Preconditions.checkNotNull(value, "value");
  }

  private ImmutableCountryDto(ImmutableCountryDto original, String value) {
    this.value = value;
  }

  /**
   * @return The value of the {@code value} attribute
   */
  @Override
  public String value() {
    return value;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link CountryDto#value() value} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for value
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableCountryDto withValue(String value) {
    if (this.value.equals(value)) return this;
    String newValue = Preconditions.checkNotNull(value, "value");
    return validate(new ImmutableCountryDto(this, newValue));
  }

  /**
   * This instance is equal to all instances of {@code ImmutableCountryDto} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableCountryDto
        && equalTo((ImmutableCountryDto) another);
  }

  private boolean equalTo(ImmutableCountryDto another) {
    return value.equals(another.value);
  }

  /**
   * Computes a hash code from attributes: {@code value}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + value.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code CountryDto} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("CountryDto")
        .omitNullValues()
        .add("value", value)
        .toString();
  }

  /**
   * Construct a new immutable {@code CountryDto} instance.
   * @param value The value for the {@code value} attribute
   * @return An immutable CountryDto instance
   */
  public static ImmutableCountryDto of(String value) {
    return validate(new ImmutableCountryDto(value));
  }


  private static ImmutableCountryDto validate(ImmutableCountryDto instance) {
    instance.check();
    return instance;
  }

  /**
   * Creates an immutable copy of a {@link CountryDto} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable CountryDto instance
   */
  public static ImmutableCountryDto copyOf(CountryDto instance) {
    if (instance instanceof ImmutableCountryDto) {
      return (ImmutableCountryDto) instance;
    }
    return ImmutableCountryDto.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableCountryDto ImmutableCountryDto}.
   * @return A new ImmutableCountryDto builder
   */
  public static ImmutableCountryDto.Builder builder() {
    return new ImmutableCountryDto.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableCountryDto ImmutableCountryDto}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_VALUE = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String value;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code CountryDto} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(CountryDto instance) {
      Preconditions.checkNotNull(instance, "instance");
      value(instance.value());
      return this;
    }

    /**
     * Initializes the value for the {@link CountryDto#value() value} attribute.
     * @param value The value for value 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder value(String value) {
      this.value = Preconditions.checkNotNull(value, "value");
      initBits &= ~INIT_BIT_VALUE;
      return this;
    }

    /**
     * Builds a new {@link ImmutableCountryDto ImmutableCountryDto}.
     * @return An immutable instance of CountryDto
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableCountryDto build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return ImmutableCountryDto.validate(new ImmutableCountryDto(null, value));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = Lists.newArrayList();
      if ((initBits & INIT_BIT_VALUE) != 0) attributes.add("value");
      return "Cannot build CountryDto, some of required attributes are not set " + attributes;
    }
  }
}

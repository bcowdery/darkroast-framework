package com.darkroast.rythm.tags;

import org.rythmengine.template.JavaTagBase;

import java.util.Map;

/**
 * Base class for rendering HTML tags with optional attributes. Any parameter not
 * retrieved using either {@link #getByNameOrExpectedPosition(String, Object)} or
 * {@link #getByNameOrPosition(String, int, Object)} accessors can be printed to the
 * template output stream as a set of attribute pairs by invoking {@link #attributes()}.
 *
 * Example:
 * <pre>
 *     String name = getByNameOrExpectedPosition("name", "name");
 *     String value = getByNameOrExpectedPosition("value", "");
 *
 *     p("<input type=\"text\" name=\"").p(name).p("\" value=\"").p(value).p("\" ");
 *     attributes();
 *     p("/>");
 * </pre>
 *
 * For tag <code>@tag(name = "name", value = "some value", class="css-class")</code>, where the parameters
 * "name" and "value" are expected parameters and "class" will be dynamically rendered as an HTML attribute.
 *
 * @author Brian Cowdery
 * @since 05-06-2013
 */
public abstract class DynamicAttributeTag extends JavaTagBase {

    private int pos = 0;
    private Map<String, Object> parameterMap;

    private Map<String, Object> _parameterMap() {
        if (parameterMap == null) {
            parameterMap = _params.asMap();
        }
        return parameterMap;
    }

    /**
     * Attempts to return the parameter value by name. If a parameter is not found
     * with a matching name a lookup will be attempted using the position index.
     *
     * Each call to this method will increment the position index by 1 when a value
     * is found. Ensure that your tag implementation is fetching parameters in the order
     * they are expected to be provided in so that tags are compatible with both the
     * named and positional parameter formats.
     *
     * @param name parameter name
     * @param defaultValue default value of type T to return if parameter not found
     * @param <T> expected parameter value type
     * @return parameter if found, default value if not
     */
    @SuppressWarnings("unchecked")
    public <T> T getByNameOrExpectedPosition(String name, T defaultValue) {
        if (_parameterMap().containsKey(name)) {
            pos++;
            return (T) _parameterMap().remove(name);
        }

        if (_params.size() >= (pos + 1)) {
            pos++;
            return (T) _params.getByPosition(pos);
        }

        return defaultValue;
    }

    /**
     * Attempts to return the parameter value by name.
     *
     * Like {@link #getByNameOrExpectedPosition(String, Object)}, this method will increment the
     * position index by 1 if the parameter was found.
     *
     * @param name parameter name
     * @param <T> expected parameter value type
     * @return parameter if found, default value if not
     */
    @SuppressWarnings("unchecked")
    public <T> T getByName(String name) {
        if (_parameterMap().containsKey(name)) {
            pos++;
            return (T) _parameterMap().remove(name);
        }

        return null;
    }

    /**
     * Attempts to return the parameter value by name.
     *
     * Like {@link #getByNameOrExpectedPosition(String, Object)}, this method will increment the
     * position index by 1 if the parameter was found.
     *
     * @param name parameter name
     * @param <T> expected parameter value type
     * @return parameter if found, default value if not
     */
    @SuppressWarnings("unchecked")
    public <T> T getByName(String name, T defaultValue) {
        if (_parameterMap().containsKey(name)) {
            pos++;
            return (T) _parameterMap().remove(name);
        }

        return defaultValue;
    }

    /**
     * Attempts to return the parameter value by name or position. Useful for fetching
     * a parameter by either the expected parameter name, or the expected position when
     * the tag is invoked with positional parameters (e.g., <code>@a("/some/url")</code>
     * vs <code>@a(href = "/some/url")</code>).
     *
     * @param name parameter name
     * @param pos expected position
     * @param defaultValue default value of type T to return if parameter not found
     * @param <T> expected parameter value type
     * @return parameter if found, default value if not
     */
    @SuppressWarnings("unchecked")
    public <T> T getByNameOrPosition(String name, int pos, T defaultValue) {
        if (_parameterMap().containsKey(name)) {
            return (T) _parameterMap().remove(name);
        }

        return _params.size() >= (pos + 1) ? (T) _params.getByPosition(pos) : defaultValue;
    }

    /**
     * Print all remaining parameters as HTML attribute pairs.
     */
    public void attributes() {
        for (Map.Entry<String, Object> param : _parameterMap().entrySet()) {
            p(" ").p(param.getKey()).p("=").p("\"").p(param.getValue()).p("\"");
        }
    }
}

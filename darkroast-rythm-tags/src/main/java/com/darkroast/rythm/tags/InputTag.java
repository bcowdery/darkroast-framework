package com.darkroast.rythm.tags;

import com.darkroast.rythm.annotations.RythmTag;
import org.rythmengine.template.ITag;

/**
 * Tag for simple input fields
 *
 * Usage:
 * <pre>
 *     @input("person.name", @person.getName());
 *
 *     @// accepts additional parameters in named format
 *
 *     @input(name: "person.name", value: @person.getName(), class: "input-large")
 * </pre>
 *
 * The HTML input type can be changed by passing the optional "<code>type</code>" parameter
 * to the @input template. Useful for rendering various HTML5 input types!
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
@RythmTag
public class InputTag extends DynamicAttributeTag {

    public InputTag() {
    }

    @Override
    public String __getName() {
        return "input";
    }

    @Override
    protected void call(ITag.__ParameterList params, __Body body) {
        String name = getByNameOrExpectedPosition("name", "name");
        String value = getByNameOrExpectedPosition("value", "");
        String type = getByNameOrExpectedPosition("type", "text");

        p("<input type=\"").p(type).p("\" name=\"").p(name).p("\" value=\"").p(value).p("\" ");
        attributes();
        p("/>");
    }
}

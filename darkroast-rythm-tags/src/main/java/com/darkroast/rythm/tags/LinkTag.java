package com.darkroast.rythm.tags;

import com.darkroast.rythm.annotations.RythmTag;
import org.rythmengine.template.ITag;

/**
 * Tag for rendering <code><a></code> link anchor tags.
 *
 * Usage:
 * <pre>
 *     @a("/some/url", "A link!");
 *
 *     @// or with a body and some optional attributes
 *
 *     @a(href = "/some/url", class="highlight") {
 *          <span>A Link! and some HTML!</span>
 *     }
 * </pre>
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
@RythmTag
public class LinkTag extends DynamicAttributeTag {

    public LinkTag() {
    }

    @Override
    public String __getName() {
        return "a";
    }

    @Override
    protected void call(ITag.__ParameterList params, __Body body) {
        String url = getByNameOrExpectedPosition("href", "#");
        String value = getByName("value");

        p("<a href=\"").p(url).p("\"");
        attributes();
        p(">");

        if (value != null) p(value);
        if (body != null) _pTagBody(params, __getBuffer());

        p("<a/>");
    }
}

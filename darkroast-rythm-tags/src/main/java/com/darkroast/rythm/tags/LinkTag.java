package com.darkroast.rythm.tags;

import com.darkroast.rythm.annotations.RythmTag;
import org.rythmengine.template.ITag;
import org.rythmengine.template.JavaTagBase;

/**
 * LinkTag
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
@RythmTag
public class LinkTag extends JavaTagBase {

    public LinkTag() {
    }

    @Override
    public String __getName() {
        return "a";
    }

    @Override
    protected void call(ITag.__ParameterList params, __Body body) {
        Object o = params.getDefault();

        String value = params.getByName("value", "");
        String url = params.getByName("url", "");

        p("<a href=\"").p(url).p("\">").p(value).p("<a/>");
    }
}

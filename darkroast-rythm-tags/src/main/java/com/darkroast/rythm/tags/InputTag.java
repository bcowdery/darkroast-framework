package com.darkroast.rythm.tags;

import com.darkroast.rythm.annotations.RythmTag;
import org.rythmengine.template.ITag;
import org.rythmengine.template.JavaTagBase;

/**
 * InputTag
 *
 * @author Brian Cowdery
 * @since 04-06-2013
 */
@RythmTag
public class InputTag extends JavaTagBase {

    public InputTag() {
    }

    @Override
    public String __getName() {
        return "input";
    }

    @Override
    protected void call(ITag.__ParameterList params, __Body body) {
        Object o = params.getDefault();

        String name = o == null ? "name" : o.toString();
        String value = params.getByName("value", "");

        p("<input type=\"text\" ").p("name=\"").p(name).p("\" value=\"").p(value).p("\" />");
    }
}

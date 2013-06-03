package com.darkroast.rythm.tags;

import com.darkroast.rythm.annotations.RythmTag;
import org.rythmengine.template.ITag;
import org.rythmengine.template.JavaTagBase;

/**
 * Hello
 *
 * @author Brian Cowdery
 * @since 03-06-2013
 */
@RythmTag
public class Hello extends JavaTagBase {

    public Hello() {
    }

    @Override
    public String __getName() {
        return "hello";
    }

    @Override
    protected void call(ITag.__ParameterList params, __Body body) {
        Object o = params.getDefault();
        String name = o == null ? "who" : o.toString();
        p("Hello ").p(name);
    }
}

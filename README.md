DarkRoast
===================

DarkRoast is a ultra fast and super lightweight MVC web framework built on top of Java EE 6 CDI. Inspired by
Struts 2, Microsoft MVC and other easy to use frameworks, DarkRoast aims to be as simple as possible by building
on standard Java libraries and established technology we all know and love.


## CDI Extension

DarkRoast provides several convenient features for CDI applications, allowing easy access to servlet objects
and important life cycle events from within your application.

### Servlet Object Injection

Inject the <code>ServletContext</code> <code>ServletRequest</code> and <code>ServletResponse</code> into any
managed bean. DarkRoast even allows injection of the typed <code>HttpServletRequest</code> and <code>HttpServletResponse</code>
without requiring additional casting.

```java
@Named
public class HelloWorldService {

    @Inject ServletContext servletContext;

    @Inject HttpServletRequest request;
    @Inject HttpServletResponse response;

    public String say() {
        return "Hello World!";
    }
}
```

### Events

Observe important web application life cycle events in your application because why not.

* <code>ServletRequestEvent</code> - Servlet request start and stop events.
* <code>ServerContextEvent</code> - Web application servlet context startup and shutdown.
* <code>BootstrapEvent</code> - DarkRoast startup and shutdown events.

Qualify your observers using <code>@Initialized</code> and <code>@Destroyed</code> annotations to observe
start and stop events individually.


#### Bootstrap

Bootstrap your own application startup after DarkRoast has done it's thing. Observe the events you need to get
your application up and running with minimal effort and without cryptic DSL's and convoluted configuration.

```java
@Startup
@ApplicationScoped
public class ApplicationBootstrap {

    private static final Logger LOG = Logger.getLogger(ApplicationBootstrap.class.getName());

    protected void applicationStartup(@Observes @Initialized BootstrapEvent event) {
        LOG.info("Application is starting up!");
    }

    protected void applicationShutdown(@Observes @Destroyed BootstrapEvent event) {
        LOG.info("Application is shutting down");
    }
}
```

## MVC

### Controllers

DarkRoast is all about the beans. Take any POJO and annotate it with a <code>@Path</code> to turn it into a RequestScoped
MVC controller. Action names are inferred from the URL, or can be explicitly mapped with another <code>@Path</code>.

__Controller:__
```java
import com.darkroast.mvc.annotations.Path;
import com.darkroast.mvc.Controller;
import com.darkroast.mvc.results.Result;

import static com.darkroast.mvc.results.Results.*;

@Path("rythm")
public class HelloWorldController implements Controller {

    @Path("index")
    public Result index() {
        return view("index.html").add("what", "Rythm");
    }
}
```

__index.html:__
```html
<html>
<head>
    <title>Hello World!</title>
</head>
<body>
    @args String what

    <p>
        Got @what?
    </p>
</body>
</html>
```

Views are rendered using the [Rythm Template Engine](http://rythmengine.org/). Inspired by Microsoft's Razor and used
by other frameworks like [Play!](http://www.playframework.com/), Rythm is easy to use, easy to extend and blazing
fast.

## View Models

### Passing a Model to the View

View models can be passed to the view layer via the action <code>Result</code> object. Any result type that renders
content will accept model parameters for rendering data back to the user. These results are also chainable, making it
simple to add many model objects to the result.

```java
@Path("index")
public Result index() {
    return view("index.html")
        .add("who", "Chuck Norris")
        .add("what", "a unicycle")
        .add("why", "his beard");
}
```

Declare the <code>@args</code> in your Rythm template.

```html
@args String who
@args String what
@args String why

<p>
    @who, @what and @why.
</p>
```

### Data Binding

Databinding is handled through a CDI extension that dynamically produces instances of objects for injectable fields
annotated with <code>@ViewModel</code>, and attempts to populate them using request parameters.

Model view beans

* A model bean must have a public default constructor
* A model bean must follow JavaBean property naming conventions
* Request parameters names must match the JavaBean property name as accessible from the Controller.

__Model:__
```java
public class Person {
    private String name;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

__Controller:__
```java
import com.darkroast.mvc.annotations.Path;
import com.darkroast.mvc.annotations.ViewModel;
import com.darkroast.mvc.Controller;
import com.darkroast.mvc.results.Result;

import static com.darkroast.mvc.results.Results.*;

@Path("person")
public class PersonController implements Controller {

    @Inject @ViewModel Person person;

    @Path("index")
    public Result index() {
        return view("index.html").add("person", person);
    }

    @Path("hello")
    public Result hello() {
        System.out.println("Hello " + person.getName());
        return view("hello.html").add("person", person);
    }
}
```

__View:__
```
@args Person person

@if (person.getName() != null) {
   Hello @person.getName()!
}

<form action="/person/hello">
    <input type="text" name="person.name" value="@person.name"/>
</form>
```




## Custom Rythm Tags

Using Rythm you can easily create specific tags for your application. This is because **Every template can be invoked
as a tag**. Templates are resolved from the template root (<code>/WEB-INF/content</code> by default), and mapped into a tag name
by stripping off the file extension and converting path separators <code>/</code> into dots <code>.</code>.


For example suppose you have a template with the following content located at <code>/WEB-INF/util/hello.html</code>:

```
Hello from tag!
```

From the any other template or even the template itself you can invoke the template as a tag:

```
@util.hello()
```


### Java Tag Interface

Rythm is so fast that there is very little performance benefit to writing a Java tag that produces output with
StringBuilder or String concatenation.

However, Java tags are still very useful for third party developers wanting to package tags in jar files. DarkRoast provides
a convenient mechanism for discovering and registering tags as managed beans through the use of the <code>@RythmTag</code>
annotation.

```java
import com.darkroast.rythm.annotations.RythmTag;
import org.rythmengine.template.ITag;
import org.rythmengine.template.JavaTagBase;

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
```

```html
<body>
    <p>
        @hello("Darkroast")
    </p>
</body>
```

### Inline Tags

TODO: Write this later... Reference https://github.com/greenlaw110/play-rythm/blob/master/documentation/manual/user_guide.textile

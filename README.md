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

_Controller:_
```java
import com.darkroast.annotations.Path;
import com.darkroast.mvc.Controller;
import com.darkroast.mvc.results.Result;

import static com.darkroast.mvc.results.Results.*;

@Path("rythm")
public class HelloWorldController implements Controller {

    @Path("index")
    public Result index() {
        return view("index.html".add("what", "Rythm");
    }
}
```

_index.html:_
```html
<html>
<head>
    <title>Hello World!</title>
</head>
<body>
    <p>
        Got @what?
    </p>
</body>
</html>
```

Views are rendered using the [Rythm Template Engine](http://rythmengine.org/). Inspired by Microsoft's Razor and used
by other frameworks like [Play!](http://www.playframework.com/), Rythm is easy to use, easy to extend and blazing
fast.




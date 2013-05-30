DarkRoast
===================

Dark Roast is a super lightweight MVC web framework built on top of Java EE 6 CDI.



### Controllers

Everything is a Bean with Dark Roast, including your controllers! Take any POJO and annotate it with a <code>@Path</code> to turn it into an MVC controller. Action names are infered from the URL, or can be explicitly mapped with another <code>@Path</code>.


_Controller:_
```java
import com.darkroast.mvc.Controller;
import com.darkroast.mvc.annotations.Path;
import com.darkroast.mvc.results.Result;

import static com.darkroast.mvc.results.Results.*;

@Path("darkroast")
public class HelloWorld implements Controller {

   @Path("index")
   public Result index() {
       return view("index.html").model("what", "Rythm");
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


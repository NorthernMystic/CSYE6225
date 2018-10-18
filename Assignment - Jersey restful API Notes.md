Assignment1 - Jersey restful API Notes

1. Configuration

new maven project

choose Jersey-quickstart-webapp



fix errors:

JSP problems:

https://stackoverflow.com/questions/22756153/the-superclass-javax-servlet-http-httpservlet-was-not-found-on-the-java-build



Maven Java EE Configuration Problem
JAX-RS (REST Web Services) 2.1 requires Java 1.8 or newer.	Assignment1	
JAX-RS (REST Web Services) 2.1 can not be installed : One or more constraints have not been satisfied.	Assignment1	

https://blog.csdn.net/join_huang/article/details/72871469



old configuration

```
    <build>
        <finalName>Assignment1</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>2.5.1</version>
                <inherited>true</inherited>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

change 1.7 to 1.10, problem fixed	//since the JRE version i use on my laptop is 10

eclipse generate getters and setters

Source -> Generate Getters and Setters



2. Fix bug

Write blabla

For each entity/data model, we will have a service and a resource







------

2.1 when run as a server

Error: the tomcat server configuration is missing

delete the server, create a new one (remember to add the application to the server)



java.lang.NoClassDefFoundError: javax/xml/bind/Unmarshaller

java.lang.NoClassDefFoundError: javax/activation/DataSource

Add:

```
        <dependency>
		    <groupId>javax.xml.bind</groupId>
		    <artifactId>jaxb-api</artifactId>
		    <version>2.1</version>
		</dependency>
```



localhost:8080/<artifactName>/<urlpattern in web.xml>/<path>

e.g: http://localhost:8080/Assignment1/webapi/myresource







------

2.2 The home page is fine

## Jersey RESTful Web Application!

[Jersey resource](http://localhost:8080/Assignment1/webapi/myresource)

Visit [Project Jersey website](http://jersey.java.net/) for more information on Jersey!



but the when click Jersey resource link

# HTTP Status 404 – Not Found

------

**Type** Status Report

**Message** Not Found

**Description** The origin server did not find a current representation for the target resource or is not willing to disclose that one exists.

------

### Apache Tomcat/9.0.12



Spend a lot of time but still not know how to solve it,

I build a totally new project in a new workspace,then I am able to find the webapi/myresource

I guess it is because i add the java file into the resource folder directly, it should be generated automaticly when I write java file in src folder, but now sure if it is the true reason. But build a new project seems always  a good solution.





------

2.3 but when visit the webapi/professors

Get the error 500 with the cause

</pre><p><b>Root Cause</b></p><pre>java.lang.IllegalStateException: java.io.IOException: java.lang.reflect.InvocationTargetException

check the website:

https://stackoverflow.com/questions/6020719/what-could-cause-java-lang-reflect-invocationtargetexception

Still do not know how to solve it,

but no InvocationTargetException after restart the server, not sure what happended

Then get the new error message:

#######SEVERE: MessageBodyWriter not found for media type=application/json, type=class java.util.ArrayList,genericType=java.util.List<csye.Assignment.student_info_system.datamodel.Professor>.

after enable the JSON support in pom.xml, everything works fine.



2.4

how to test in postman:

use raw /JSON if we want to test POST

```
1.
{
    "firstName":"Evan",
    "professorId":0,
    "program":"CSYE"	
}

to test the method 
public Professor addProfessor(Professor prof) {
	return profService.addProfessor(prof);
}

since the constructor of Professor is 	
public Professor(long professorId, String firstName, String program) {
		this.professorId = professorId;
		this.firstName = firstName;
		this.program = program;
}


2.when we want to test nested json
{
	"profid": 1,
	"Professors": {
		"firstName":"Lily",
		"professorId":1,
		"program":"CSYE"	
	}
}


3. another case 
	@PUT
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor updateProfessor(@PathParam("professorId") long profId, 
			Professor prof) {
		return profService.updateProfessorInformation(profId, prof);
	}
	
for this case, we will still test using
{
	"firstName": "Lily",
	"professorId": 0,
	"program": "CSYE"
}

but the url is http://localhost:8080/student-info-system/webapi/professors/0
since the long porfId is come from the @PathParam("professorId") which 0 for this case
```



2.5

Do not know how to test the studetn method

```
public Student(String firstName, String lastName, Long id, Image image, List<String> enrolledClass) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.image = image;
		this.enrolledClass = enrolledClass;
}
```

https://stackoverflow.com/questions/39660074/post-image-data-using-postman

```
try:
{
	"firstName": "Stevens",
	"lastName": "Xi",
	"id": 1,
	"mage": null,
	"enrolledClass": []
}

works
```

At first I get an error likes :Unexpected char 59 at (line no=4, column no=9, offset=54)

It is because that I have a typo "id": 1; which should be "id": 1,



2.6 deploy the application on AWS elastic beanstalk

https://www.youtube.com/watch?v=UcfBSW1fXOM

start from 9 minutes



Error 500:

java.lang.UnsupportedClassVersionError: csye/Assignment/student_info_system/MyResource has been compiled by a more recent version of the Java Runtime (class file version 54.0), this version of the Java Runtime only recognizes class file versions up to 52.0 (unable to load class [csye.Assignment.student_info_system.MyResource])



Seems the default Java version that Elastic beanstalk use is Java 8

Should use Java 8 at local at first LOL



Try - right click project - build path - add Library - JRE - add excuction environment java se 1.8

Seem works. 
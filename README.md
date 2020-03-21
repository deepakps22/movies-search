# movies-search
A Spring Boot application that allows to add and search movie names

### Running Application
- Download the zip or clone the Git repository.
- Unzip the zip file (if you have downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse 
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the right project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application


Here are some endpoints you can call:

### Get information about movies 

```
GET http://localhost:8080/movies/

RESPONSE: 
{
    "movieList": [
        {
            "movie": "the exorcist"
        },
        {
            "movie": "the dark knight"
        }
    ]
}
```

### Create a movie resource

```
POST http://localhost:8080/movies/
Accept: application/json
Content-Type: application/json

REQUEST:
{
"movie" : "Life is beautiful"
}

RESPONSE: HTTP 201 (Created)

```

### Search movies that begin with specific prefix

```
POST http://localhost:8080/movies/search/the/2


Response: HTTP 200

[
    "the dark knight",
    "the exorcist"
]

Content: paginated list 
```


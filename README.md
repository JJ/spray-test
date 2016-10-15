## ejemplo de uso de _spray_ Template Project

Este proyecto usa Scala 2.11 + Akka 2.3 + spray 1.3


Follow these steps to get started:

1. Git-clone this repository.

        $ git clone git://github.com/JJ/spray-test my-project

2. Change directory into your clone:

        $ cd my-proyect

3. Launch SBT:

        $ sbt

4. Compile everything and run all tests:

        > test

5. Start the application:

        > re-start

6. Browse to [http://localhost:8080](http://localhost:8080/)


8. Ejecuta una serie de pruebas:

```
$ curl http://localhost:8080
["routes", "get,post"]

$ curl -X PUT http://localhost:8080/0/0/Uno 
{
  "local": 0,
  "visitante": 0,
  "quien": "Uno"
}

$ curl -X PUT http://localhost:8080/0/1/Otro
{
  "local": 0,
  "visitante": 1,
  "quien": "Otro"
}                                                                              

$ curl -X PUT http://localhost:8080/3/1/Aquel
{
  "local": 3,
  "visitante": 1,
  "quien": "Aquel"
}                                                                              

$ curl http://localhost:8080/Aquel     
{
  "local": 3,
  "visitante": 1,
  "quien": "Aquel"
}
```

7. Stop the application:

        > re-stop

8. Learn more at http://www.spray.io/

9. Start hacking on `src/main/scala/com/example/MyService.scala`

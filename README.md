# databitcoin

ejercicio para demostrar la utilización del api de Streams (java.util.stream), exponiendo un microservicio implementado en Spring Boot

Se hadesarrollado de tal manera que la mayor parte de las clases sean reutilizables para implementar servicios similares sobre los tipos de cambios de otras monedas.

Queda pendiente mejoras en la validación de parametros de entrada e implentar mejor las respuestas REST en casos de errores

### Intruccione para ejecutar
primero de debe compilar contruir la aplicacion ejecutando el siguiente comando de maven:
```
mvn clean install
```

una vez generado el archivo jar se puede ejecutar con el siguiente comando:

java -jar ./target/databitcoin-0.0.1-SNAPSHOT.jar
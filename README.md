# BibliotecaSolidGoF

Sistema de biblioteca en Java que demuestra principios SOLID, patrones de diseño GoF y patrones GRASP.  
Permite registrar materiales y usuarios, crear préstamos, verificar vencimientos, calcular multas y generar reportes en texto y CSV.

## Requisitos

- Java 21 instalado (`java -version`)
- Maven 3.x instalado (`mvn -version`)

## Cómo clonar el proyecto

git clone https://github.com/Pacha1005/BibliotecaSolidGoF-.git
cd BibliotecaSolidGoF-

(O descarga el ZIP desde GitHub y descomprímelo.)

## Cómo ejecutar las pruebas

Desde la carpeta donde está `pom.xml`:

mvn clean test

Esto compila el proyecto y ejecuta todos los tests unitarios.

## Cómo ejecutar el programa

mvn clean compile
mvn exec:java -Dexec.mainClass="edu.pe.biblioteca.BibliotecaSolidGoF"

El programa mostrará en consola el flujo completo del sistema:
- Registro de materiales y usuarios
- Creación de préstamos
- Verificación de préstamos vencidos y notificaciones
- Devolución de materiales y cálculo de multas
- Generación de reportes en texto y CSV

## Estructura del proyecto

- `src/main/java/edu/pe/biblioteca/domain`: Entidades de dominio (Usuario, Prestamo, MaterialBibliotecario, Libro, Revista).
- `src/main/java/edu/pe/biblioteca/service`: Fachada `BibliotecaService` y clases de reportes.
- `src/main/java/edu/pe/biblioteca/strategy`: Estrategias de multa y observadores (Strategy, Observer).
- `src/main/java/edu/pe/biblioteca/factory`: Fábrica de materiales (Factory Method).
- `src/main/java/edu/pe/biblioteca/adapter`: Importadores de datos (Adapter).
- `src/test/java`: Pruebas unitarias con JUnit 5 y Mockito.

## Autor

Juan Diego Montoya (Pacha1005)

# meli
Repositorio con el codigo fuente del Challenge MercadoLibre


## Cómo comenzar

Estas instrucciones le permitirán obtener una copia del proyecto en su máquina local para fines de desarrollo y prueba. clone o descargue este repositorio en su máquina local.

### Prerequisitos

Java jdk 8

## IDE
1. Intellij IDEA

# Ejecutando
#### Componentes

1. Mutant service

Siga los siguientes pasos.

2. ejecute el siguiente comando desde el directorio raíz del proyecto (donde se encuentra pom.xml) o en la terminal del IDE del proyecto para hacer una primera construcción.

` -mvn clean install `

una vez creados los archivos ejecutables en la carpeta target, puede comprobar cómo funciona

### Commando

` java -jar [name component].jar `

3. Individual Swagger URL del servicio :

- http://localhost:8080/swagger-ui/index.html#/

### prueba del servicio

- Utilizando CURL

` curl --location --request POST 'http://localhost:8080/api/v1/mutants/mutant' \
--header 'Content-Type: application/json' \
--data-raw '{
"dna":["ATGCGA","CAGTAC","ATTTTT","AGTAGG","ACCCCA","ACTTTG"]
}' `

` curl --location --request GET 'http://localhost:8080/api/v1/mutants/stats' `

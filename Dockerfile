# Usa a imagem oficial do Java (Eclipse Temurin) versão 21 em Alpine (leve)
# https://hub.docker.com/_/eclipse-temurin
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia TODOS os arquivos do projeto para dentro do container
COPY . ./

# Dá permissão de execução ao Maven Wrapper
RUN chmod +x mvnw

# Faz o build do projeto com Maven
# -DskipTests → ignora testes para acelerar
# dependency:list → gera lista de dependências (opcional)
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

# Comando para iniciar a aplicação Quarkus
# Executa o JAR gerado dentro da pasta target/quarkus-app
CMD ["sh", "-c", "java -jar target/quarkus-app/quarkus-run.jar"]
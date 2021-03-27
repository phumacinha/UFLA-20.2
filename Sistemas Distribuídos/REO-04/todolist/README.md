# Execução da API To Do List
Para executar a API é necessário ter acesso à um banco de dados MySQL e instalar o gerenciador de pacotes Maven.

## Banco de dados MySQL
Para executar a API é necessário ter acesso à um banco de dados MySQL.
<br>
No arquivo `src/main/resources/application.properties`, deve-se alterar os valores `<database-address>`, `<database-port>`, `<user>` e `<password>`. Por exemplo, caso seu banco de dados seja esteja rodando em `localhost` na porta `3306` e seu usuário seja `root` com senha `strongpassword`, o conteúdo deve ser alterado para:
```properties
spring.datasource.url = jdbc:mysql://localhost:3306/todolist?createDatabaseIfNotExist=true
spring.datasource.username = root
spring.datasource.password = strongpassword
```

## Gerenciador de dependências Maven
O gerenciador de dependências Maven é responsável por baixar os pacotes necessários para execução da API. No Ubuntu, execute os seguintes comandos para instalar o Maven:
1. Primeiramente, atualize a indexação de pacotes:
```console
$ sudo apt update
```
2. Depois, instale o Maven com o seguinte comando:
```console
$ sudo apt install maven
```
3. Verifique a instalação rodando o comando:
```console
$ mvn -version
```
4. A saída deve-se parecer com isso:
```console
Apache Maven 3.6.3
Maven home: /usr/share/maven
Java version: 11.0.10, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
Default locale: pt_BR, platform encoding: UTF-8
OS name: "linux", version: "5.8.0-48-generic", arch: "amd64", family: "unix"
```

## Build and run
Por fim, para executar a API acesse o diretório do projeto através do terminal e execute o comando
```console
$ mvn spring-boot:run
```
Após baixar todas as dependências, a API está pronta para ser utilizada.
<br><br>
As requisições devem ser feitas para o endpoint:
```
http://localhost:8080/todolist/1.0.0
```

### [Verifique a documentação da API clicando aqui.](https://documenter.getpostman.com/view/12444955/TzCJg9yR)
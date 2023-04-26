# Projeto

## Definição
A empresa "Agenda ada" esta lançando um novo aplicativo para o mercado. Esse aplicativo é uma agenda para o controle de atividade de seus usuários.
Nessa agenda teremos a possibilidade de cadastrar atividades a serem executadas e destinar essas atividades à um usuário em especifico. E, essa atividade cadastrada, deve ser concluída e manter a data de encerramento.  
Para termos um lançamos tranquilo no mercado vamos automatizar os testes, visando um aumento de qualidade. Para essa automação devemos utilizar Selenium na parte de páginas da web e Cucumber + RestAssured no parte da API.

## Regras

### Usuário
Nas páginas Web teremos o cadastro/lista de usuários na url http://localhost:8080/app/users Onde é possível criar, listar e atualizar os usuário.
- Não deve ser possível cadastrar dois usuário com o mesmo username.
- Não deve ser permitido alterar o username do usuário após o cadastro.
- O cadastro de usuário deve exigir name, username e password para que seja realizado.
- A listagem deve exibir todos os usuários já cadastrados, sem nenhum tipo de filtro.
- Na listagem deve ser possível ver o name, username e ter acesso a página de edição.
- A senha antiga não deve ser carregada na edição do usuário, para evitar que seja exposta.
- É permitido atualizar name e password do usuário.
- Não deve ser possível apagar usuário já cadastrados.

### Tarefas
A parte de tarefas ainda não temos uma tela, teremos apenas uma API no endereço http://localhost:8080/api/tasks Onde é possível criar, listar e atualizar as task.   
- Todas as tarefas recém cadastradas devem ter o status igual a OPEN.
- Não deve ser possível cadastrar uma tarefa com status igual a CLOSE.
- A tarefa deve estar vinculada a um usuário. Esse vinculo acontece no payload de criação.
- Toda tarefa cadastrada deve ter a informação de title preenchida, sendo essa uma informação obrigatória.
- A descrição da tarefa é opcional e pode ser preenchida em um segundo momento utilizando o verbo PUT.
- O usuário, a qual destina-se a atividade, também pode ser alterado.
- Após a conclusão de uma tarefa, essa pode ser encerrada chamando o PUT da API e trocando o status para CLOSE.
- Ao encerrar uma terafa deve ser registrado a data em `closedAt`.
- Nenhuma tarefa encerrada pode sofrer qualquer alteração.
- Não deve ser possível apagar uma atividade já cadastrada. Permanecerá para histórico.
  
Exemplo de payload para criar e atualizar a tarefa:
```json
{
    "title": "Aula toda segunda",
    "description": "Segunda fire!!!!",
    "user": {
        "id": 1,
        "name": "William"
    },
    "status": "OPEN"
}
```
A documentação da API pode ser encontrada em http://localhost:8080/swagger-ui/index.html#/

## Detalhes técnicos do projeto

### Consutrção
Todo o projeto foi construído com base no Spring Boot + Maven.  
Feito uso de uma base de dados H2 para que não seja necessário nenhum tipo de instalação/execução. Porém, ao utilizar esse tipo de banco de dados a cada vez que o aplicativo é encerrado o banco é apagado.  
Também foi utilizada uma abordagem de API Rest para a manipulação de tarefas. Essa API possibilita a criação de tarefas pelo verbo POST, a edição pelo VERBO PUT e a consulta pelo verbo GET.

### Execução
Para a execução do projeto é possível utilizando o Maven ou pelo IntelliJ.

#### Execução pelo Maven
##### Pré-requisito
Ter o maven instalado na máquina. Para verificar se esta instalado execute o comando: `mvn --v`  
Se o comando executar com sucesso você terá uma saída parecida com:
```
william@Williams-MacBook-Pro ~ % mvn --v
Apache Maven 3.9.0 (9b58d2bad23a66be161c4664ef21ce219c2c8584)
Maven home: /Users/william/apps/apache-maven-3.9.0
Java version: 17.0.3.1, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-17.0.3.1.jdk/Contents/Home
Default locale: en_BR, platform encoding: UTF-8
OS name: "mac os x", version: "13.2.1", arch: "aarch64", family: "mac"
```

##### Execução
É possível executar o comand `mvn spring-boot:run` dentro da pasta do projeto. Precisa ser onde esta o arquivo pom.xml



#### Execução pelo IntelliJ
##### Pré-requisito
Ter o IntelliJ instalado.

##### Execução
Importar o projeto para o IntelliJ. É importante que o projeto seja importado como projeto maven (já demonstrado como fazer). Após a importação com sucesso é possível executar a partir da classe `ScheduleApplication`
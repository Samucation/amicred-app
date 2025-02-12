# **Manual de integração da aplicação AMICRED** 

## **Empresa: EXOSOFT**

A aplicação está previamente configurada para rodar localmente como também via núvem e docker, para cada
ambiente será necessário o manuseio e criação de algumas variáveis de ambiente, podendo ser que essas variáveis
sejam criadas no próprio intellij "Em contextos aonde você está rodando local e sem dockerizar a aplicão
( contexto de desenvolvimento ) ou setando as enviroments diretamente no sistema operacional ou cloud que a aplicação
será executada. ( Modo de produção ) Abaixo existe a aplicação para rodar em todos os ambientes."

- ### **Rodando a aplicação inteira via docker:** ###

- Caso queira rodar a aplicação apenas para testes é possível rodar ele totalmente via docker, ou seja o banco de dados e a aplicação estarão dentro
- de um container docker, nesses casos você precisará de poucos passos para rodar a aplicação.
- - Com o docker instalado na sua máquina windows ou linux rodar o seguinte comando na raiz do projeto:
- - ```docker composer up ``` Se tudo ser certo a aplicação será configurada automáticamente pelo docker:
- - Acesse a url **http://localhost:8083/swagger-ui/index.html#/user-controller/isServerLive** para saber se a aplicação está rodando.


- ### **Usar a aplicação localmente para desenvolvimento sem docker:** ###

- Será necessário criar um banco de dados relacional do tipo PosgresSQL
- Para isso siga os seguintes comandos: 

- **Criar o banco de dados via docker no cmd ou terminal executar**
```
docker run -d -e POSTGRES_DB=amicred_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=amicred -p 5432:5432 --name postgres-amicred postgres
```


- **Inserindo variáveis de ambiente, definindo o ambiente local de desenvolvimento**
- A aplicação sempre irá procurar no seu arquivo application.class ( **AmicredApplication** ) qual é o ambiente
- setado nas variáveis de ambiente, para rodar localmente no **intellij** vá em:
- - Edit Configurations
- - New configuration 
- - Escolha a opção do menu esquerdo no simbolo de + para adicionar uma aplicação.
- - Especifique o **JAVA 22** para a aplicação.
- - E em seguida coloque o pacote da aplicação (**com.exosoft.amicred.AmicredApplication**) O Intellij busca autoático se clicar no botão.
- - Em variáveis de ambiente coloque ( **APPLICATION_ENVIRONMENT=local;ENV_PATH=env;ENV_FILE=local** ) sem os parenteses.
- - Em **Work Directory** tenha certeza que está no diretório da aplicação clonada.
- - Clique para executar a aplicação em modo ( **Debug** ) ou modo sem debug.
- - Acesse a url **http://localhost:8083/swagger-ui/index.html#/user-controller/isServerLive** para saber se a aplicação está rodando.






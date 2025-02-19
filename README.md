# **Manual de integração da aplicação AMICRED** 

## **Empresa: EXOSOFT**

A aplicação está previamente configurada para rodar localmente como também via núvem e docker, para cada
ambiente será necessário o manuseio e criação de algumas variáveis de ambiente, podendo ser que essas variáveis
sejam criadas no próprio intellij "Em contextos aonde você está rodando local e sem dockerizar a aplicão
( contexto de desenvolvimento ) ou setando as enviroments diretamente no sistema operacional ou cloud que a aplicação
será executada. ( Modo de produção ) Abaixo existe a explicação de como rodar em todos os ambientes."

### **01 - Rodando a aplicação inteira via docker:** ###

- Caso queira rodar a aplicação apenas para testes é possível rodar ele totalmente via docker, ou seja o banco de dados e a aplicação estarão dentro
- de um container docker, nesses casos você precisará de poucos passos para rodar a aplicação.
- - Com o docker instalado na sua máquina windows ou linux rodar o seguinte comando na raiz do projeto:
- - ```docker composer up ``` Se tudo ser certo a aplicação será configurada automáticamente pelo docker:
- - Acesse a url **http://localhost:8080/swagger-ui/index.html#/user-controller/isServerLive** para saber se a aplicação está rodando.

### **02 - Usar a aplicação localmente para desenvolvimento sem docker:** ###

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
- - Em variáveis de ambiente coloque ( <span style="color: red;">**APPLICATION_ENVIRONMENT=local;ENV_PATH=env;ENV_FILE=local** </span>) sem os parenteses.
- - Em **Work Directory** tenha certeza que está no diretório da aplicação clonada.
- - Clique para executar a aplicação em modo ( **Debug** ) ou modo sem debug.
- - Acesse a url **http://localhost:8083/swagger-ui/index.html#/user-controller/isServerLive** para saber se a aplicação está rodando.

### **03 - Dicas de desenvolvimento (Comandos importantes)** ###
- - Rodar migrations: Na raiz do projeto executar via cmd ou terminal o comando ```mvn flyway:migrate```
- - Resetar migrações ou reparar migrações do banco de dados, via cmd ou terminal rodar o comando: ```mvn flyway:repair```
- - Executar a instalação de dependencias do projeto dando skip dos testes, via cmd ou terminal rodar o comando: ``` mvn clean install -DskipTests```
- - Recriar a imagem docker da aplicação, caso esteja rodando a aplicação no modo 01 ```docker compose up --build``` ou por qualquer outro motivo.

### **04 - Acessando e configurando o keycloak para funcionar a aplicação** ###
- - Para acessar o keycloak use o url local http://localhost:8081 
- - Para acessar o portal administrador use as credencias: 
- - - Usuário: **admin**
- - - Senha: **admin**
- - - Crie um novo reaml, clicando logo abaixo do nome keycloak no canto superior esquerdo clique o droopdown e em seguida no botão <strong style="background-color: darkblue; color: #f0f0f0">Create Realm</strong>
- - - de o nome de <strong>amicred-realm</strong>
- - - Vá até Clients e crie um client e denome <strong>amicred-client-ext</strong>
- - - Em <strong>Valid redirect URIs</strong> adicione as seguintes urls para que o keycloak tenha acesso a essas urls, sem isso ele não vai reconhecer a aplicação:
- - - http://localhost:8083/secure-data-1
- - - http://localhost:8083/secure-data-2
- - - http://localhost:8083/swagger-ui/index.html
- - - Aperte save e deixe todo o resto com a configuração padrão.
- - - Vá agora em Users do amicred-realm e clique em <strong>Add User</strong> para acionar um novo usuário, adicione usuário e senha.

### **05 - Recuperando o token do keycloak para ter acesso aos endpoints da aplicação:**
- - Para ter acesso aos endpoints da aplicação, será necessário antes recuperar o bearer token, então tenha certeza que o seu keycloak está rodando e configurado com o amicred-realm e o client amicred-client-ext
- - No meu caso eu criei um usuário de nome <strong>samucation</strong> com senha: <strong>123Mudar</strong> mas o código abaixo precisará da sua alteração nesses parametros caso você tenha mudado o nome do usuário ou senha ou do realm ou client.
- - Use o CURL abaixo com as devidas alterações para recuperar o bearer token:
```
curl --location 'http://localhost:8081/realms/amicred-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=amicred-client-ext' \
--data-urlencode 'client_secret=admin' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=samucation' \
--data-urlencode 'password=123Mudar' \
--data-urlencode 'scope=openid offline_access profile'
```
Troque os valores do CURL em:
- - data-urlencode username= <strong style="color:darkred">samucation</strong>
- - data-urlencode username=samucation'> data-urlencode 'password= <strong style="color:darkred">123Mudar </strong>
- - Por um valores válidos para você de usuário e senha que voce criou dentro do keycloak no seu realm.

### **06 - Autenticando via keycloak** ###
- - Após passar pelo passo 04 e 05 e obter o token válido do keycloak, use esse token para desbloquear o cadeado do swagger.
- - Para isso acesse a url a seguir e em seguida coloque o token no cadeado a direita.
- Com o token em mãos, acesse a uri do swagger: http://localhost:8083/swagger-ui/index.html
- - No cadeado insira o valor recuperado em code e aperte autorize para liberar os endpoints bloqueados que só tem acesso via token.
- - agora acesse os endpoints, caso o token expire, peça um novo token via CURL do passo 05.

## **Keycloak notas bonus, Novos endpoins precisarão de configuração**
- - Caso queira incluir novos endpoints será necessário na sessão client do Keycloak adicionar essas novas URLS, caso contrário o keycloak não terá acesso as urls e o sistema não funcionará.


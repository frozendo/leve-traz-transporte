Projeto Transportadora - Entregando Mercadorias
=============

**Tecnologias utilizadas:**
* Java 7
* MySql
* Maven
* Spring Boot
* Hibernate

**Spring Boot**
O Spring Boot foi escolhido pois fornece uma ótima estrutura para criações de micro serviços, sendo possível executar o projeto com poucas configurações, proporcionando uma forma rápida e simples de desenvolvimento. O proprio Spring Boot fornece um servidor embutido (Tomcat), o que facilitou o desenvolvimento do Webservice, pois não foi necessário a utilização de containers. Por fazer parte do projeto Spring Framework, ele também oferece IoC e Injeção de Dependencias, também utilizadas na solução.

**Hibernate**
O Hibernate foi utilizado para facilitar o mapeamento entre as entidades Java e as tabelas do banco, fornecendo uma solução independente do tipo de bancos utilizado. Com ele, é possível alterar o fornecedor do banco de dados - de Mysql para Oracle por exemplo - alterando apenas algumas configurações do projeto.

**Maven**
O Maven foi utilizado para resolver as dependencias do projeto e realizar o empacotamento da solução, gerando um jar para ser executado.

** Arquitetura **
Para o projeto utilizei a solução em camadas, separando o processo da seguinte forma: 
 - Controller: responsável por receber as requisições e enviá-las para processamento.
 - Service: responsável pelo processamento dos dados
 - DAO: responsável pelas chamadas ao banco.

Obs.: para integrar os services de Rotas e Mapas, foi criado um Facade, que realiza o processo utilizando os services de cada entidade para obter as informações necessárias.

Controller -> Facade -> Service -> DAO -> Hibernate -> MySql

**Executando a aplicação**

Para executar a aplicação, execute o comando maven abaixo em cima do diretório onde os fontes foram baixados.
```
mvn package
```

Após a execução do maven, o arquivo .jar estará pronto para execução. Na mesma janela do promp, execute o comando: 
```
java -jar target\mapa-melhor-rota-0.0.1-SNAPSHOT.jar
```
Nesse momento o Spring Boot irá iniciar o servidor embarcado e o serviço estará funcionando.

O projeto fornece dois serviços: novoMapa e melhorRotaMapa. 
Ambos são obtidos através de requisições do tipo POST, recebendo um json como parametro.

**Cadastrando um novo mapa**
Para cadastrar um novo mapa, o sistema espera um Json que com as seguintes propriedades: 

- nome = nome do mapa que será cadastrado
- rotas = rotas de destino do mapa
	- origem: ponto inicial da rota
	- destino: ponto final da rota
	- distancia: distancia entre os dois pontos

Obs.: o parametro rota deve ser passado no formado de array. Assim, várias rotas podem ser cadastradas para o mesmo mapa.
	
Segue um exemplo de um Json valido: 
```
{"nome":"Mapa SJRP - A para B","rotas":[{"origem":"A","destino":"B","distancia":5}]}
```
A resposta é uma string com o resultado da execução: se o mapa foi cadastrado, apresenta uma mensagem de sucesso. Se algum erro ocorrer, apresenta mensagem informando qual erro ocorreu.

**Consultando melhor rota**
Para consultar a melhor rota para um mapa, o sistema espera um Json que com as seguintes propriedades:

- nomeMapa = nome do mapa pesquisado
- origem: ponto inicial da rota
- destino: ponto final da rota
- autonomiaVeiculo: autonomia do caminhão (km/l)
- valorCombustivel: valor do litro do combustivel

Segue um exemplo de um Json valido: 
```
{"nomeMapa":"MAPA DE SP","origem": "A","destino": "B","autonomiaVeiculo": 20,"valorCombustivel": 2.50}
```

A resposta é uma Json com o resultado da execução. Esse Json possui duas propriedades:

- rota: melhor rota encontrada entre os pontos solicitados
- custo: custo da viagem -  valorCombustivel * (distanciaPercurso / autonomiaVeiculo)

Segue um exemplo de um Json retornado: 
```
{"custo":1.5,"rota":"A, B"}
```

Para informações sobre as regras consideradas, veja o arquivo Especificação-Webservice.doc

**Outras informações**
1 - O arquivo hibernate.cfg.xml é o arquivo que armazena os parametrôs de conexão com o banco, e pode ser encontrado em \target\classes\hibernate.cfg.xml

2 - No arquivo application.properties é possível trocar a porta do servidor. O mesmo pode ser encontrado em \target\classes\application.properties
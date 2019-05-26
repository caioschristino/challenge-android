# Arquitetura

## Clean Architecture 


A arquitetura dessa solução é uma versão MVVM da Clean Architecture, do Uncle Bob.
De acordo com a Clean Architecture, a aplicação deve ser dividida em 5 camadas conceituais (pondendo haver subcamadas em cada uma delas), da mais inferior à mais exterior delas, nessa ordem: Entities, Interactors, Interface Adapters, Views e Plugins. 

- A camada de Entitities possui as entidades (modelos) do nogócio; 
- A de Interactors possui Use Cases, componentes que orquestram as entidades de negócio para atingir um caso de uso específico; 
- A camada de Interface Adapters possui objetos que fazem a ponte entre os Use Cases e a camada de Views. Nessa camada é implementada alguma arquitetura de GUI, tais como MVC, MVVM, etc. 
- Por sua vez, a camada de Views possui as telas e componentes com que o usuário interage diretamente; e, por fim mas não menos importante,
- Na camada de Plugins residem as bibliotecas de terceiros e classes concretas que encapsulam essas bibliotecas e que implementam alguma interface das camadas internas (por exemplo, as interfaces do Repository Pattern, definidas na camada Interactors, são implementadas na camada Plugins. Então, as respectivas classes concretas encapsulam a Retrofit, biblioteca de terceiros usada para as requisições à API). 

Com a camada Plugins, o princípio de Liskov é respeitado e, portanto, permite-se a substituibilidade apenas "plugando" uma outra classe concreta que implemente a mesma interface. A camada Plugins possui uma subcamada chamada Main, a mais externa de todas, que é o ponto de partida da aplicação e na qual as injeções de dependencia ocorrem. Em geral, as camadas internas apenas conversam com suas próprias interfaces em tempo de escrita de código e compilação. Em tempo de execução, as devidas classes concretas são injetadas (plugadas).

Na Clean Architecture, há a Regra de Dependência que exige que classes de uma dada camada apenas dependam (importem) classes de camadas inferiores a ela, nunca de camadas superiores. A dependência ocorre, portanto, apenas em uma única direção: da camada mais externa para a mais interna. Isso se opõe à direção do fluxo de dados quando eles partem da camada mais interna para a externa (no caso, por exemplo, de um resultado da camada Interactors sendo devolvido para a View). Para resolver isso, recorre-se à inversão de dependência: cada camada pode definir uma interface para a qual irá devolver os dados. Essa interface pode ser implementada pela camada mais externa e a respectiva classe concreta pode ser instanciada em tempo de execução substituindo a interface (via alguma estratégia de injeção de dependência). Alternativamente, pode-se usar o Observer Pattern. 

Nesse app, as camadas do Clean Architecture foram mapeadas para um ou mais pacotes. A camada Entites corresponde ao pacote Model, comum para todas as features. A camada Interactors corresponde ao pacote Business de cada feature. A camada Interface Adapters corresponde ao pacote Gateway de cada feature. A camada Views corresponde ao pacote View de cada feature. A camada Plugins, por sua vez, corresponde ao pacote Plugin, comum para todo app. 

Portanto, cada feature possui seu próprio pacote, no qual estão os pacotes Business, Gateway e View específicos. Componentes das camadas Interactors, Interface Adapters e Views que são compartilhados por mais de uma feature ficam no pacote Base, o qual também possui os pacotes Business, Gateway e View específicos. Dessa forma, a arquitetura permite diretamente uma segregação futura do app em vários módulos.

Nesse app, utiliza-se injeção de dependência entre as camadas Interactors/Interface Adapters e a Plugins, e Observer Pattern (com LiveData) entre a camada Interface Adapters e a Views.


## A camada Entities

Todos os modelos de negócio estão presente nessa camada, que essencialmente são data classes. Diferente de meros DTOs, essas classes representam uma entidade de negócio e podem possuir métodos que abstraem alguma regra de negócio sobre os dados encapsulados. As classes dessa camada somente podem depender de outras classes da mesma camada. Nenhuma dependência com classes do Android ou qualquer framework é permitida.


## A camada Interactors

Todas as interações são coordenadas por Use Cases. A classe UseCase realiza o pattern Template Method e define um fluxo com métodos que as subclasses podem sobrescrever. UseCases podem receber um objeto de input e um lambda de callback. Eles fazem automaticamente o tratamento de exceções e as devolvem para o método onError() que, por padrão, encapsula a exceção emum objeto Output que é recebido pela lambda de callback. Um objeto Output, portanto, encapsula tanto um resultado como uma exceção.
Em geral, UseCases precisam de uma fonte de dados, seja local ou remota. Para isso, a camada Interactors de cada feature implementa o Repository Pattern ao definir uma interface que abstrai a fonte de dados. UseCases, em geral, recebem um objeto dessa interface em seus construtores. As classes concretas de cada uma dessas interfaces ficam na camada Plugin, na qual os UseCases também são instanciados.

A classe UseCase permite que cada método do seu fluxo seja chamado em uma Thread/coroutine específica, mas não implementa isso. Toda a parte de multithreading foi definida em uma classe separada, chamada UseCaseInvoker, favorecendo, dessa forma, o desacoplamento e facilitando os testes dessa camada. A UseCaseInvoker recebe o UseCase a ser executado e até dois Dispatchers. Os métodos de entrada e processamento (UseCase.guard() e UseCase.execute()) são executados no Dispatcher recebido no parâmetro "executeOn", já os métodos de saída (UseCase.onError() e UseCase.onSuccess()) são executados no Dispatcher recebido no parâmetro "resultOn". Essa separação foi baseada na maneira como a solução seria implementada com os métodos "executeOn()" e "observeOn()" do RxJava.

Nenhuma dependência com componentes de Android ou qualquer framrwork é permitida nessa camada. As classes dessa camada somente podem depender de outras classes da mesma camada ou (importar as) classes da camada inferior, a Entities.


## A camada Interface Adapters

Essa camada é responsável por invocar Use Cases de acordo com as ações do usuário. Essa camada possui essencialmente o(s) ViewModel(s) usado(s) pelos Fragments pertencentes à camada View.
Os Use Cases são recebidos via injeção de dependência sobre a qual falarei mais adiante.

Os ViewModels possuem um Map de LiveData(s). A comunicação com a camada mais externa é feita pelas instâncias de LiveData, nas quais o ViewModel publica ViewState(s) que, por sua vez, são observados pela camada de Views. Optou-se por um Map tanto para que as LiveData pudessem ser nomeadas, como para que houvesse mais de um canal de saída para a camada externa, pois sabe-se que LiveData pode perder dados em cenários de concorrência. Em geral, a estratégia realizada é um canal (LiveData) para cada UseCase.

ViewStates encapsulam objetos que representam um resultado ou estado. Eles também possuem uma flag que permite aos observadores indicar que o respectivo ViewState já foi processado, evitando, dessa forma, reprocessamento caso o LiveData envie-o mais de uma vez.

Essa camada possui dependência restrita aos componentes do Android (nomeadamente componentes de androidx.lifecycle.*). As classes dessa camada somente podem depender de outras classes da mesma camada ou (importar as) classes das camadas inferiores, a Entities e a Interactors.


## A camada Views

Essa camada possui os componentes de interface (Fragments, Activity, Navigation, Adapters, Animations e views customizadas). Esse app segue o padrão Single Activity e todas as telas são representadas por Fragments. A navegação entre as telas é feita pelo Navigation.

Essa camada possui alta dependência com o framework Android. As classes dessa camada somente podem depender de outras classes da mesma camada ou (importar as) classes das camadas inferiores: Entities, Interactors e Interface Adapters. Em geral, apenas depende da Entities (modelos) e Interface Adapters (ViewModels), não interagindo diretamente com Interactors (UseCases).


## A camada Plugins

A camada Plugins define as classes concretas das interfaces do Repository Pattern de cada feature. Nessa camada, também está a biblioteca de terceiros usada por essas classes concretas para acessar a API remota. Os UseCases também são instanciados nessa camada e passados para a camada Interface Adapter (que os utiliza) via injeção de dependência.


### Injeção de Dependência

Existem várias maneiras de realizar injeção de dependência e alguns frameworks disponíveis para isso. Nessa aplicação, optei por não usar nenhum framework. Segundo Uncle Bob, todos os frameworks (para network, banco de dados, DI, etc) são detalhes e, como tal, a decisão sobre qual usar pode ser postergada. Contudo, para não deixar de lado os benefícios da DI, realizei uma injeção de dependência bem simples que se vale de algumas capacidades da linguagem Kotlin. 

Cada feature possui um pacote especial chamado DI. Esse pacote possui uma interface que declara as dependências da feature para a camada Interface Adapter (em geral, as dependências são os UseCases usados pelos ViewModels). Essa interface também possui um Companion Object no qual uma implementação concreta dessa mesma interface é injetada com os respectivos objetos (das dependências declaradas) devidamente instanciados. 

A injeção acontece propriamente na camada Plugins, mais especificamente na Main que, no nosso caso, corresponde à classe BaseApplication (o ponto de partida de todo app). Dessa forma, obedece-se a Regra da Dependência da Clean Architecture. 

Não foi criada injeção de dependência para outras camadas além da Interface Adapter, mas, se fosse, o conceito poderia ser o mesmo, com um pacote DI específico para cada camada.

-------------------------------

# o Lodjinha

Minimo SDK: API 15</br>
Linguagem: Java ou Kotlin</br>

Serão consideradas funcionalidades completas se:</br>
- O descritivo da funcionalidade for implementado completamente.</br>
- A tela estiver aderente ao protótipo.</br>
- Não houver bugs impeditivos que atrapalhem ou impossibilitem a execução da funcionalidade.</br>
- O layout estiver aderente à todos os devices que suportem a versão mínima e superiores.</br>

Serão considerados bônus:</br>
- Teste unitário </br>
- Teste de UI

## DESAFIO

### 1 - Home

###### Funcionalidade 01
Exibir a barra de banners rotativo. Cada banner deve preencher todo o espaço horizontal da tela. Ao realizar o swipe left ou swipe right, o banner deve ser trocado pelo próximo ou anterior, conforme disponibilidade. Utilizar um indicador para facilitar ao usuário saber quantos banners existem e em qual posição ele está.

###### Funcionalidade 02
Exibir um menu deslizável horizontal com as categorias, conforme protótipo. O número de categorias é fixo, e não há necessidade de scroll infinito. Ao clicar em uma categoria, o app deve redirecionar o usuário para a Funcionalidade 04.

###### Funcionalidade 03
Exibir uma lista dos produtos mais vendidos. A lista possui um número fixo de produtos e não há necessidade de scroll infinito. Ao clicar em um produto, o usuário deve ser direcionado à Funcionalidade 05.

### 2 - Listagem de Categorias
	
###### Premissas:
- Um indicador de loading deve ser exibido enquanto uma nova página estiver sendo carregada.
- O usuário não deve ficar com a rolagem e navegação travados enquanto uma nova página estiver sendo carregada.

###### Funcionalidade 04
Exibir uma lista dos produtos da categoria selecionada, conforme protótipo. O lista possui um número desconhecido de produtos, e deverá ser paginado, limitando a página em 20 registros. Ao tocar em um produto, o usuário deverá ser direcionado à Funcionalidade 05.

### 3 - Exibição de Produto
	
###### Funcionalidade 05
Exibir a descrição do produto conforme protótipo. O botão Reservar deve estar sempre visível, fixado na parte de baixo da tela. O texto de descrição poderá vir formatado como HTML. O app deve tratar esse texto e exibí-lo corretamente (negrito, itálico, etc).
	
###### Funcionalidade 06
Ao clicar no botão Reservar, o app deve efetuar a reserva do produto com o servidor. Exibir a mensagem de sucesso ou erro da reserva. O usuário não deve poder tocar outra vez no botão enquanto a primeira reserva não for concluída. Se a reserva for concluída com sucesso, após fechar a mensagem de sucesso, retornar para a tela que chamou a Exibição de Produto.

### 4 - Sobre

###### Funcionalidade 07
Exibir o logo e o nome do app. Na parte de baixo da tela, exibir o nome do desenvolvedor (seu nome) e a data de desenvolvimento.

## RECURSOS

###### Protótipo Navegável

https://marvelapp.com/f1jb1eg

###### Fontes e Cores

https://scene.zeplin.io/project/589b3ef2dba1a0801d3f1be1

https://fonts.google.com/specimen/Pacifico

Os arquivos das imagens estão na pasta imagens.

###### Documentação

https://alodjinha.herokuapp.com/swagger-ui.html

## CONCLUSÃO

Crie um Fork desse repositório e envie um pull request.</br>
Caso seu projeto possua alguma pré condição para ser executado, crie um arquivo README.md com um passo a passo para que seja possível executá-lo.</br>
Projetos que não puderem ser executados não serão avaliados.

# COMEÇAR!!!

Foque em entregar funcionalidades completas!</br></br>
Quantidade não é qualidade!

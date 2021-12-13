# Arquitetura Data Lake na AWS, Camada de Mensagens com o Kafka. Desenvolver Producers e Consumer em Java e por último criação de 2 pipeline com StreamSets.

![1]
Essa será a arquitetura quando todo o projeto estiver pronto, mas irei dividir o projeto para não ficar muito grande então cada camada irei implementar e separar por projeto.
Camada de Mensagem com Kafka.

Todos os scripts estarão em anexo.

## Iniciando o Projeto
Iremos criar uma camada de Mensagem na construção do Data Lake de Projetos Anteriores e veremos em projetos finais a construção total de um Data Lake.
![2]
![3]
![4]

## Arquitetura Do Apache Kafka.
![5]

- Em linhas gerais e transferência de dados de um lado para outro. E como funciona? O Kafka chamo isso de tópicos o meu Producers ele precisa ter privilegio para enviar um dado para o tópico. O tópico nada mais é que um agrupamento de mensagem se você viu o projeto coleta de dados de sensores IoT é basicamente o que criamos então eu vou ter alguns Producers que vão enviar dados um ou mais Tópicos de acordo com a arquitetura que estivermos montando do outro lado os Consumers são os serviços que vão consumir esses dados eles vão fazer uma espécie de assinatura uma subscrição nesses Tópicos por exemplo do lado esquerdo eu posso ter produzindo um Topicos “X” ele envia isso para o Kafka e eu tenho um outro serviço do outro lado que vai consumir esses dados do Tópico X.
 - E como ocorre o controle aos Topics?
![6]
- Ele é feito pelo Broker como a equipe do Kafka gosta de chamar e uma espécie de Caixa de Correio alguém passa e coloca uma carta naquela caixinha depois uma outra pessoa passa e abre a caixinha e vê se tem correspondência o conceito e muito similar então o Producers ele vai gerar uma mensagem e envia para um tópico que está sendo controlado pelo Broker do outro lado eu tenho o Consumers que vai fazer uma conexão com o Broker e vai buscar os dados daquele Tópico.
![7]

O Broker nada mais é que um executável ele vai rodar na máquina onde está o Apache Kafka embora você possa montar uma arquitetura um pouco diferente e esse executável ele vai interagir com o Sistema Operacional estou chamando de executável, mas no Linux ele é um Binário quando um Producers envia um dado para o Tópico o Broker recebe aquele dado busca na lista de Tópico que ele tem e vai gravar aquele dado e ele grava no Sistema de Arquivo ele grava no SO ele precisa de algum processo de armazenamento para poder guardar aqueles dados enquanto o Consumers não busca esses dados para então levar para uma outra aplicação em linhas gerais essa é arquitetura do apache Kafka é claro que nós temos várias configurações do Producers, Broker e Consumers podemos montar diferentes arquitetura com essa combinação além claro dos cluster do apache Kafka.
Não é difícil imagina se o volume de dados que você tem a processa for muito grande apenas uma máquina com apache Kafka não de conta do recado se você tiver por exemplo um grande volume de streaming de dados você precise de um Cluster Kafka e o conceito e praticamente o mesmo com apache hadoop que basicamente é você distribuir o broker em mais de uma máquina
![8]

Eu posso ter o Broker em 2, 3, 4 máquinas ou eu posso ter 2 Broker rodando na mesma máquina. Eu posso ter um tópico associado a um Broker e um outro Tópico associado a outro Broker mas ambos na mesma máquina. Se eventualmente um serviço apresentar problema ou um tópico tiver uma carga de dados muito grande eu posso desligar só aquele Broker e o outro Broker fica rodando normalmente ou então você pode colocar o Broker em máquinas diferentes simplesmente colocar o Binário do apache Kafka em disposição e configurar o arquivo. Nesse exemplo da imagem são tamanho 4 o que significa 4 broker rodando em 3 máquinas. Más como sabemos nada é fácil e quando você cria um cluster Kafka ele não tem um sistema de gerenciamento e como resolvemos isso? Você precisa de outra camada de software o Zookeeper então a gestão do Cluster do apache Kafka é feita com apache Zookeeper.

## Onde entra Apache Zookeeper nesse contexto?
![9]
- Ele é como se fosse um “Manager” ele é o responsável por guardar informações dos dados.
![10]

![11]

## Desenvolvendo Kafka Producers e Consumer em Java 

![12]

No caso o nosso código está enviando a mensagem, mas o consumer não está recebendo uma ótima oportunidade para troubleshooting. O problema basicamente está nos do Broker o serve-0.properties precisamos colocar o ip do aws neles porque antes eu estava logando pelo meu servidor local então nem precisei modificar. Porque ele estava no localhost.

![13]
![14]

E pronto o consumer recebeu a mensagem.

![15]

Com isso todas as mensagens foram enviada, ou seja, temos uma aplicação Java que produz mensagem, estou produzindo a mensagem manualmente dentro do meu código mas eu poderia estar coletando isso de um Banco de dados de dispositivo IoT ou de qualquer outra fonte que você queira buscar dados e levar para o Kafka os dados ficam nos tópicos depois serão consumidos pelas aplicações para que sejam armazenado para que sejam processado e etc. 
Para ver um exemplo mais interessante, você pode usar um script fornecido pelo Kafka que produz mensagens em tempo real. Esse script é usado para teste de performance com o Kafka. Para usá-lo, abra um terminal e digite o comando conforme abaixo (considerando, claro que seu Kafka cluster está ativo):
bin/kafka-producer-perf-test.sh --topic topic3 --num-records 50 --record-size 1 --throughput 10 --producer-props bootstrap.servers=ec2-18-218-132-169.us-east-2.compute.amazonaws.com:9092 key.serializer=org.apache.kafka.common.serialization.StringSerializer value.serializer=org.apache.kafka.coomon.serialization.StringSerializer
O Comando cria um Producer, que você pode usar para gerar grandes volumes de dados em tempo real e testar suas aplicações de consumo. Altere o nome do servidor para o nome do seu servidor Kafka. Fique à vontade para aumentar o número de registros no parâmetro num-records. Ao executar o comando, o Kafka produz mensagens e enviar ao broker. Então a  aplicação java consome as mensagens

![16]

Esse exemplo de aplicação pode ser usado para consumer os dados no Kafka e levar para armazenamento no HDFS ou processamento com o Spark, de acordo com o seu objetivo final. Altere o programa e experimente outras opções você pode por exemplo, consumer os dados em pequenos lotes (batches) apenas alterando o código para consumir os dados em intervalos de tempo. As possibilidades são muito!!!

## 2 Pipeline Inteiro com StreamSets


![17]
![18]

pronto basicamente é simples baixar e instalar e conectar !!!  agora vamos colocar a mão na massa e criar alguns pipeline com StreamSets

![19]

- olha que incrivel basicamente é so escolher sua fonte de dados criar um diretorio para esses dados e acessar com seu midlware de preferencia eu estou usando o kafka Producer

![20]

fiz a primeira etapa que é do sensores IoT para o midlware porque ele garante caso a conexão acabe ou se eu quiser trocar a minha fonte de dados não preciso refazer todo. A minha estratégia basicamente é so trocar o StreamSets realmente é magnifico e ele também mostra statics e error dos dados enviados e o mais legal é tudo isso em Tempo Real. 
Então já temos a nossa primeira parte do nosso pipeline agora vamos construir a segunda parte onde nós vamos consumir as mensagens e então enviar os dados para o Data Lake no cluster HDFS.
Porque eu não criei ainda ? basicamente eu quero eles independe porque a final esse é o objetivo se as duas parte estiverem no mesmo pipeline caso de algum problema a produção para e não é isso que eu quero. É uma boa ideia segmentar isso então vou criar outro pipeline

![21]
Tudo Executando como o esperado.

![22]
eu coloquei só o Diretorio aonde será armazenado os dados no HDFS e pronto finalizamos essa etapa está tudo pronto. Se por acabo você tiver algum problema com a conexão você pode entrar no arquivo core-site.xml que está no diretorio que colocamos na print acima /opt/hadoop/etc/hadoop e copiar o URI que está lá não precisa fazer isso pq o diretorio que colocamos já vai ler esses arquivos mas se der é so colocar o URI> hdfs://ip-172-31-12-68.us-east-2.compute.internal:19000 aqui ele validou com sucesso significa que está tudo funcionando

![23]
Ambos os pipeline estão ativo essa é uma vantagem que eu considero em ter dois pipeline inteiro e indendente o 1 ele gera as mensagem o 2 ele consome as mensagens eu posso a qualquer momento desativar o pipe2 e o 1 estara rodando normalmente não temo problemas de perde os dados. Se eu coloco o pipeline em um único arquivo um único ponto ai eu posso ter problemas se algum processo falhar vai falhar meu pipeline se eu tiver que interromper apenas uma etapa eu vou ter que parar todo o pipeline eu gosto dessa boa prática de acordo com as etapas que você tem com seu processo.

Fim. Repare que o Data Lake está ficando bem robusto conforme o projeto anda. Primeiro projeto foi a criação do Data Lake na Aws, instâncias e Aquisição de dados em Batch, no segundo projeto Implementando Aquisição de dados em Streaming com Flume e Nifi e agora estamos colocando a camada de mensagem com Apache Kafka, Zookeeper para administrar os metadados e 2 Pipeline com StreamSets.

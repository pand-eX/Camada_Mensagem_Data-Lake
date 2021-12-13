import org.apache.kafka.clients.producer.*;

import java.text.*;
import java.util.*;

public class KafkaProducerApp {

    public static void main(String[] args){

        // Cria a classe Properties para instanciar o Producer com as configurações desejadas:
        Properties props = new Properties();

        props.put("bootstrap.servers", "ec2-18-218-132-169.us-east-2.compute.amazonaws.com:9092, ec2-18-218-132-169.us-east-2.compute.amazonaws.com:9093");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        props.put("acks", "1");
        props.put("buffer.memory", 33554432);
        props.put("compression.type", "none");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("client.id", "");
        props.put("linger.ms", 0);
        props.put("max.block.ms", 60000);
        props.put("max.request.size", 1048576);
        props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        props.put("request.timeout.ms", 30000);
        props.put("max.in.flight.requests.per.connection", 5);
        props.put("retry.backoff.ms", 5);


        KafkaProducer<String, String> myProducer = new KafkaProducer<String, String>(props);

        DateFormat dtFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        String topic = "topico3";

        // Número de registros para enviar
        int numberOfRecords = 1000;

        // Quanto tempo você deseja aguardar antes que o próximo registro seja enviado
        long sleepTimer = 0;

        try {
            for (int i = 0; i < numberOfRecords; i++ )
                myProducer.send(new ProducerRecord<String, String>(topic, String.format("Mensagem de Teste: %s  enviada em %s", Integer.toString(i), dtFormat.format(new Date()))));
            Thread.sleep(sleepTimer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myProducer.close();
        }

    }
}


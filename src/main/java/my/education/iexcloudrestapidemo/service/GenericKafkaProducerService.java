package my.education.iexcloudrestapidemo.service;

public interface GenericKafkaProducerService<V> {

    void produce(V v);
}

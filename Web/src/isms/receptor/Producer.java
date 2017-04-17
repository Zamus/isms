package isms.receptor;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import isms.common.Constants;
import isms.common.KafkaTopicNameGenerator;
import isms.records.SensorRecord;
import isms.serialization.SensorRecordSerializer;

public class Producer extends KafkaProducer<String, SensorRecord> {
	public Producer(Properties properties, Serializer<String> keySerializer, Serializer<SensorRecord> valueSerializer) {
		super(properties, keySerializer, valueSerializer);
	}

	public Producer() {
		this(properties(), new StringSerializer(), new SensorRecordSerializer());
	}

	private static Properties properties() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS);

		return props;
	}

	public void send(SensorRecord record) {
		String topic = new KafkaTopicNameGenerator().get(record.getOwnerId());
		String key = record.key();
		long timestamp = record.getTime();
		ProducerRecord<String, SensorRecord> message = new ProducerRecord<String, SensorRecord>(topic, 0, timestamp, key, record);

		super.send(message);
	}
}
package isms.serialization;

import org.apache.kafka.common.serialization.Deserializer;

public abstract class JsonDeserializer<T> extends WrapperJsonSerde<T> implements Deserializer<T> {

	protected JsonDeserializer(Class<T> clazz) {
		super(clazz);
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		return mapper.readValue(data, clazz);
	}

}

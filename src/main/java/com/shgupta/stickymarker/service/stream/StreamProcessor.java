package com.shgupta.stickymarker.service.stream;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;
import java.util.UUID;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.model.ListStreamsRequest;
import com.amazonaws.services.kinesis.model.ListStreamsResult;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.Record;

public class StreamProcessor {

	static String kinesisShardId;

	static final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

	public static void main(String[] args) throws Exception {
//		new Thread(() -> {
//			try {
//				producer();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}).start();
//
//		Thread.sleep(15 * 1000);
		new Thread(() -> {
			try {
				consumer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		
	}

	public static void producer() throws Exception {
		AmazonKinesis kinesis = AmazonKinesisClientBuilder.defaultClient();
		ListStreamsRequest listStreamsRequest = new ListStreamsRequest();
		listStreamsRequest.setLimit(10);
		ListStreamsResult listStreamsResult = kinesis.listStreams(listStreamsRequest);
		List<String> streamNames = listStreamsResult.getStreamNames();
		while (listStreamsResult.isHasMoreStreams()) {
			if (streamNames.size() > 0) {
				listStreamsRequest.setExclusiveStartStreamName(streamNames.get(streamNames.size() - 1));
			}

			listStreamsResult = kinesis.listStreams(listStreamsRequest);
			streamNames.addAll(listStreamsResult.getStreamNames());
		}
		// Print all of my streams.
		System.out.println("List of my streams: ");
		for (int i = 0; i < streamNames.size(); i++) {
			System.out.println("\t- " + streamNames.get(i));
		}
		int counter = 0;
		while (true) {
            long createTime = System.currentTimeMillis();
            PutRecordRequest putRecordRequest = new PutRecordRequest();
            putRecordRequest.setStreamName("stickymarker-ks");
            putRecordRequest.setData(ByteBuffer.wrap(String.format(++counter + "testData-%d", createTime).getBytes()));
            putRecordRequest.setPartitionKey(String.format("partitionKey-%d", createTime));
            PutRecordResult putRecordResult = kinesis.putRecord(putRecordRequest);
            System.out.printf("Successfully put record, partition key : %s, ShardID : %s, SequenceNumber : %s.\n",
                    putRecordRequest.getPartitionKey(),
                    putRecordResult.getShardId(),
                    putRecordResult.getSequenceNumber());
            Thread.sleep(1000);
            if (counter > 30) {
            	break;
            }
        }
	}

	public static void consumer() throws Exception {
		// TODO Auto-generated method stub

		String workerId = InetAddress.getLocalHost().getCanonicalHostName() + ":" + UUID.randomUUID();
		KinesisClientLibConfiguration kinesisClientLibConfiguration = new KinesisClientLibConfiguration("StickyMarker",
				"stickymarker-ks", awsCredentials(), workerId);
		kinesisClientLibConfiguration.withInitialPositionInStream(InitialPositionInStream.LATEST);
		Worker worker = new Worker(new IRecordProcessorFactory() {

			@Override
			public IRecordProcessor createProcessor() {

				return new IRecordProcessor() {

					@Override
					public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {
						// TODO Auto-generated method stub

					}

					@Override
					public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
						System.out.println("processRecords API Invoked ==> ");
						for (Record record : records) {
							try {
								System.out.println(
										"Shard " + kinesisShardId + " Consuming -> " + record.getSequenceNumber() + ", " + record.getPartitionKey()
												+ ", " + decoder.decode(record.getData()).toString());
							} catch (CharacterCodingException e) {}
						}
						System.out.println("processRecords API Completed ==> ");
					}

					@Override
					public void initialize(String shardId) {
						kinesisShardId = shardId;

					}
				};
			}
		}, kinesisClientLibConfiguration);
		worker.run();
	}

	public static AWSCredentialsProvider awsCredentials() {
		//
		return new AWSCredentialsProvider() {

			@Override
			public AWSCredentials getCredentials() {
				return new BasicAWSCredentials("AKIAID3Y4IJFVOXOW7FQ", "LzqE8jwv3oo+spjhvI7+9NlZWxAzA0cbPjsXNEaQ");
			}

			@Override
			public void refresh() {
				// TODO Auto-generated method stub

			}

		};
	}
}

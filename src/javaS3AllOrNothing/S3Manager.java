package javaS3AllOrNothing;

import java.util.logging.Logger;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3Manager {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BucketDownloader.class.getName());
	
	// This attribute should be static, but I avoided that during local testing
	private static AmazonS3 s3;
	
	/**
	 * Constructor that starts the AmazonS3 client if not already started
	 */
	public S3Manager() {
		if ( s3 == null ) {
			AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider("AwsCredentials.target.properties");
			s3  = new AmazonS3Client(credentialsProvider);
		}
	}
	
	public AmazonS3 getClient() {
		return this.s3;
	}

}

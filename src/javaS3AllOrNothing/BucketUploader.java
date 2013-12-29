package javaS3AllOrNothing;

import java.io.File;
import java.util.logging.Logger;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class BucketUploader {
	
	private static final Logger logger = Logger.getLogger(BucketUploader.class.getName());
	
	private AmazonS3 s3;
	
	/**
	 * Constructor that starts the AmazonS3 client if not already started
	 */
	public BucketUploader() {
		s3 = new S3Manager().getClient();
	}
	
	/**
	 * Put an object, blocking the thread till it's done
	 * @param bucketName
	 * @param key
	 * @param f
	 */
	public void put ( String bucketName, String key, File f ) {
		logger.info("Uploading a new object "+bucketName+"/"+key+" to S3 from a file "+f.getName());
        s3.putObject(new PutObjectRequest(bucketName, key, f ));
        logger.info("Done");
	}

}

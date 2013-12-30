package javaS3AllOrNothing;

import java.io.File;
import java.util.logging.Logger;

import com.amazonaws.services.s3.AmazonS3;

public class BucketUploader {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BucketUploader.class.getName());
	
	private AmazonS3 s3;
	
	/**
	 * Constructor that starts the AmazonS3 client if not already started
	 */
	public BucketUploader() {
		s3 = new S3Manager().getClient();
	}
	
	public boolean uploadBucket (String bucketName, String folderPath) {
		
		System.out.println("-- Started");
		
		// Verify that the given folder exists
		File folder = new File (folderPath);
		if ( !folder.exists() || !folder.isDirectory() ) {
			System.err.println("Given folder doesn't exist or isn't a directory: "+folderPath);
			return false;
		}
		
		// Check if the bucket exists
		boolean exists = s3.doesBucketExist(bucketName);
		
		if ( !exists ) {
			s3.createBucket(bucketName);
		}
		
		// Upload contents
		uploadFolder (folder, bucketName);
		
		System.out.println("-- Done");
		
		return true;
	}
	
	/**
	 * This method is used recursively in order to upload all contents of the given folder to the given bucket
	 * @param folder
	 * @param bucketName
	 */
	private void uploadFolder (File folder, String bucketName) {
		
		// For each element, put
		File[] files = folder.listFiles();
		
		for ( File file : files ) {
			
			// Only if the file object represents a file, upload it
			if ( file.isFile() ) {
				String key = file.getPath();
				key = key.substring( key.indexOf(bucketName) + bucketName.length() +1 );
				
				System.out.println(key);
				s3.putObject(bucketName, key, file);
			}
			
			// If this is a directory, recursively call it
			else if ( file.isDirectory() )
				uploadFolder ( file, bucketName );
		}
		
	}
	
	public static void main (String [] args){
		
		BucketUploader uploader = new BucketUploader();
		
		String bucketName = "";
		String folderName = "";
		
		uploader.uploadBucket(bucketName, folderName);
	}
}

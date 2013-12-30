package javaS3AllOrNothing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class BucketDownloader {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BucketDownloader.class.getName());
	
	private AmazonS3 s3;
	
	/**
	 * Constructor that starts the AmazonS3 client if not already started
	 */
	public BucketDownloader() {
		s3 = new S3Manager().getClient();
	}
	
	
	/**
	 * Download all files from the given bucket and put them in a folder with its name
	 * @param bucketName
	 * @return false if a fatal error happened, true otherwise
	 */
	public boolean downloadBucket(String bucketName) {
		
		System.out.println("-- Started");
		
		// Verify that the directory exists. Create it if not
		File folder = new File (bucketName);
		if ( !folder.exists() ) {
			boolean created = folder.mkdirs();
			if ( !created ) {
				System.err.println("The folder to store the bucket locally couldn't be created");
				return false;
			}
		}
		
		// Verify that the bucket exists
		boolean exists = s3.doesBucketExist(bucketName);
		if ( !exists ) {
			System.err.println("Error: Bucket "+bucketName+" doesn't exist");
		}
		System.out.println("Bucket "+bucketName+" exists");
		
		// List all objects
		ObjectListing listing = s3.listObjects(bucketName);
		boolean truncated = false;
		do {
			
			List<S3ObjectSummary> list = listing.getObjectSummaries();
			
			for ( S3ObjectSummary objectSummary : list ) {
				
				File newFile = new File (bucketName +"/"+ objectSummary.getKey());
				if ( newFile.exists() ) continue;
				
				
				// Download the object content
				S3Object object = s3.getObject(bucketName, objectSummary.getKey());
				S3ObjectInputStream objectContent = object.getObjectContent();
		        
		        try {
		        	
		        	// Get the corresponding folder
		        	String subfolderPath = objectSummary.getKey();
		        	System.out.println(subfolderPath);
		        	
		        	if ( subfolderPath.contains("/")) {
		        		subfolderPath = subfolderPath.substring(0, subfolderPath.lastIndexOf("/"));
		        		
			        	// Create folder
			        	File subfolder = new File (bucketName+"/"+subfolderPath);
			        	if ( !subfolder.exists() )
			        		subfolder.mkdirs();
		        	}
		        	
		        	// Create file object
		        	File file = new File (bucketName +"/"+ objectSummary.getKey());
		        	
		        	// Only if it isn't a directory, write it
		        	if ( !file.isDirectory() ) {
		        		FileOutputStream fout = new FileOutputStream( file );
			        	byte[] buf = new byte[1024];
			        	int len;
			        	while ((len = objectContent.read(buf)) != -1) {
			        	    fout.write(buf, 0, len);
			        	}
			        	fout.close();
		        	}
		        	
		        	
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			// Repeat the iteration if there are more 
			if( listing.isTruncated() ) {
				truncated = true;
				listing = s3.listNextBatchOfObjects(listing);
			} 
			else
				truncated = false;
			
		} while ( truncated );
		
		System.out.println("-- Done");
		
		return true;
	}

	public static void main (String [] args) {
		
		BucketDownloader downloader = new BucketDownloader();
		
		String bucketName = "";
		
		downloader.downloadBucket(bucketName);
	}
}

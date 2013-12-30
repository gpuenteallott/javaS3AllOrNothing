package javaS3AllOrNothing;

import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class BucketDeleter {
	
	private AmazonS3 s3;
	
	/**
	 * Constructor that starts the AmazonS3 client if not already started
	 */
	public BucketDeleter() {
		s3 = new S3Manager().getClient();
	}
	
	public boolean deleteBucketContents( String bucketName ) {
		
		System.out.println("-- Deleting contents");
		
		// Verify that the bucket exits
		if ( !s3.doesBucketExist(bucketName) ) {
			System.err.println("Bucket "+bucketName+" doesn't exist");
			return false;
		}
		
		// Remove all objects inside the bucket
		
		// List all objects
		ObjectListing listing = s3.listObjects(bucketName);
		boolean truncated = false;
		do {
			
			List<S3ObjectSummary> list = listing.getObjectSummaries();
			for ( S3ObjectSummary objectSummary : list ) {
				
				System.out.println(objectSummary.getKey());
				s3.deleteObject(bucketName, objectSummary.getKey());
			}
			
			// Repeat the iteration if there are more 
			if( listing.isTruncated() ) {
				truncated = true;
				listing = s3.listNextBatchOfObjects(listing);
			} 
			else
				truncated = false;
			
		} while ( truncated );
		
		
		System.out.println("-- Deleting contents done");
		return true;
	}
	
	public boolean deleteBucket( String bucketName ) {
		
		System.out.println("-- Started");
		
		if ( !deleteBucketContents (bucketName) )
			return false;
			
		// Delete bucket
		s3.deleteBucket(bucketName);
				
		System.out.println("-- Done");
		return true;
	}
	
	
	public static void main (String [] args) {
		
		BucketDeleter deleter = new BucketDeleter();
		
		String bucketName = "";
		
		deleter.deleteBucketContents(bucketName);
		
	}

}

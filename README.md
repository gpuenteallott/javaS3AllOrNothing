Java S3 All or Nothing

==================

Java S3 All or Nothing is a small one-evening project to create a simple Java program to operate will all the contents in a AWS S3 bucket.

The purpose was being able to download the entire bucket to a folder and upload a folder to a new bucket.

I upload here this code to allow developers to learn from it, or improve it if they want.

There are four classes:

 - S3Manager handles the client
 - BucketDownloader has the code to download all the contents of a bucket to a folder
 - BucketUploader has the code to upload all contents of a folder to a bucket, preserving the folder structure in the key names
 - BucketDeleter implements the function of deleting a bucket and all its contents automatically

The lib/ folder contains all libraries needed by this program, which consist in the AWS SDK and other libraries that it requires.

 Have a nice day!
 Guillermo

